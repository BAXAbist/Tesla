package tesla;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ivan + Tolik = ♥
 */
public class TeslaAgent extends Agent{
    
    private final AID driver = new AID("driver", AID.ISLOCALNAME);
    
    
    protected void setup() {
        MapGUI gui;
        try {
            gui = new MapGUI(this);
            gui.showGui();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        
        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(getAID());
        ServiceDescription sd = new ServiceDescription();
        sd.setType("control-recieve");
        sd.setName("JADE-control");
        dfd.addServices(sd);
        try {
            DFService.register(this, dfd);
        }
        catch (FIPAException fe) {
            fe.printStackTrace();
        }
        
        addBehaviour(new CheckControl());
    }
    
    
    private class CheckControl extends CyclicBehaviour {
        private int direction = 0;
        
        public void action(){
            MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.CFP);
            ACLMessage msg = myAgent.receive(mt);
            
            int control = Integer.parseInt(msg.getContent());
            System.out.println(control);
            
            switch (control){
                case (3):{//поворот направо
                    if (direction == 3){
                        direction = 0;
                    } 
                    else {
                        direction++;
                    }
                };
                break;
                
                case(2):{//поворот налево
                    if (direction == 0){
                        direction = 3;
                    } 
                    else {
                        direction--;
                    }
                };
                break;
                
                case(1):{//остановка
                };
                break;
                
                case(4):{//начать движение
                };
                break;
            }
            
        }
        
        
    }
    
}

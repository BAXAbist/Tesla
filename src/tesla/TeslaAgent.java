package tesla;

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
 * @author tolik and Ivan
 */
public class TeslaAgent extends Agent{

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
        
        public void action(){
            MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.INFORM);
            ACLMessage msg = myAgent.receive(mt);
            int control = Integer.parseInt(msg.getContent());
            
            switch (control){
                case (3):{//поворот направо
                };
                break;
                
                case(2):{//поворот налево
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

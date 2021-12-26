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
    private MapGUI gui;
    private int cnt_fuel = 100;
    private int fuel_cons_move = 2;
    
    protected void setup() {
        
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
        private int direction = 1;
        
        public void action(){
            MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.CFP);
            ACLMessage msg = myAgent.receive(mt);

            if (msg != null){
                int control = Integer.parseInt(msg.getContent());
                //System.out.println(control);
                if (cnt_fuel == 0) 
                    control = 4;
                switch (control){
                    case (1):{//поворот направо
                        if (direction == 3){
                            direction = 0;                           
                        } 
                        else {
                            direction++;
                        }
                        gui.turn(direction);
                        isGood(msg);
                        cnt_fuel -= fuel_cons_move;
                    };
                    break;

                    case(2):{//поворот налево
                        if (direction == 0){
                            direction = 3;
                        } 
                        else {
                            direction--;
                        }
                        gui.turn(direction);
                        isGood(msg);
                        cnt_fuel -= fuel_cons_move;
                    };
                    break;

                    case(4):{//остановка
                        isGood(msg);
                    };
                    break;

                    case(3):{//начать движение
                        
                        boolean b = gui.isWall(direction);
                        checkWall(b,msg);
                        if(b){
                            gui.move(direction);
                            cnt_fuel -= fuel_cons_move;
                        }
                    };
                    break;
                    
                    case(5):{
                        
                        if (gui.checkGas()){
                            if (cnt_fuel == 100)
                                System.out.println("ты чё, краёв не видишь, бак уже полный");
                            else {
                                if (cnt_fuel < 50)
                                    cnt_fuel += 50;
                                else
                                    cnt_fuel = 100;
                                System.out.println("всё, заправились, можно и дальше хасанить");
                            }
                       }
                        else
                            System.out.println("ишь ты чего захотел, а нет тут заправки");
                         
                    }
                }
            }        
        }
        public void checkWall(boolean isWall, ACLMessage msg){
            ACLMessage reply = msg.createReply();
            if (isWall){
                reply.setPerformative(ACLMessage.PROPOSE);
                reply.setContent(String.valueOf(cnt_fuel));
            }
            else{
                reply.setPerformative(ACLMessage.REFUSE);
                reply.setContent(String.valueOf(cnt_fuel));
            }
            myAgent.send(reply);
        }
        
        public void isGood(ACLMessage msg){
            ACLMessage reply = msg.createReply();
            reply.setPerformative(ACLMessage.PROPOSE);
            reply.setContent(String.valueOf(cnt_fuel));
             myAgent.send(reply);
        }
    }
}
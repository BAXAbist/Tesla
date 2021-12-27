
package tesla;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tolik
 */
public class ElonMuskAgent extends Agent {
    
    private int typeMove;
    private int oldMove;
    private final AID car = new AID("car", AID.ISLOCALNAME);
    private MessageTemplate mt;
    private ControlGUI gui;
    private boolean checkWait = false;
    
    /**
     *
     */
    @Override
    protected void setup() {
        gui = new ControlGUI(this);
        gui.showGui();
        typeMove = 4;
        oldMove = typeMove;
        
        addBehaviour(new SendMoveMes(this,500));
    }
    
    public void updateMes(int typeMove){
        this.typeMove = typeMove;
        if (typeMove == 3 || typeMove == 4)
            oldMove = typeMove;
        if (typeMove == 5)
            oldMove = 4;
        if (typeMove == 6)
            checkWait = !checkWait;
    }
    
    private class SendMoveMes extends TickerBehaviour{

        public SendMoveMes(Agent a, long period) {
            super(a, period);
        }
        
        @Override
        protected void onTick() {
            if (checkWait){
                ACLMessage cfp = new ACLMessage(ACLMessage.CFP);
                cfp.addReceiver(car);
                cfp.setContent(String.valueOf(typeMove)); 
                cfp.setConversationId("move"); 
                cfp.setReplyWith("cfp" + System.currentTimeMillis()); 
                myAgent.send(cfp);
                if (typeMove == 1 || typeMove == 2 || typeMove == 5)
                    typeMove = oldMove;

                 mt = MessageTemplate.and(MessageTemplate 
                        .MatchConversationId("move"), MessageTemplate 
                        .MatchInReplyTo(cfp.getReplyWith()));
                try {
                    Thread.sleep(100);
                } catch (Exception ex) {
                    System.out.println(ex);
                }
                 ACLMessage reply = myAgent.receive(mt); 
                if (reply != null) { 
                    if (reply.getPerformative() == ACLMessage.REFUSE) {
                        typeMove = 4;
                        oldMove = typeMove;
                        gui.stop();
                        System.out.println("чорт, стена");
                    }
                    int cnt_fuel = Integer.parseInt(reply.getContent());
                    if(cnt_fuel == 0){
                        gui.stop();
                        typeMove = 4;
                        oldMove = typeMove;
                    }
                    gui.fuel(cnt_fuel);
                }
            }
        }
    }
}

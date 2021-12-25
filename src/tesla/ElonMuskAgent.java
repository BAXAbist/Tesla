
package tesla;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;

/**
 *
 * @author tolik
 */
public class ElonMuskAgent extends Agent {
    
    private int typeMove;
    private final AID car = new AID("car", AID.ISLOCALNAME);
    
    protected void setup() {
        ControlGUI gui = new ControlGUI(this);
        gui.showGui();
        typeMove = 4;
        
        addBehaviour(new SendMoveMes(this,1000));
    }
    
    public void updateMes(int typeMove){
        this.typeMove =typeMove;
    }
    
    private class SendMoveMes extends TickerBehaviour{

        public SendMoveMes(Agent a, long period) {
            super(a, period);
        }
        
        @Override
        protected void onTick() {
            ACLMessage cfp = new ACLMessage(ACLMessage.CFP);
            cfp.addReceiver(car);
            cfp.setContent(String.valueOf(typeMove)); 
            cfp.setConversationId("move"); 
            cfp.setReplyWith("cfp" + System.currentTimeMillis()); 
            myAgent.send(cfp); 
        }
    }
}

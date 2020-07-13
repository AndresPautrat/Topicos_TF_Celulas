package agents;

import jade.core.Agent;
import jade.core.behaviours.ParallelBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public abstract class EntityAgent extends Agent {
    
    protected ParallelBehaviour parallel;
    
    protected abstract void computeStatus();
    
    protected abstract void computeMessage(ACLMessage msg);
    
    @Override
    protected void setup() {
        try {
            DFAgentDescription dfd = new DFAgentDescription();
            dfd.setName(getAID());
            DFService.register(this, dfd);
            parallel = new ParallelBehaviour();
            //Comportamiento movimiento
            parallel.addSubBehaviour(new TickerBehaviour(this, 100){
		@Override
                protected void onTick() { computeStatus(); }
            });
            parallel.addSubBehaviour(new CyclicBehaviour(this) {
                @Override
                public void action() {
                    // listen if a greetings message arrives
                    ACLMessage msg = receive(MessageTemplate.MatchPerformative(ACLMessage.INFORM));
                    if (msg != null)
                        computeMessage(msg);
                    else
                        block();
                }
            });
            addBehaviour(parallel);
        }
        catch (Exception e) {
            System.out.println("Saw exception in GuestAgent: " + e);
            e.printStackTrace();
        }
    }
    
    protected void die() {
        try {
            DFService.deregister(this);
            doDelete();
        }
        catch (Exception e) { e.printStackTrace(); }
    }
}
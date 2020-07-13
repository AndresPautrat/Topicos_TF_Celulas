package agents;

import TF.HostAgent;
import classes.Virus;
import static classes.Virus.StatusVirus.DEAD;
import static classes.Virus.StatusVirus.MOVING;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;
import static jade.lang.acl.ACLMessage.INFORM;

public class VirusAgent extends EntityAgent {  
    
    @Override
    protected void setup() {
        super.setup();
        parallel.addSubBehaviour(new TickerBehaviour(this, 100) {
            @Override
            protected void onTick() { getLocal().infect(); }
        });
    }
    
    public Virus getLocal() { return Virus.getLocal(getLocalName()); }

    @Override
    protected void computeStatus() {
        switch(getLocal().status) {
            case MOVING: getLocal().movementUpDown();
                break;
            case INFECTING: 
                break;
        }
    }

    @Override
    protected void computeMessage(ACLMessage msg) { 
        if (getLocal().status == MOVING) {
            switch(msg.getPerformative()){
                case INFORM:
                    if (msg.getContent().equals("die"))
                        die();
                    break;
            }
        }
    }
    
    @Override
    protected void die() {
        super.die();
        HostAgent.number_of_Virus -= 1;
        getLocal().status = DEAD;
    }
}
package agents;

import TF.HostAgent;
import classes.BCell;
import static classes.Cell.StatusCell.*;
import classes.NCell;
import classes.TCell;
import jade.core.AID;
import jade.lang.acl.ACLMessage;
import static jade.lang.acl.ACLMessage.*;
import java.util.Map;

public class NCellAgent extends CellAgent {
    
    @Override
    protected void computeStatus() {
        switch(getLocal().status) {
            case MOVING: getLocal().movementRandom();
                break;
            case INFECTED:
                getLocal().movementUpDown();
                if (((NCell)getLocal()).virus != null)
                    ((NCell)getLocal()).virus.position = getLocal().position;
                if(!HostAgent.INFECTION) {
                    ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
                    msg.setContent("infection");
                    for(Map.Entry<String,BCell> entry : BCell.getBCells().entrySet())
                        msg.addReceiver(new AID(entry.getValue().getName(), AID.ISLOCALNAME));
                    for(Map.Entry<String,TCell> entry : TCell.getTCells().entrySet())
                        msg.addReceiver(new AID(entry.getValue().getName(), AID.ISLOCALNAME));
                    send(msg);
                    HostAgent.INFECTION = true;
                }
                break;
            case GOINGTO:
                break;
            case ATTACKING: 
                break;
            case DEAD: 
                break;
        }
    }

    @Override
    protected void computeMessage(ACLMessage msg) {
        if (getLocal().status == INFECTED) {
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
        HostAgent.number_of_N_cells -= 1;
        getLocal().status = DEAD;
        super.die();
    }
}
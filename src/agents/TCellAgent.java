package agents;

import java.util.Map;

import TF.Functions;
import TF.Position;
import classes.Antibody;
import classes.Cell;
import classes.NCell;
import classes.TCell;
import jade.core.AID;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;

public class TCellAgent extends CellAgent {

    @Override
    protected void setup() {
        super.setup();

        parallel.addSubBehaviour(new TickerBehaviour(this, 1000) {

            @Override
            protected void onTick() {
                // TODO Auto-generated method stub
                int sumX = 0, sumY = 0, count = 0;
                for (Map.Entry<String, NCell> entry : NCell.getNormalCells().entrySet()) {
                    NCell nCell = entry.getValue();
                    // if (nCell.status == Cell.StatusCell.INFECTED) {
                    double distancia = getLocal().position.distance(nCell.position);
                    Boolean itsMinimun = true;
                    for (Map.Entry<String, TCell> entryB : TCell.getTCells().entrySet()) {
                        TCell tCell = entryB.getValue();
                        if (tCell.position.distance(nCell.position) < distancia) {
                            itsMinimun = false;
                            break;
                        }
                    }
                    if (itsMinimun) {
                        sumX += nCell.position.getX();
                        sumY += nCell.position.getY();
                        count++;
                    }
                    // }
                }
                if (count != 0) {
                    int distancia = 99999;
                    Position pos = new Position(sumX / count, sumY / count);
                    Position newPos = new Position(0, 0);
                    for (Map.Entry<String, NCell> entry : NCell.getNormalCells().entrySet()) {
                        NCell nCell = entry.getValue();
                        if (nCell.status == Cell.StatusCell.INFECTED) {
                            double newDis = nCell.position.distance(pos);
                            if (newDis < Double.valueOf(distancia)) {
                                distancia = (int) newDis;
                                newPos.setX(nCell.position.getX());
                                newPos.setY(nCell.position.getY());
                            }
                        }
                    }
                    if (distancia < 99999) {
                        getLocal().status = Cell.StatusCell.GOINGTO;
                        getLocal().setGoal(newPos);
                    }
                } else {
                    double distancia = 999999;
                    Position pos = new Position(0, 0);
                    for (Map.Entry<String, NCell> entry : NCell.getNormalCells().entrySet()) {
                        NCell nCell = entry.getValue();
                        if (nCell.status == Cell.StatusCell.INFECTED) {
                            double newDis = getLocal().position.distance(nCell.position);
                            if (newDis < distancia) {
                                distancia = newDis;
                                pos = nCell.position;
                            }
                        }
                    }
                    if (distancia < 999999) {
                        getLocal().setGoal(pos);
                        getLocal().status = Cell.StatusCell.GOINGTO;
                    }
                }
            }

        });
    }

    @Override
    protected void computeStatus() {
        switch (getLocal().status) {
            case ATTACKING:
                for (Map.Entry<String, NCell> entry : NCell.getNormalCells().entrySet()) {
                    NCell cell = entry.getValue();
                    if (cell.status == Cell.StatusCell.INFECTED) {
                        if (Functions.isClose(getLocal().position, cell.position)) {
                            ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
                            msg.setContent("die");
                            msg.addReceiver(new AID(cell.getName(), AID.ISLOCALNAME));
                            send(msg);
                        }
                    }
                }
                break;
            case MOVING:
                getLocal().movementRandom();
                break;
            /*
             * case INFECTED: break;
             */
            case GOINGTO:
                getLocal().goToGoal();
                for (Map.Entry<String, NCell> entry : NCell.getNormalCells().entrySet()) {
                    NCell cell = entry.getValue();
                    if (cell.status == Cell.StatusCell.INFECTED) {
                        if (Functions.isClose(getLocal().position, cell.position)) {
                            ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
                            msg.setContent("die");
                            msg.addReceiver(new AID(cell.getName(), AID.ISLOCALNAME));
                            send(msg);
                        }
                    }
                }
                break;
            /*
             * case DEAD: break;
             */
        }
    }

    @Override
    protected void computeMessage(ACLMessage msg) {
        if (msg.getContent().equals("infection") && getLocal().status != Cell.StatusCell.ATTACKING) {
            getLocal().status = Cell.StatusCell.ATTACKING;
        }
    }
}
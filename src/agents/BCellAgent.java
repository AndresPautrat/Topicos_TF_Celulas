package agents;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import TF.Functions;
import TF.HostAgent;
import TF.Position;
import classes.Antibody;
import classes.BCell;
import classes.Cell;
import classes.Virus;
import jade.core.behaviours.TickerBehaviour;
import jade.gui.VisualServicesList;
import jade.lang.acl.ACLMessage;

public class BCellAgent extends CellAgent {
    int sumX = 0, sumY = 0, count = 0;

    @Override
    protected void setup() {
        super.setup();
        parallel.addSubBehaviour(new TickerBehaviour(this, 5000) {

            @Override
            protected void onTick() {
                // TODO Auto-generated method stub
                for (int i = 0; i < 5; i++) {
                    Antibody antibody = new Antibody();
                    antibody.position = new Position(getLocal().position.getX(), getLocal().position.getY());
                    antibody.status = Antibody.StatusAntibody.MOVING;
                    antibody.start();
                    HostAgent.number_of_Antibodies += 1;
                }
            }

        });
        parallel.addSubBehaviour(new TickerBehaviour(this, 5000) {

            @Override
            protected void onTick() {
                // TODO Auto-generated method stub
                int sumX = 0, sumY = 0, count = 0;
                for (Map.Entry<String, Virus> entry : Virus.getActiveVirus().entrySet()) {
                    Virus virus = entry.getValue();
                    double distancia = getLocal().position.distance(virus.position);
                    Boolean itsMinimun = true;
                    for (Map.Entry<String, BCell> entryB : BCell.getBCells().entrySet()) {
                        BCell bCell = entryB.getValue();
                        if (bCell.position.distance(virus.position) < distancia) {
                            itsMinimun = false;
                            break;
                        }
                    }
                    if (itsMinimun) {
                        sumX += virus.position.getX();
                        sumY += virus.position.getY();
                        count++;
                    }
                }
                if (count != 0) {
                    getLocal().status = Cell.StatusCell.GOINGTO;
                    getLocal().setGoal(new Position(sumX / count, sumY / count));
                }
            }

        });
    }

    @Override
    protected void computeStatus() {
        switch (getLocal().status) {
            case MOVING:
                getLocal().movementRandom();
                break;
            case GOINGTO:
                getLocal().goToGoal();
                break;
            default:
                break;
        }
    }

    @Override
    protected void computeMessage(ACLMessage msg) {
    }
}
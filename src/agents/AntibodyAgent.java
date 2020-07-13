package agents;

import java.util.Map;

import TF.Functions;
import TF.Position;
import classes.Antibody;
import classes.Virus;
import jade.core.AID;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;

public class AntibodyAgent extends EntityAgent {

    public Antibody getLocal() {
        return Antibody.getLocal(getLocalName());
    }

    @Override
    protected void setup() {
        super.setup();

        parallel.addSubBehaviour(new TickerBehaviour(this, 5000) {

            @Override
            protected void onTick() {
                double distancia = 999999;
                Position pos = new Position(0, 0);
                // TODO Auto-generated method stub
                for (Map.Entry<String, Virus> entry : Virus.getActiveVirus().entrySet()) {
                    Virus virus = entry.getValue();
                    double newDis = getLocal().position.distance(virus.position);
                    if (newDis < distancia) {
                        distancia = newDis;
                        pos = virus.position;
                    }
                }
                if (distancia < 999999) {
                    getLocal().setGoal(pos);
                    getLocal().status = Antibody.StatusAntibody.GOINGTO;
                }
            }
        });
    }

    @Override
    protected void computeStatus() {
        int cercania = 10;
        for (Map.Entry<String, Antibody> entry : Antibody.getAntibodies().entrySet()) {
            Antibody antibody = entry.getValue();
            if (getLocal().position.isClose(antibody.position, cercania) && getLocal().position != antibody.position) {
                double deltaX = Math.abs(antibody.position.getX() - getLocal().position.getX());
                int signX = 1;
                if (antibody.position.getX() < getLocal().position.getX())
                    signX = -1;
                double deltaY = Math.abs(antibody.position.getY() - getLocal().position.getY());
                int signY = 1;
                if (antibody.position.getY() < getLocal().position.getY())
                    signY = -1;
                if (deltaX > 0) {
                    double angleMovement = Math.atan(deltaY / deltaX);
                    double x_tmp = Math.ceil(cercania * Math.cos(angleMovement));
                    if (signX == -1)
                        x_tmp = Math.floor(signX * cercania * Math.cos(angleMovement));
                    double y_tmp = Math.ceil(cercania * Math.sin(angleMovement));
                    if (signY == -1)
                        y_tmp = Math.floor(signY * cercania * Math.sin(angleMovement));
                    getLocal().position.add(-x_tmp, -y_tmp);
                } else
                    getLocal().position.addY(-signY * cercania);
            }
        }
        switch (getLocal().status) {
            case MOVING:
                getLocal().movementRandom();
                break;
            case GOINGTO:
                getLocal().goToGoal();
            case ATTACKING:
                for (Map.Entry<String, Virus> entry : Virus.getActiveVirus().entrySet()) {
                    Virus virus = entry.getValue();
                    if (virus.status != Virus.StatusVirus.INFECTING) {
                        if (Functions.isClose(getLocal().position, virus.position)) {
                            ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
                            msg.setContent("die");
                            msg.addReceiver(new AID(virus.getName(), AID.ISLOCALNAME));
                            send(msg);
                        }
                    }
                }
                break;
        }
    }

    @Override
    protected void computeMessage(ACLMessage msg) {
        if (msg.getContent().equals("infection") && getLocal().status != Antibody.StatusAntibody.ATTACKING) {
            getLocal().status = Antibody.StatusAntibody.ATTACKING;
        }
    }
}
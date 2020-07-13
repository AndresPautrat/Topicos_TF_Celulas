package classes;

import TF.Functions;
import TF.Position;
import static frame.EnvironmentPanel.MAX_X;
import static frame.EnvironmentPanel.MAX_Y;
import static frame.EnvironmentPanel.BORDER_X;
import static frame.EnvironmentPanel.BORDER_Y;
import jade.core.Agent;
import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public abstract class Entity {
    private String id;
    protected Agent agent;
    public Position position;
    protected Color color;
    protected int radius;
    public int speed;
    protected int direction;
    protected String entity_type;
    protected Position goal;
    
    public Entity() {
        position = new Position(
                BORDER_X + (int) ((MAX_X - BORDER_X) * Math.random()), 
                BORDER_Y + (int) ((MAX_Y - BORDER_Y) * Math.random()));
        color =  new Color((int)(Math.random() * 0x1000000));
        direction = 1;
        entity_type = "entity";
        //this.id = id;
        id = Functions.randomString();
        goal = null;
        if(this instanceof Virus) {
            entity_type = "Virus";
            Virus.addVirus((Virus) this);
        }
        else if(this instanceof Antibody) {
            entity_type = "Antibody";
            Antibody.addAntibody((Antibody) this);
        }
        else {
            //System.out.println(this.getName());
            if(this instanceof NCell)
                entity_type = "NCell";
            else if(this instanceof TCell)
                entity_type = "TCell";
            else if(this instanceof BCell)
                entity_type = "BCell";
            Cell.addCell((Cell) this);
        }
    }
    
    public void setGoal(Position goal) { 
        this.goal = goal;
        goToGoal();
    }
    
    public void goToGoal() {
        if(!hasArrived()) {
            double deltaX = Math.abs(goal.getX() - position.getX());
            int signX = 1;
            if (goal.getX() < position.getX())
                signX = -1;
            double deltaY = Math.abs(goal.getY() - position.getY());
            int signY = 1;
            if (goal.getY() < position.getY())
                signY = -1;
            if (deltaX > 0) {
                double angleMovement = Math.atan(deltaY / deltaX);
                double x_tmp = Math.ceil(speed * Math.cos(angleMovement));
                if (signX == -1)
                    x_tmp = Math.floor(signX * speed * Math.cos(angleMovement));
                double y_tmp = Math.ceil(speed * Math.sin(angleMovement));
                if (signY == -1)
                    y_tmp = Math.floor(signY * speed * Math.sin(angleMovement));
                position.add(x_tmp, y_tmp);
            }
            else
                position.addY(signY * speed);
        }
        if(position.getX() < BORDER_X)
            position.setX(BORDER_X);
        else if(position.getX() >= MAX_X - BORDER_X)
            position.setX(MAX_X - BORDER_X);
        if(position.getY() < BORDER_Y)
            position.setY(BORDER_Y);
        else if(position.getY() >= MAX_Y - BORDER_Y)
            position.setY(MAX_Y - BORDER_Y);
    }
    
    public boolean hasArrived() { return Functions.isClose(goal, position); }
    
    public String getID() { return id; }
    
    public String getName() { return entity_type + "_" + id; }
    
    public abstract void paint(Graphics g);
    
    public void start() { start(this); }
   
    private void start(Entity ent) { 
        /*if(ent instanceof Virus)
            Virus.addVirus((Virus) ent);
        else if(ent instanceof Antibody)
            Antibody.addAntibody((Antibody) ent);
        else
            Cell.addCell((Cell) ent);*/
        Functions.createAgent(getName(), "agents." + entity_type + "Agent"); 
    }
    
    public void movementRandom() {
        Random aleatorio = new Random(System.currentTimeMillis());
        if (position.getX() <= BORDER_X)
            position.addX(speed);
        else if (position.getX() >= MAX_X - BORDER_X)
            position.addX(-speed);
        else if (aleatorio.nextInt(100) > 50)
            position.addX(speed);
        else
            position.addX(-speed);
        if (position.getY() <= BORDER_Y)
            position.addY(speed);
        else if (position.getY() >= MAX_Y - BORDER_Y)
            position.addY(-speed);
        else if (aleatorio.nextInt(100) > 50)
            position.addY(speed);
        else
            position.addY(-speed);
    }
    
    public void movementUpDown() {
        Random aleatorio = new Random(System.currentTimeMillis());
        if (position.getX() <= BORDER_X)
            position.addX(speed);
        else if (position.getX() >= MAX_X - BORDER_X)
            position.addX(-speed);
        else if (aleatorio.nextInt(100) > 50)
            position.addX(speed);
        else
            position.addX(-speed);
        if (position.getY() <= BORDER_Y)
            direction = 1;
        else if (position.getY() >= MAX_Y - BORDER_Y)
            direction = -1;
        position.addY(direction * speed);
    }
}
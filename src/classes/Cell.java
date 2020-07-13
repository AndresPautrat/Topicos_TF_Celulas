package classes;

import TF.Functions;
import TF.Position;
import static classes.Cell.StatusCell.DEAD;
import static classes.Cell.StatusCell.INFECTED;
import static frame.EnvironmentPanel.MAX_X;
import static frame.EnvironmentPanel.MAX_Y;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RadialGradientPaint;
import java.util.Map;
import java.util.TreeMap;

public abstract class Cell extends Entity {
    protected static TreeMap<String,Cell> Cells = new TreeMap<String,Cell>();
    public static void addCell(Cell c) { Cells.put(c.getName(), c); }
    //private static TreeMap<String,Cell> newCells = new TreeMap<String,Cell>();
    //public static void addCell(Cell c) { newCells.put(c.getName(), c); }
    public static Cell getLocal(String name) { 
        if (!Cells.containsKey(name))
            System.out.println(Cells.keySet().size() + " " + Cells.keySet());
        return Cells.get(name); 
    }
    
    public static void paintAllCells(Graphics g) {
        try {
            for (Map.Entry<String, Cell> entry : Cells.entrySet()) {
                if(entry.getValue().status != DEAD)
                    entry.getValue().paint(g);
            }
            //Cells.putAll(newCells);
            //newCells.clear();
        } catch(Exception e) { }
    }
    
    public enum StatusCell { MOVING, GOINGTO, INFECTED, ATTACKING, DEAD; }
    
    public StatusCell status;
    public int number_of_clones;
    public boolean is_cloned = false;
    
    protected void setStatus(StatusCell status) { this.status = status; }
    
    public Cell() {
        super();
        number_of_clones = 0;
        //entity_type = "Cell";
        //cell_type = "Cell";
        is_cloned = false;
        int del = 100;
        position = new Position((int) ((MAX_X - del * 2) * Math.random() + del), 
                                (int) ((MAX_Y - del * 2) * Math.random() + 2 * del));
        color = Color.BLACK;
        radius = 80;
        speed = 4;
        status = StatusCell.MOVING;
    }
    
    @Override
    public void paint(Graphics g) {
        g.setColor(color);
        switch (entity_type) {
            case "NCell":
                int f = 4;
                if (is_cloned) {
                    g.drawOval(position.getX(), position.getY(), radius, radius);
                    g.drawOval(position.getX() + f, position.getY() + f, radius - 2 * f, radius - 2 * f);
                    f = 8;
                    g.drawOval(position.getX() + f, position.getY() + f, radius - 2 * f, radius - 2 * f);
                    g.setColor(color);
                }
                else {
                    g.drawOval(position.getX(), position.getY(), radius, radius);
                    g.drawOval(position.getX() + f, position.getY() + f, radius - 2 * f, radius - 2 * f);
                    g.setColor(color);                    
                }
                break;
            default:
                g.fillOval(position.getX(), position.getY(), radius, radius);
                g.setColor(Color.WHITE);
                f = 5;
                g.fillOval(position.getX() + f, position.getY() + f, radius - 2 * f, radius - 2 * f);
                g.setColor(color);
                g.drawString(entity_type.charAt(0) + "", position.getX() + radius / 2 - 3, position.getY() + radius / 2 + 4);
                break;
        }
        if (status == INFECTED) {
            Graphics2D gr = (Graphics2D) g;
            gr.setPaint(new RadialGradientPaint(
                    new Point(200, 400), 50, new float[] { 0, 0.3f, 1 }, 
                    new Color[] { Color.RED, Color.RED, Color.RED }));
            gr.fill(Functions.shapeStar(position.getX() + 40, position.getY() + 40, radius / 8, radius / 4, 20, 0));
        }
    }
    
    @Override
    public String getName() { return entity_type + "_" + getID(); }
}
package classes;

import TF.Functions;
import TF.HostAgent;
import TF.Position;
import static classes.Virus.StatusVirus.INFECTING;
import static frame.EnvironmentPanel.MAX_X;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RadialGradientPaint;
import java.util.Map;
import java.util.TreeMap;

public class Virus extends Entity {
    private static TreeMap<String, Virus> VirusL = new TreeMap<String, Virus>(); 
    public static TreeMap<String, Virus> getActiveVirus() {
        TreeMap<String, Virus> _virus = new TreeMap<String, Virus>();
        for (Map.Entry<String, Virus> entry : VirusL.entrySet()) {
            if (entry.getValue().status != StatusVirus.INFECTING && entry.getValue().status != StatusVirus.DEAD)
                _virus.put(entry.getKey(), entry.getValue());
        }
        return _virus;
    }
    
    public static void addVirus(Virus v) { VirusL.put(v.getName(), v); }
    public static Virus getLocal(String name) { return VirusL.get(name); }

    public enum StatusVirus { MOVING, INFECTING, DEAD; }
    
    public StatusVirus status;
    
    public Virus() {
        super();
        position = new Position((int) (MAX_X * Math.random()), (int) (50 * Math.random()));
        color = Color.RED;
        radius = 20;
        speed = 15;
        status = StatusVirus.MOVING;
        //entity_type = "Virus";
    }
    
    @Override
    public void paint(Graphics g) {        
        if (status != INFECTING) {
            g.setColor(color);
            Graphics2D gr = (Graphics2D) g;
            gr.setPaint(new RadialGradientPaint(
            new Point(200, 400), 50, new float[] { 0, 0.3f, 1 }, 
            new Color[] { Color.RED, Color.YELLOW, Color.ORANGE }));
            gr.fill(Functions.shapeStar(position.getX(), position.getY(), radius / 2, radius, 20, 0));
        }        
    }    
     
    public void infect() {
        if (status != INFECTING) {
            try {
                for (Map.Entry<String, NCell> entry : NCell.getNormalCells().entrySet()) {
                    NCell cell = entry.getValue();
                    if (cell.status != Cell.StatusCell.INFECTED) {
                        if (Functions.isClose(position,cell.position)) {
                            HostAgent.number_of_I_cells += 1;
                            cell.status = Cell.StatusCell.INFECTED;
                            cell.speed *= 4;
                            cell.color = Color.RED;
                            ((NCell)cell).virus = this;
                            status = INFECTING;
                            break;
                        }
                    }
                }
            }
            catch(Exception e) { }
        }
    }
}
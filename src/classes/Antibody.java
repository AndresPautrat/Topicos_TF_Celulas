package classes;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.QuadCurve2D;
import java.util.Map;
import java.util.TreeMap;

public class Antibody extends Entity {
    private static TreeMap<String,Antibody> Antibodies = new TreeMap<String,Antibody>();
    public static void addAntibody(Antibody a) { Antibodies.put(a.getName(), a); }
    //private static TreeMap<String,Antibody> newAntibodies = new TreeMap<String,Antibody>(); 
    //public static void addAntibody(Antibody a) { newAntibodies.put(a.getName(), a); }
    public static Antibody getLocal(String name) { return Antibodies.get(name); }
    public static TreeMap<String, Antibody> getAntibodies() { 
        /*TreeMap<String,Antibody> Antibodies_tmp = ((TreeMap<String,Antibody>)Antibodies.clone());
        TreeMap<String,NCell> _ncells = new TreeMap<String, NCell>();
        for (Map.Entry<String, Cell> entry : Cells_tmp.entrySet()) {
            if (entry.getValue() instanceof NCell)
                _ncells.put(entry.getKey(), (NCell) entry.getValue());
        }*/
        //return ((TreeMap<String,Antibody>)Antibodies.clone()); 
        return Antibodies; 
    }
    
    public static void paintAllAntibodies(Graphics g) {        
        try {
            for (Map.Entry<String, Antibody> entry : Antibodies.entrySet())
            entry.getValue().paint(g);
            //Antibodies.putAll(newAntibodies);
            //newAntibodies.clear();
        } catch(Exception e) { }
    }
    /*public static void paintAllAntibodies(Graphics g) {
        for (Map.Entry<String, Antibody> entry : Antibodies.entrySet())
            entry.getValue().paint(g);
        try {
            Antibodies.putAll(newAntibodies);
            newAntibodies.clear();
        } catch(Exception e) { System.out.println(e); }
    }*/
    
    public enum StatusAntibody { MOVING, GOINGTO, ATTACKING; }
    
    public StatusAntibody status;
    
    public Antibody() {
        super();
        //super(Functions.randomID());
        color = Color.CYAN;
        radius = radius / 2;
        speed = 10;
        //entity_type = "Antibody";
        status = StatusAntibody.MOVING;
    }
    
    @Override
    public void paint(Graphics g) {
        g.setColor(color);
        Graphics2D G2D = (Graphics2D)g;
        //G2D.setColor(Color.GREEN);
        G2D.setStroke(new BasicStroke(3.0f));
        QuadCurve2D QC2D = new QuadCurve2D.Float(position.getX(), position.getY(), position.getX(), position.getY() + 10, position.getX() + 10, position.getY() + 10);
        G2D.draw(QC2D);
        QuadCurve2D QC2D2 = new QuadCurve2D.Float(position.getX(), position.getY(), position.getX(), position.getY() + 10, position.getX() - 10, position.getY() + 10);
        G2D.draw(QC2D2);
        G2D.setColor(Color.red);
        //G2D.draw(new Rectangle2D.Float(position.getX(), position.getY(), position.getX() + 1.0f, position.getY() + 1.0f));
        //G2D.draw(new Rectangle2D.Float(40.0f, 170.0f, 1.0f, 1.0f));
        //G2D.draw(new Rectangle2D.Float(190.0f, 220.0f, 1.0f, 1.0f));
    }
}
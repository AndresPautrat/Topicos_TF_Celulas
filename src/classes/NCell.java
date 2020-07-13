package classes;

import static TF.HostAgent.number_of_I_cells;
import static TF.HostAgent.number_of_Virus;
import TF.Position;
import java.awt.Color;
import java.util.Map;
import java.util.TreeMap;

public class NCell extends Cell {
    public static TreeMap<String, NCell> getNormalCells() {
        //TreeMap<String,Cell> Cells_tmp = ((TreeMap<String,Cell>)Cells.clone());
        TreeMap<String, NCell> _ncells = new TreeMap<String, NCell>();
        for (Map.Entry<String, Cell> entry : Cells.entrySet()) {
            if (entry.getValue() instanceof NCell)
                _ncells.put(entry.getKey(), (NCell) entry.getValue());
        }
        return _ncells;
    }
    
    public Virus virus;
    
    public NCell() {
        super();
        //super(Functions.randomID());
        color = Color.BLUE;
        //entity_type = "NCell";
        virus = null;
    }
    
    public NCell reproduce() {
        double d = 0.3;
        if (status == StatusCell.INFECTED)
            d = 0.6;
        if (Math.random() < d) {
            number_of_clones += 1;
            //NCell ncell = new NCell(id * 100 + number_of_clones);            
            NCell ncell = new NCell();
            ncell.is_cloned = true;
            ncell.status = status;
            ncell.speed = speed;
            ncell.color = color;
            ncell.position = new Position(position.getX(), position.getY());
            if (status == StatusCell.INFECTED) {
                //Virus v = new Virus(virus.id * 100 + number_of_clones);
                //Virus v = new Virus();
                //v.status = virus.status;
                //ncell.virus = v;
                //ncell.virus.position = new Position(position.getX(), position.getY());
                //v.start();
                number_of_I_cells += 1;
                number_of_Virus += 1;
            }
            return ncell;
        }
        return null;
    }
}
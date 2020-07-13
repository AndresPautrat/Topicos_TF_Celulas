package classes;

import java.awt.Color;
import java.util.Map;
import java.util.TreeMap;

public class BCell extends Cell {
    public static TreeMap<String, BCell> getBCells() {
        TreeMap<String, BCell> _bcells = new TreeMap<String, BCell>();
        for (Map.Entry<String, Cell> entry : Cells.entrySet()) {
            if (entry.getValue() instanceof BCell)
                _bcells.put(entry.getKey(), (BCell) entry.getValue());
        }
        return _bcells;
    }
    
    public BCell() {
        super();
        color = Color.GREEN;
        radius = radius / 2;
        speed = 4;
        //entity_type = "BCell";
    }
}
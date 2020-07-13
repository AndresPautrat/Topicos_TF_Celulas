package classes;

import java.awt.Color;
import java.util.Map;
import java.util.TreeMap;

public class TCell extends Cell {
    public static TreeMap<String, TCell> getTCells() {
        TreeMap<String, TCell> _tcells = new TreeMap<String, TCell>();
        for (Map.Entry<String, Cell> entry : Cells.entrySet()) {
            if (entry.getValue() instanceof TCell)
                _tcells.put(entry.getKey(), (TCell) entry.getValue());
        }
        return _tcells;
    }
    
    public TCell() {
        super();
        color = Color.MAGENTA;
        radius = radius / 2;
        speed = speed * 3;
        //entity_type = "TCell";
    }
}
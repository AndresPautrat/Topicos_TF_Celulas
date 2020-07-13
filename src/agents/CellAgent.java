package agents;

import classes.Cell;

public abstract class CellAgent extends EntityAgent {
    public Cell getLocal() { return Cell.getLocal(getLocalName()); }
}
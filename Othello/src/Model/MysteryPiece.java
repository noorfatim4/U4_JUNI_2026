package Model;

/**
 * MysteryPiece-klassen representerar en spelarpjäs på spelplanen.
 * Ärver från abstrakt-klassen Piece
 *
 * @author Noor-Fatima Nabi & Fauzia Bhuiyan
 */
public class MysteryPiece extends Piece {

    private MysteryType type;
    private boolean activated = false;

    /**
     * Denna konstruktor skapar ett nytt mysterium
     * @param row
     * @param col
     * @param type
     * @author Fauzia Bhuiyan
     */
    public MysteryPiece(int row, int col, MysteryType type) {
        super(row, col);
        this.type = type;
    }

    /**
     * Denna metod returnerar typen av mysterium
     * @return type
     * @author Noor-Fatima Nabi
     */
    public MysteryType getType() {
        return type;
    }

    /**
     * Denna metod returnerar om mysteriet har aktiverats
     * @return boolean
     * @author Noor-Fatima Nabi
     */
    public boolean isActivated() {
        return activated;
    }

    /**
     * Sätter aktiveringsstatus för mysteriet
     * @param value
     * @author Fauzia Bhuiyan
     */
    public void setActivated(boolean value) {
        activated = value;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
}

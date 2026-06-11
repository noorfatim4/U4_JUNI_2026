package Model;

/**
 * Abstrakt basklass för alla pjäser på spelplanen.
 *
 * @author Fauzia Bhuiyan
 */
public abstract class Piece {
    protected int row;
    protected int col;
    protected int owner;

    /**
     * Denna konstruktor skapar en pjäs med rad, kolumn och ägare.
     * @param row
     * @param col
     * @param owner
     * @author Fauzia Bhuiyan
     */
    public Piece(int row, int col, int owner) {
        this.row = row;
        this.col = col;
        this.owner = owner;
    }

    /**
     * Denna metod skapar en pjäs med endast rad och kolumn
     * @param row
     * @param col
     * @author Noor-Fatima Nabi
     */
    public Piece(int row, int col) {
        this.row = row;
        this.col = col;
        this.owner = 0;
    }

    public int getOwner() {
        return owner;
    }

    public void setOwner(int newOwner) {
        this.owner = newOwner;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
}

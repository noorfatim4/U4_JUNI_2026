package Model;

/**
 * Board klassen hanterar rader och kolumner i spelplanen
 *
 * @author Noor-Fatima Nabi & Fauzia Bhuiyan
 */
public class Board {
    private final Piece[][] boxes;

    /**
     * Denna kontruktor skapar en array som representerar spelplanen
     * @param rows
     * @param cols
     * @author Fauzia Bhuiyan
     */
    public Board(int rows, int cols) {
        boxes = new Piece[rows][cols];
    }

    /**
     * Getter metod för spelplanen
     * @param row
     * @param col
     * @return boxes
     * @author Fauzia Bhuiyan
     */
    public Piece getPiece(int row, int col) {
        return boxes[row][col];
    }

    /**
     * Denna metod kontrollerar om plats är tom
     * @param row
     * @param col
     * @return boolean
     * @author Noor-Fatima Nabi
     */
    public boolean isEmpty(int row, int col) {
        return boxes[row][col] == null;
    }

    /**
     * Denna metod hanterar placering av pjäs
     * @param piece
     * @author Noor-Fatima Nabi
     */
    public void placePiece(Piece piece) {
        boxes[piece.getRow()][piece.getCol()] = piece;
    }

    /**
     * Getter för att returnera antalet rader på spelplanen.
     * @return boxes
     * @author Fauzia Bhuiyan
     */
    public int getRows() {
        return boxes.length; }

    /**
     * Getter för att returnera antalet kolumner på spelplanen.
     * @return boxes
     * @author Fauzia Bhuiyan
     */
    public int getCols() {
        return boxes[0].length; }

    /**
     * Denna metod kontrollerar grannar runt spelar-pjäser
     * @param row
     * @param col
     * @return boolean
     * @author Noor-Fatima Nabi
     */
    public boolean hasNeighbourPiece(int row, int col) {
        for (int[] d : ALL_DIRECTIONS) {
            int r = row + d[0], c = col + d[1];
            if (withinArray(r, c) && boxes[r][c] != null) return true;
        }
        return false;
    }

    /**
     * Denna metod kontrollerar grannar runt mysterium
     * @param row
     * @param col
     * @return boolean
     * @author Noor-Fatima Nabi
     */
    public boolean hasNeighbourMystery(int row, int col) {
        for (int[] d : ALL_DIRECTIONS) {
            int r = row + d[0], c = col + d[1];
            if (withinArray(r, c) && boxes[r][c] instanceof MysteryPiece)
                return true;
        }
        return false;
    }

    /**
     * Denna metod kontrollerar offset utanför array
     * @param row
     * @param col
     * @return boolean
     * @author Noor-Fatima Nabi
     */
    public boolean withinArray(int row, int col) {
        return row >= 0 && col >= 0 && row < getRows() && col < getCols();
    }

    /**
     * Denna metod kontrollerar att inget hamnar i hörnen
     * @param row
     * @param col
     * @return boolean
     * @author Noor-Fatima Nabi
     */
    public boolean isCorner(int row, int col) {
        return (row == 0 || row == getRows() - 1) && (col == 0 || col == getCols() - 1);
    }

    private static final int[][] ALL_DIRECTIONS = {
            {-1,-1},{-1,0},{-1,1},
            { 0,-1},      { 0,1},
            { 1,-1},{ 1,0},{ 1,1}
    };
}

package Model;

/**
 * PlayerPiece-klassen representerar en spelarpjäs på spelplanen
 * Ärver från abstrakt-klassen Piece
 */
public class PlayerPiece extends Piece {

    /**
     * Denna konstruktor skapar en ny spelarpjäs
     * @param row
     * @param col
     * @param owner
     * @author Noor-Fatima Nabi & Fauzia Bhuiyan
     */
    public PlayerPiece(int row, int col, int owner) {
        super(row, col, owner);
    }
}

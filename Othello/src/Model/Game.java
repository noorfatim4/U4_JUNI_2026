package Model;

import java.util.ArrayList;
import java.util.List;
import Shared.TileOwner;

/**
 * Game klassen hanterar turordning, placering av pjäser,
 * överraskningar och mysterium
 *
 * @author Noor-Fatima Nabi & Fauzia Bhuiyan
 */
public class Game {
    private Board board;
    private GameStatus status;
    private int currentPlayer = 1;
    private int score1 = 0;
    private int score2 = 0;
    private final HighscoreManager highscoreManager = new HighscoreManager();
    private int placedP1 = 0;
    private int placedP2 = 0;
    private boolean extraTurn     = false;
    private boolean skipNextTurn  = false;
    private String player1Name = "Player 1";
    private String player2Name = "Player 2";
    private int activatedMysteries = 0;
    private int totalMysteries = 0;
    private static final int[][] DIRECTIONS = {
            {-1, -1}, {-1, 0}, {-1, 1},
            {0,  -1},          {0,  1},
            {1,  -1}, {1,  0}, {1,  1}
    };

    /**
     * Denna konstruktor initierar spelplan
     * @author Fauzia Bhuiyan
     */
    public Game() {
        newGame();
    }

    /**
     * Denna metod återställer spelet och skapar en ny runda
     * @author Noor-Fatima Nabi
     */
    public void newGame() {
        board          = new Board(8, 8);
        status         = GameStatus.ACTIVE;
        currentPlayer  = 1;
        score1         = 0;
        score2         = 0;
        placedP1       = 0;
        placedP2       = 0;
        extraTurn      = false;
        skipNextTurn   = false;
        activatedMysteries = 0;
        totalMysteries = 0;
        placeMysteries();
    }

    /**
     * Denna metod placerar slumpmässigt ut minst 5 mysterier på spelplanen
     * @author Fauzia Bhuiyan & Noor-Fatima Nabi
     */
    private void placeMysteries() {
        int count = 0;
        int maxAttempts = 10_000;   // guard against infinite loop on tiny boards

        while (count < 5 && maxAttempts-- > 0) {
            int r = (int) (Math.random() * board.getRows());
            int c = (int) (Math.random() * board.getCols());

            if (!board.isEmpty(r, c))         continue;   // tile already taken
            if (board.isCorner(r, c))          continue;   // U4FG8: no corners
            if (board.hasNeighbourMystery(r, c)) continue; // U4FG5: not touching

            MysteryType type = MysteryType.randomType();   // U4FG6: random type
            board.placePiece(new MysteryPiece(r, c, type));
            count++;
        }
        totalMysteries = count;
    }

    /**
     * Denna metod placerar pjäs för aktuell spelare
     * @param row
     * @param col
     * @author Fauzia Bhuiyan & Noor-Fatima Nabi
     */
    public boolean placePiece(int row, int col) {
        if (status == GameStatus.FINISHED)
            return false;
        if (!board.isEmpty(row, col))
            return false;

        boolean isFirstMove = (currentPlayer == 1) ? (placedP1 == 0) : (placedP2 == 0);
        if (!isFirstMove && !board.hasNeighbourPiece(row, col)) {
            return false;
        }

        //placerar pjäs
        board.placePiece(new PlayerPiece(row, col, currentPlayer));
        if (currentPlayer == 1) placedP1++; else placedP2++;


        applyCaptures(row, col, currentPlayer);
        updateScores();

        if (isGameOver()) {
            status = GameStatus.FINISHED;
            int   winnerScore = getWinnerScore();
            String winnerName = getWinnerName();
            if (!winnerName.equals("Draw") && highscoreManager.qualifies(winnerScore)) {
            } return true;
        }
        handleTurnSwitch();
        return true;
    }

    /**
     * Denna metod utför överraskningar i alla 8 riktningar från en placerad pjäs
     * @param row
     * @param col
     * @param owner
     * @author Fauzia Bhuiyan & Noor-Fatima Nabi
     */
    private void applyCaptures(int row, int col, int owner) {
        for (int[] dir : DIRECTIONS) {
            tryCaptureDirection(row, col, owner, dir[0], dir[1]);
        }
    }

    /**
     * Denna metod söker igenom en riktning efter möjliga överraskningar.
     * @param row
     * @param col
     * @param owner
     * @param dr
     * @param dc
     * @author Fauzia Bhuiyan & Noor-Fatima Nabi
     */
    private void tryCaptureDirection(int row, int col, int owner, int dr, int dc) {
        List<PlayerPiece> toFlip = new ArrayList<>();
        int r = row + dr;
        int c = col + dc;

        while (board.withinArray(r, c)) {
            Piece p = board.getPiece(r, c);

            if (p == null) return;
            if (p instanceof MysteryPiece mp) {
                if (!mp.isActivated()) {
                    activateMystery(mp);
                }
                return;
            }

            if (p instanceof PlayerPiece pp) {
                if (pp.getOwner() != owner) {
                    toFlip.add(pp);           // opponent piece – mark for flipping
                } else {
                    // Own piece found – flip everything collected so far
                    for (PlayerPiece flip : toFlip) {
                        flip.setOwner(owner);
                    }
                    return;
                }
            }
            r += dr;
            c += dc;
        }
    }

    /**
     * Denna metod aktiverar ett mysterium och utför dess effekt
     * @param mp
     * @author Fauzia Bhuiyan & Noor-Fatima Nabi
     */
    private void activateMystery(MysteryPiece mp) {
        if (mp.isActivated()) return;
        mp.setActivated(true);
        activatedMysteries++;

        int row = mp.getRow();
        int col = mp.getCol();

        switch (mp.getType()) {
            case TIMEJUMP: {
                extraTurn = true;
            }
            case NARCISSUS: {
                skipNextTurn = true;
            }
            case ADDITIVE: {
                applyAdditive(row, col);
                updateScores();
            }
        }
        board.placePiece(new PlayerPiece(row, col, currentPlayer));
    }

    /**
     * Mysterium: Additive
     * @param row
     * @param col
     * @author Fauzia Bhuiyan & Noor-Fatima Nabi
     */
    private void applyAdditive(int row, int col) {
        int[][] positions = { {0,0}, {-1,0}, {1,0}, {0,-1}, {0,1} };
        for (int[] d : positions) {
            int r = row + d[0];
            int c = col + d[1];
            if (board.withinArray(r, c) && board.isEmpty(r, c)) {
                board.placePiece(new PlayerPiece(r, c, currentPlayer));
            }
        }
    }

    /**
     * Denna metod hanterar turbytet efter ett drag.
     * @author Fauzia Bhuiyan & Noor-Fatima Nabi
     */
    private void handleTurnSwitch() {
        if (extraTurn) {
            extraTurn = false;
            return;
        }
        switchPlayer();
        if (skipNextTurn) {
            skipNextTurn = false;
            switchPlayer();
        }
    }

    /**
     * Denna metod byter till nästa spelare
     * @author Fauzia Bhuiyan & Noor-Fatima Nabi
     */
    private void switchPlayer() {
        currentPlayer = (currentPlayer == 1) ? 2 : 1;
    }

    /**
     * Denna metod räknar om och uppdaterar båda spelarnas poäng.
     * @author Fauzia Bhuiyan & Noor-Fatima Nabi
     */
    private void updateScores() {
        score1 = 0;
        score2 = 0;
        for (int r = 0; r < board.getRows(); r++) {
            for (int c = 0; c < board.getCols(); c++) {
                Piece p = board.getPiece(r, c);
                if (p instanceof PlayerPiece pp) {
                    if (pp.getOwner() == 1) score1++;
                    else score2++;
                }
            }
        }
    }

    /**
     * Denna metod kontrollerar om spelet är slut
     * @return boolean
     * @author Fauzia Bhuiyan & Noor-Fatima Nabi
     */
    public boolean isGameOver() {
        return noEmptyTiles() || allMysteriesActivated();
    }

    /**
     * Denna metod kontrollerar om det finns några tomma platser kvar
     * @return boolean
     * @author Fauzia Bhuiyan & Noor-Fatima Nabi
     */
    private boolean noEmptyTiles() {
        for (int r = 0; r < board.getRows(); r++)
            for (int c = 0; c < board.getCols(); c++)
                if (board.getPiece(r, c) == null) return false;
        return true;
    }

    /**
     * Denna metod kontrollerar om alla mysterier har aktiverats
     * @return boolean
     * @author Fauzia Bhuiyan & Noor-Fatima Nabi
     */
    private boolean allMysteriesActivated() {
        return totalMysteries > 0 && activatedMysteries >= totalMysteries;
    }

    /**
     * Denna metod returnerar  spelplanens status som ett 2D array
     * @return TileOwner[][]
     * @author Fauzia Bhuiyan & Noor-Fatima Nabi
     */
    public TileOwner[][] getBoardStatus() {
        TileOwner[][] out = new TileOwner[board.getRows()][board.getCols()];
        for (int r = 0; r < board.getRows(); r++) {
            for (int c = 0; c < board.getCols(); c++) {
                Piece p = board.getPiece(r, c);
                if (p == null) {
                    out[r][c] = TileOwner.EMPTY;
                } else if (p instanceof MysteryPiece) {
                    out[r][c] = TileOwner.MYSTERY;
                } else if (p instanceof PlayerPiece pp) {
                    out[r][c] = (pp.getOwner() == 1) ? TileOwner.PLAYER1 : TileOwner.PLAYER2;
                }
            }
        }
        return out;
    }

    public int getCurrentPlayer()             {
        return currentPlayer; }

    public int getScorePlayer1()              {
        return score1; }

    public int getScorePlayer2()              {
        return score2; }

    public HighscoreManager getHighscoreManager() {
        return highscoreManager; }

    /**
     * Denna metod returnerar vinnarens poäng.
     * @author Noor-Fatima Nabi
     */
    public int getWinnerScore() {
        return Math.max(score1, score2);
    }

    /**
     * Denna metod returnerar vinnarens namn, eller "Draw" vid oavgjort
     * @author Noor-Fatima Nabi
     */
    public String getWinnerName() {
        if (score1 > score2) return player1Name;
        if (score2 > score1) return player2Name;
        return "Draw";
    }

    /**
     * Denna metod sätter spelarnas namn
     * @param name1
     * @param name2
     * @author Noor-Fatima Nabi
     */
    public void setPlayerNames(String name1, String name2) {
        this.player1Name = (name1 == null || name1.isBlank()) ? "Player 1" : name1;
        this.player2Name = (name2 == null || name2.isBlank()) ? "Player 2" : name2;
    }
}

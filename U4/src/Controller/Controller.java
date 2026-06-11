package Controller;

import Model.*;
import Shared.TileOwner;
import View.GameFrame;
import javax.swing.*;

/**
 * Controller klassen hanterar kommunikation mellan model och view paket
 *
 * @author Noor-Fatima Nabi & Fauzia Bhuiyan
 */
public class Controller {
    private Game game;
    private GameFrame view;

    /**
     * Denna kontruktor skapar ett nytt spel och ber om användarinput
     * @author Fauzia Bhuiyan
     */
    public Controller() {
        this.game = new Game();
    }

    /**
     * Denna metod sparar och visar spelpanelerna
     * @param view
     * @author Fauzia Bhuiyan
     */
    public void setView(GameFrame view) {
        this.view = view;
    }

    /**
     * Denna metod påbörjar spelet i programmet
     * @ author Noor-Fatima Nabi
     */
    public void startGame() {
        refreshView();
    }

    /**
     * Denna metod kontrollerar dragen på spelrutorna
     * @ param row
     * @ param col
     * @ author Noor-Fatima Nabi
     */
    public void tileClicked(int row, int col) {
        if (view == null) return;
        if (!game.placePiece(row, col)) {
            JOptionPane.showMessageDialog(view, "Invalid move ! Place next to a pawn !");
            return;
        }
        for (String msg : game.getPendingMysteryMessages()) {
            JOptionPane.showMessageDialog(view, msg, "Mysterium aktiverat!",
                    JOptionPane.INFORMATION_MESSAGE);
        }
        refreshView();
        if (game.isGameOver()) {
            showGameOver();
        }
    }

    /**
     * Denna metod påbörjar ett nytt spel utan att stänga fönstret
     * @ author Fauzia Bhuiyan
     */
    public void newGame() {
        game.newGame();
        String name1 = JOptionPane.showInputDialog(view, "Enter name for Player 1:");
        String name2 = JOptionPane.showInputDialog(view, "Enter name for Player 2:");
        game.setPlayerNames(name1, name2);
        refreshView();
    }

    /**
     * Denna metod uppdaterar fönstret med poäng
     * @ author Fauzia Bhuiyan & Noor-Fatima Nabi
     */
    private void refreshView() {
        TileOwner[][] boardStatus = game.getBoardStatus();
        view.getMainPanel().getBoardPanel().updateBoard(boardStatus);
        view.getMainPanel().updateInfo(
                game.getCurrentPlayer(),
                game.getScorePlayer1(),
                game.getScorePlayer2()
        );
        view.getMainPanel().getStatusPanel()
                .setCurrentPlayer("Player " + game.getCurrentPlayer());
        view.getMainPanel().getInfoPanel()
                .updateScores(game.getScorePlayer1(), game.getScorePlayer2());
        view.getMainPanel().getInfoPanel()
                .setHighscores(game.getHighscoreManager().getHighscoreStrings());
    }

    /**
     * Denna metod uppdaterar spelresultat
     * @ author Fauzia Bhuiyan & Noor-Fatima Nabi
     */
    private void showGameOver() {
        String winnerName  = game.getWinnerName();
        int    winnerScore = game.getWinnerScore();

        String message = winnerName.equals("Draw")
                ? "It's a draw! Both players have " + winnerScore + " points."
                : winnerName + " wins with " + winnerScore + " points!";

        JOptionPane.showMessageDialog(view, message, "Game Over", JOptionPane.INFORMATION_MESSAGE);

        if (!winnerName.equals("Draw") && game.getHighscoreManager().qualifies(winnerScore)) {
            String entered = JOptionPane.showInputDialog(
                    view,
                    "You made the highscore list!\n"
                  + "Enter your name (leave blank to use \"" + winnerName + "\"):",
                    winnerName
            );
            String finalName = (entered != null && !entered.isBlank()) ? entered : winnerName;
            game.getHighscoreManager().addScore(finalName, winnerScore);
            view.getMainPanel().getInfoPanel()
                    .setHighscores(game.getHighscoreManager().getHighscoreStrings());
        }
    }
}

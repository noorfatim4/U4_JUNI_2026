package View;

import Controller.Controller;
import javax.swing.*;
import java.awt.*;

/**
 * Main panel that assembles the board, status bar, info panel,
 * coordinate input fields, and New Game button.
 */
public class MainPanel extends JPanel {

    private final BoardPanel  boardPanel;
    private final InfoPanel   infoPanel;
    private final StatusPanel statusPanel;
    private final JLabel turnLabel;

    public MainPanel(Controller controller) {
        setLayout(new BorderLayout());

        boardPanel  = new BoardPanel(controller);
        infoPanel   = new InfoPanel();
        statusPanel = new StatusPanel();

        JPanel topBar = new JPanel(new GridLayout(1, 3));
        turnLabel         = new JLabel("Turn: Player 1", SwingConstants.CENTER);
        topBar.add(turnLabel);

        JPanel bottomBar = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton newGameBtn = new JButton("New Game");
        newGameBtn.addActionListener(e -> controller.newGame());
        bottomBar.add(newGameBtn);
        add(bottomBar, BorderLayout.SOUTH);

        add(topBar,      BorderLayout.NORTH);
        add(boardPanel,  BorderLayout.CENTER);
        add(infoPanel,   BorderLayout.EAST);
    }

    public BoardPanel  getBoardPanel()  { return boardPanel; }
    public InfoPanel   getInfoPanel()   { return infoPanel; }
    public StatusPanel getStatusPanel() { return statusPanel; }

    /** Updates the top score/turn labels. */
    public void updateInfo(int currentPlayer, int scoreP1, int scoreP2) {
        turnLabel.setText("Turn: Player " + currentPlayer);
    }
}

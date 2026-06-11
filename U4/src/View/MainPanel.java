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

        // --- Top bar: current player indicator ---
        JPanel topBar = new JPanel(new GridLayout(1, 3));
        turnLabel         = new JLabel("Turn: Player 1", SwingConstants.CENTER);
        topBar.add(turnLabel);

//        // --- Bottom bar: coordinate input (U4FG17) + New Game (U4FG22) ---
//        JPanel bottomBar = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 4));
//        bottomBar.add(new JLabel("Row:"));
//        JTextField rowField = new JTextField(3);
//        bottomBar.add(rowField);
//        bottomBar.add(new JLabel("Col:"));
//        JTextField colField = new JTextField(3);
//        bottomBar.add(colField);

//        JButton goBtn = new JButton("Place");
//        goBtn.addActionListener(e ->
//                controller.coordinateInput(rowField.getText(), colField.getText()));
//        bottomBar.add(goBtn);

//        bottomBar.add(Box.createHorizontalStrut(20));

        // U4FG22: New Game button (does NOT restart the application)
        JPanel bottomBar = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton newGameBtn = new JButton("New Game");
        newGameBtn.addActionListener(e -> controller.newGame());
        bottomBar.add(newGameBtn);
        add(bottomBar, BorderLayout.SOUTH);
//        bottomBar.add(newGameBtn);

        add(topBar,      BorderLayout.NORTH);
        add(boardPanel,  BorderLayout.CENTER);
        add(infoPanel,   BorderLayout.EAST);
//        add(bottomBar,   BorderLayout.SOUTH);
    }

    // -----------------------------------------------------------------------

    public BoardPanel  getBoardPanel()  { return boardPanel; }
    public InfoPanel   getInfoPanel()   { return infoPanel; }
    public StatusPanel getStatusPanel() { return statusPanel; }

    /** Updates the top score/turn labels. */
    public void updateInfo(int currentPlayer, int scoreP1, int scoreP2) {
        turnLabel.setText("Turn: Player " + currentPlayer);
    }
}

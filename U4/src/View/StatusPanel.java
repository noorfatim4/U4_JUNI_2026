package View;

import javax.swing.*;
import java.awt.*;

public class StatusPanel extends JPanel {

    private JLabel turnLabel;

    public StatusPanel() {
        setLayout(new FlowLayout(FlowLayout.LEFT));
        turnLabel = new JLabel("Turn: Player 1");
        add(turnLabel);
    }

    public void setCurrentPlayer(String playerName) {
        turnLabel.setText("Turn: " + playerName);
    }
}

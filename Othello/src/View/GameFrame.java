package View;

import Controller.Controller;
import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {

    private MainPanel mainPanel;

    public GameFrame(Controller controller) {
        setTitle("Omvälvning – Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        mainPanel = new MainPanel(controller);
        add(mainPanel, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public MainPanel getMainPanel() {
        return mainPanel;
    }
}

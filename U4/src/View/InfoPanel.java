package View;

import javax.swing.*;
import java.awt.*;

/**
 * Side panel showing live scores and the highscore list.
 * U4FG15, U4FG18.
 */
public class InfoPanel extends JPanel {

    private final JLabel scoreP1;
    private final JLabel scoreP2;
    private final DefaultListModel<String> listModel;

    public InfoPanel() {
        setPreferredSize(new Dimension(210, 0));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        // U4FG15: current scores always visible
        scoreP1 = new JLabel("Player 1 (Sonic): 0");
        scoreP2 = new JLabel("Player 2 (Shadow): 0");
        add(scoreP1);
        add(scoreP2);
        add(Box.createVerticalStrut(16));

        // Highscore list (U4FG18)
        JLabel hsTitle = new JLabel("Highscore Top 10");
        hsTitle.setFont(hsTitle.getFont().deriveFont(Font.BOLD));
        add(hsTitle);
        add(Box.createVerticalStrut(4));

        listModel = new DefaultListModel<>();
        JList<String> highscoreList = new JList<>(listModel);
        highscoreList.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scroll = new JScrollPane(highscoreList);
        scroll.setPreferredSize(new Dimension(190, 200));
        scroll.setMaximumSize(new Dimension(Integer.MAX_VALUE, 200));
        add(scroll);
    }

    public void updateScores(int p1, int p2) {
        scoreP1.setText("Player 1 (Sonic): " + p1);
        scoreP2.setText("Player 2 (Shadow): " + p2);
    }

    public void setHighscores(String[] scores) {
        listModel.clear();
        for (String s : scores) listModel.addElement(s);
    }
}

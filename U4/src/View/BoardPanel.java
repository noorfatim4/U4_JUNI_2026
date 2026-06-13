package View;

import Controller.Controller;
import Shared.TileOwner;
import javax.swing.*;
import java.awt.*;

public class BoardPanel extends JPanel {

    private static final int SIZE = 8;
    private TileButton[][] tiles;

    public BoardPanel(Controller controller) {
        setLayout(new GridLayout(SIZE, SIZE));
        setPreferredSize(new Dimension(400, 400));
        setBackground(Color.BLACK);

        tiles = new TileButton[SIZE][SIZE];

        for (int r = 0; r < SIZE; r++) {
            for (int c = 0; c < SIZE; c++) {
                TileButton btn = new TileButton(r, c, controller);
                btn.setStatus(TileOwner.EMPTY);
                tiles[r][c] = btn;
                add(btn);
            }
        }
    }

    public void updateBoard(TileOwner[][] status) {
        for (int r = 0; r < status.length; r++) {
            for (int c = 0; c < status[r].length; c++) {
//                if (status[r][c] == TileOwner.MYSTERY) {
//                }
                tiles[r][c].setStatus(status[r][c]);
            }
        }
        repaint();
    }
}

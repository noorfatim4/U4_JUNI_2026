package View;

import Controller.Controller;
import Shared.TileOwner;
import javax.swing.*;
import java.awt.*;

    public class TileButton extends JButton {

        private int row, col;
        private Controller controller;
        private TileOwner status;

        public TileButton(int row, int col, Controller controller) {
            this.row = row;
            this.col = col;
            this.controller = controller;

            setFocusPainted(false);
            setOpaque(true);
            setBorderPainted(true);
            setFont(new Font("Arial", Font.BOLD, 18));


            addActionListener(e -> controller.tileClicked(row, col));
        }

        public void setStatus(TileOwner status) {
            this.status = status;
            setIcon(null);
            setText("");

            switch (status) {
                case EMPTY -> {
                    setBackground(Color.GREEN);
                    setText("");
                }
                case PLAYER1 -> {
                    setBackground(Color.LIGHT_GRAY);
                    setIcon(ImageLoader.PLAYER1_ICON);
                    setText("");
                }
                case PLAYER2 -> {
                    setBackground(Color.LIGHT_GRAY);
                    setIcon(ImageLoader.PLAYER2_ICON);
                    setText("");
                }
                case MYSTERY -> {
                    setBackground(Color.YELLOW);
                    setText("?");
                }
            }
            repaint();
        }
    }
package Controller;
import View.GameFrame;
import javax.swing.SwingUtilities;

//fjfjfjfj

public class Main {

    /**
     * Denna metod påbörjar programmet
     * @param args
     * @author Noor-Fatima Nabi & Fauzia Bhuiyan
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Controller controller = new Controller();
            GameFrame frame = new GameFrame(controller);
            controller.setView(frame);
            controller.startGame();
        });
    }
}

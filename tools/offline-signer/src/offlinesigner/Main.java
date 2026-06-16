package offlinesigner;

import offlinesigner.ui.OfflineSignToolFrame;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            OfflineSignToolFrame frame = new OfflineSignToolFrame();
            frame.setVisible(true);
        });
    }
}
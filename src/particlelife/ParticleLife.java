package particlelife;

import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author arthu
 */
public class ParticleLife {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        World world = new World();
        JFrame frame = new JFrame();
        JPanel mainPanel = new JPanel();
        frame.setContentPane(mainPanel);

        mainPanel.setLayout(new BorderLayout());

        JPanel toolPanel = new JPanel();
        JButton playPauseButton = new JButton("play");
        playPauseButton.addActionListener((e) -> {
            if (world.isRunning()) {
                world.pause();
                playPauseButton.setText("play");
            } else {
                world.play();
                playPauseButton.setText("pause");
            }
        });
        toolPanel.add(playPauseButton);

        GraphicPanel graphicPanel = new GraphicPanel(world);
        world.addPropertyChangeListener(graphicPanel);

        mainPanel.add(toolPanel, BorderLayout.SOUTH);
        mainPanel.add(graphicPanel, BorderLayout.CENTER);

        frame.setVisible(true);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}

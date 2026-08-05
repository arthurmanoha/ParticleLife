package particlelife;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.JPanel;

/**
 *
 * @author arthu
 */
public class GraphicPanel extends JPanel implements PropertyChangeListener {

    private World w;

    private int preferredWidth = 800;
    private int preferredHeight = 600;

    private double x0, y0, zoom;

    public GraphicPanel(World world) {
        this.w = world;
        setPreferredSize(new Dimension(preferredWidth, preferredHeight));
        x0 = 10;
        y0 = 30;
        zoom = 6;
    }

    @Override
    public void paintComponent(Graphics g) {
        g.setColor(Color.black);
        g.fillRect(0, 0, g.getClipBounds().width, g.getClipBounds().height);

        for (Particle p : w.getParticles()) {
            p.paint(g, x0, y0, zoom);
        }

        paintReferential(g, x0, y0, zoom);
    }

    private void paintReferential(Graphics g, double x0, double y0, double zoom) {
        g.setColor(Color.white);
        int panelHeight = g.getClipBounds().height;

        g.drawLine((int) x0, (int) (panelHeight - y0), (int) (x0 + zoom * 10), (int) (panelHeight - y0));
        g.drawLine((int) x0, (int) (panelHeight - y0), (int) (x0), (int) ((panelHeight - (y0 + zoom * 10))));
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("step")) {
            repaint();
        }
    }
}

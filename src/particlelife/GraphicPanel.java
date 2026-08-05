package particlelife;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import javax.swing.JPanel;

/**
 *
 * @author arthu
 */
public class GraphicPanel extends JPanel implements MouseListener, MouseMotionListener, MouseWheelListener {

    private World w;

    private int preferredWidth = 800;
    private int preferredHeight = 600;

    private double x0, y0, zoom;

    private int xMouse, yMouse;
    private boolean isMouseClickActive;
    private int panelHeight;

    public GraphicPanel(World world) {
        this.w = world;
        setPreferredSize(new Dimension(preferredWidth, preferredHeight));
        x0 = 10;
        y0 = 30;
        zoom = 6;
        xMouse = 0;
        yMouse = 0;
        addMouseListener(this);
        addMouseMotionListener(this);
        addMouseWheelListener(this);
        isMouseClickActive = false;
    }

    @Override
    public void paintComponent(Graphics g) {
        g.setColor(Color.black);
        g.fillRect(0, 0, g.getClipBounds().width, g.getClipBounds().height);

        for (Particle p : w.getParticles()) {
            p.paint(g, x0, y0, zoom);
        }

        paintReferential(g, x0, y0, zoom);
        panelHeight = g.getClipBounds().height;
    }

    private void paintReferential(Graphics g, double x0, double y0, double zoom) {
        g.setColor(Color.white);
        int panelHeight = g.getClipBounds().height;

        g.drawLine((int) x0, (int) (panelHeight - y0), (int) (x0 + zoom * 10), (int) (panelHeight - y0));
        g.drawLine((int) x0, (int) (panelHeight - y0), (int) (x0), (int) ((panelHeight - (y0 + zoom * 10))));
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON2) {
            isMouseClickActive = true;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON2) {
            isMouseClickActive = false;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (isMouseClickActive) {
            x0 += e.getX() - xMouse;
            y0 += -(e.getY() - yMouse);
            xMouse = e.getX();
            yMouse = e.getY();
        }
        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        xMouse = e.getX();
        yMouse = e.getY();
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        int xCenter = e.getX();
        int yCenter = e.getY();

        double f = e.getWheelRotation() < 0 ? 1.1 : 1 / 1.1;

        x0 = f * (x0 - xCenter) + xCenter;
        y0 = f * (yCenter + y0 - panelHeight) + panelHeight - yCenter;

        zoom = zoom * f;
        repaint();
    }

}

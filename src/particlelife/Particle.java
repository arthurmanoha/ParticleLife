package particlelife;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

/**
 *
 * @author arthu
 */
public class Particle {

    private double x, y, radius;

    private static int NB_TYPES = 3;
    private int type;

    public Particle(double newX, double newY, double newRadius) {
        this.x = newX;
        this.y = newY;
        this.radius = newRadius;
        this.type = new Random().nextInt(NB_TYPES);
        System.out.println("type: " + type);
    }

    public void paint(Graphics g, double x0, double y0, double zoom) {

        int panelHeight = g.getClipBounds().height;

        int xApp = (int) (x0 + this.x * zoom);
        int yApp = (int) (panelHeight - (y0 + this.y * zoom));
        int rApp = (int) (this.radius * zoom);

        g.setColor(this.getColor());
        g.fillOval(xApp - rApp, yApp - rApp, 2 * rApp, 2 * rApp);
    }

    private Color getColor() {
        Color result = Color.gray;

        switch (this.type) {
        case 0:
            result = Color.blue;
            break;
        case 1:
            result = Color.red;
            break;
        case 2:
            result = Color.green;
            break;
        default:
            result = Color.gray;
            break;
        }
        return result;
    }
}

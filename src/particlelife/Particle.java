package particlelife;

import java.awt.Color;
import java.awt.Graphics;
import static java.lang.Math.sqrt;
import java.util.Random;

/**
 *
 * @author arthu
 */
public class Particle {

    private double x, y, radius;
    private double mass;
    private double vx, vy;
    private double fx, fy;

    private static int NB_TYPES = 3;
    private int type;

    private static double constantG = 1.0;

    private static int NB_PARTICLES_CREATED = 0;
    private int id;

    // Particles closer than this will not interact.
    private double neutralDistance = 1.0;

    public Particle(double newX, double newY, double newRadius) {
        this.x = newX;
        this.y = newY;
        this.radius = newRadius;

        this.mass = 1;
        this.vx = 0;
        this.vy = 0;
        this.fx = 0;
        this.fy = 0;

        this.id = NB_PARTICLES_CREATED;
        NB_PARTICLES_CREATED++;

        this.type = new Random().nextInt(NB_TYPES);
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

    protected void resetForces() {
        fx = 0;
        fy = 0;
    }

    protected void computeForce(Particle p) {
        if (p != this) {
            double distance = getDistance(p);

            if (distance > neutralDistance) {

                double force = constantG * this.mass * p.mass / (distance * distance);
                double dx = (p.x - this.x) / distance;
                double dy = (p.y - this.y) / distance;

                fx += force * dx;
                fy += force * dy;
            }
        }
    }

    private double getDistance(Particle p) {
        double dx = p.x - this.x;
        double dy = p.y - this.y;
        return sqrt(dx * dx + dy * dy);
    }

    protected void updateSpeed(double dt) {
        this.vx += this.fx * dt / mass;
        this.vy += this.fy * dt / mass;
    }

    protected void move(double dt) {
        this.x += this.vx * dt;
        this.y += this.vy * dt;
    }

}

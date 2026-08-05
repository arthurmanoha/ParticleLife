package particlelife;

import java.awt.Color;
import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import static java.lang.Math.max;
import static java.lang.Math.sqrt;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author arthu
 */
public class Particle {

    private double x, y, radius;
    private double mass;
    private double vx, vy;
    private double fx, fy;

    private static int NB_TYPES = 1;
    private int type;

    private static double constantG = 0.03;

    private static int NB_PARTICLES_CREATED = 0;
    private int id;

    // Particles closer than this will not interact.
    private double minRadius = 1.0;
    // Particles farther away than this will not interact.
    private double maxRadius = 5.0;

    private static double[][] attractionArray = null;
    private static double attractionCoef = 0.1;
    private static double friction = 0;

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
        loadAttractions();
    }

    public void paint(Graphics g, double x0, double y0, double zoom) {

        int panelHeight = g.getClipBounds().height;

        int xApp = (int) (x0 + this.x * zoom);
        int yApp = (int) (panelHeight - (y0 + this.y * zoom));
        int rApp = (int) (this.radius * zoom);

        g.setColor(this.getColor());
        g.fillOval(xApp - rApp, yApp - rApp, max(2 * rApp, 2), max(2 * rApp, 2));
    }

    private Color getColor() {
        Color result = Color.gray;

        switch (this.type) {
        case 0:
            result = Color.red;
            break;
        case 1:
            result = Color.green;
            break;
        case 2:
            result = Color.blue;
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

            if (distance > minRadius && distance < maxRadius) {

                double factor = attractionArray[this.type][p.type] * attractionCoef;

                double force = factor * constantG * this.mass * p.mass / (distance * distance);
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

        // Friction
        this.vx = (1 - friction) * this.vx;
        this.vy = (1 - friction) * this.vy;
    }

    protected void move(double dt) {
        this.x += this.vx * dt;
        this.y += this.vy * dt;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    protected void setX(double newX) {
        x = newX;
    }

    protected void setY(double newY) {
        y = newY;
    }

    public double getVx() {
        return this.vx;
    }

    public double getVy() {
        return this.vy;
    }

    protected void setVx(double newVx) {
        this.vx = newVx;
    }

    protected void setVy(double newVy) {
        this.vy = newVy;
    }

    private static void loadAttractions() {
        int nbTypes = 0;

        if (attractionArray == null) {
            String filename = "src/particlelife/particle_behavior.txt";
            try {
                BufferedReader reader = new BufferedReader(new FileReader(new File(filename)));

                String line;
                line = reader.readLine();
                if (line.split("\t")[0].equals("nbTypes")) {
                    nbTypes = Integer.valueOf(line.split("\t")[1]);
                    attractionArray = new double[nbTypes][];
                    for (int row = 0; row < nbTypes; row++) {
                        attractionArray[row] = new double[nbTypes];
                    }
                    line = reader.readLine(); // Ignore this line, it is only the list of types numbers.
                }
                int row = 0;
                while ((line = reader.readLine()) != null) {
                    String[] split = line.split("\t");
                    if (split[0].equals("friction")) {
                        friction = Double.valueOf(split[1]);
                        System.out.println("friction: " + friction);
                    } else {
                        for (int col = 0; col < nbTypes; col++) {
                            double newVal = Double.valueOf(split[col + 1]);
                            attractionArray[row][col] = newVal;
                        }
                    }
                    row++;
                }

            } catch (FileNotFoundException ex) {
                System.out.println("file " + filename + " not found.");
            } catch (IOException ex) {
                Logger.getLogger(Particle.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}

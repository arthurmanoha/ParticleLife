package particlelife;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * This class contains all the particles.
 *
 * @author arthu
 */
public class World {

    private ArrayList<Particle> allparticles;

    private boolean isRunning;
    private Timer timer;
    private long timerPeriod = 10; // ms
    private double dt = 0.1;

    private PropertyChangeSupport pcs;
    private int step = 0;

    private int nbParticlesInit = 50;

    // Maximum x or y available to the particles
    private double xMin = -10;
    private double xMax = 10;
    private double yMin = -5;
    private double yMax = 5;

    public World() {

        allparticles = new ArrayList<>();
        initializeRandomParticles();
        isRunning = false;
        timer = null;
        pcs = new PropertyChangeSupport(this);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }

    public ArrayList<Particle> getParticles() {
        return allparticles;
    }

    private void initializeRandomParticles() {
        Random r = new Random();
        for (int i = 0; i < nbParticlesInit; i++) {

            double x = r.nextDouble() * (xMax - xMin) + xMin;
            double y = r.nextDouble() * (yMax - yMin) + yMin;

            allparticles.add(new Particle(x, y, 0.1));
        }
    }

    protected boolean isRunning() {
        return isRunning;
    }

    protected void pause() {
        setIsRunning(false);
    }

    protected void play() {
        setIsRunning(true);
    }

    private void setIsRunning(boolean b) {
        isRunning = b;
        System.out.println("running: " + (b ? "yes" : "no"));
        long delay = 0;

        if (isRunning) {
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    step();
                }

            }, delay, timerPeriod);
        } else {
            timer.cancel();
        }
    }

    private void step() {
        for (Particle p : allparticles) {
            p.resetForces();
        }

        // Brute-force version, needs refining
        for (Particle p : allparticles) {
            for (Particle other : allparticles) {
                p.computeForce(other);
            }
        }

        for (Particle p : allparticles) {
            p.updateSpeed(dt);
        }
        // Move particles
        for (Particle p : allparticles) {
            p.move(dt);
        }

        for (Particle p : allparticles) {
            computeWallCollisions(p);
        }
        step++;
        pcs.firePropertyChange("step", step - 1, step);
    }

    private void computeWallCollisions(Particle p) {
        if (p.getX() > xMax && p.getVx() > 0 || p.getX() < xMin && p.getVx() < 0) {
            p.setVx(-p.getVx());
        }
        if (p.getY() > yMax && p.getVy() > 0 || p.getY() < yMin && p.getVy() < 0) {
            p.setVy(-p.getVy());
        }
    }

    public double getXMin() {
        return xMin;
    }

    public double getXMax() {
        return xMax;
    }

    public double getYMin() {
        return yMin;
    }

    public double getYMax() {
        return yMax;
    }
}

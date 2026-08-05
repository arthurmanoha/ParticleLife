package particlelife;

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
    private long timerPeriod;

    public World() {

        allparticles = new ArrayList<>();
        initializeRandomParticles();
        isRunning = false;
        timer = null;
        timerPeriod = 1000;
    }

    public ArrayList<Particle> getParticles() {
        return allparticles;
    }

    private void initializeRandomParticles() {
        Random r = new Random();
        for (int i = 0; i < 10; i++) {

            double x = r.nextDouble() * 100;
            double y = r.nextDouble() * 100;

            allparticles.add(new Particle(x, y, 1));
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
        System.out.println("World.step()");
    }
}

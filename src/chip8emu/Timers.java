package chip8emu;

import java.util.Timer;
import java.util.TimerTask;

public class Timers {
    
    private int delay; //delay timer
    private int sound; //sound timer
    private Timer timer;
    private Countdown task;
    
    public Timers() {
        delay = 0;
        sound = 0;
        timer = new Timer();
        task = new Countdown();
        timer.scheduleAtFixedRate(task, 1, 1);
    }
    
    //methods
    
    public int getDelay() {
        return delay;
    }
    
    public int getSound() {
        return sound;
    }
    
    public void setDelay(int x) {
        delay = x;
    }
    
    public void setSound(int x) {
        sound = x;
    }
    
    public void decrement() {
        if (task.check()) {
            if (delay > 0)
                delay--;
            if (sound > 0)
                sound--;
        }
        /*if (delay > 0)
            delay--;
        if (sound > 0)
            sound--;*/
    }
    
}

package chip8emu;

import java.util.TimerTask;

public class Countdown extends TimerTask {
    
    private int x;
    private boolean tick;
    private int third;
    private int total;
    
    public Countdown() {
        x = 17;
        tick = false;
        third = 0;
        total = 0;
    }
    
    public boolean check() {
        System.out.print("");
        return tick;
    }
    
    public void run() {
        if (x == 0) {
            if (third == 2) x = 16;
            else x = 17;
            tick = true;
            //System.out.println("tick!");
        }
        else
            tick = false;
        x--;
        
        total++;
        if (total%1000 == 0)
            System.out.println((total/1000) + " seconds elapsed.");
    }
    
}

package chip8emu;

public class Timers {
    
    private int delay; //delay timer
    private int sound; //sound timer
    
    public Timers() {
        delay = 0;
        sound = 0;
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
        if (delay > 0)
            delay--;
        if (sound > 0)
            sound--;
    }
    
}

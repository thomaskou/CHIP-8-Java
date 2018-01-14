package chip8emu;

public class Display {
    
    private byte[][] gfx = new byte[64][32];
    
    public Display() {
        displayClear();
    }
    
    //methods
    
    public byte get(int x, int y) {
        return gfx[x][y];
    }
    
    public void set(int x, int y, byte i) {
        gfx[x][y] = i;
    }
    
    public void displayClear() {
        for (byte[] x : gfx)
            for (byte y : x)
                y = 0;
    }
    
}
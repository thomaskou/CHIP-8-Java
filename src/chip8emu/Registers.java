package chip8emu;

public class Registers {
    
    private int[] V = new int[16];
    //V[15] is for carry, borrow, shift, overflow, collision detection
    
    public Registers() {
        for (int k : V)
            k = 0;
    }
    
    //methods
    
    public int get(int k) {
        return V[k];
    }
    
    public void set(int k, int x) {
        V[k] = x;
    }
    
}

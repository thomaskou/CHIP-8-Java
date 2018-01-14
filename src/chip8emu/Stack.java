package chip8emu;

public class Stack {
    
    private int[] stack = new int[16];
    private int pointer; //stack pointer
    
    public Stack() {
        for (int k : stack)
            k = 0;
    }
    
    //methods
    
    public int get(int k) {
        return stack[k];
    }
    
    public void set(int k, int x) {
        stack[k] = x;
    }
    
    public int getP() {
        return pointer;
    }
    
    public void setP(int x) {
        pointer = x;
    }
    
    public void up() {
        pointer++;
    }
    
    public void down() {
        pointer--;
    }
    
}
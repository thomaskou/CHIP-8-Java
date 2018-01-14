package chip8emu;

public class Input {
    
    private byte[] input = new byte[16];
    
    public Input() {
        for (byte k : input)
            k = 0;
    }
    
    //methods
    
    public byte get(int k) {
        return input[k];
    }
    
    public void set(int k, byte x) {
        input[k] = x;
    }
    
}

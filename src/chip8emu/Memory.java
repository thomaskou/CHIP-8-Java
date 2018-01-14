package chip8emu;

public class Memory {
    
    private byte[] memory = new byte[4096];
    //first 512 bytes -> CHIP-8 interpreter
    //last 256 bytes -> display refresh
    //96 bytes below -> call stack, internal use, etc.
    
    public Memory() {
        for (byte k : memory)
            k = 0;
        initializeFonts();
    }
    
    //methods
    
    public byte get(int k) {
        return memory[k];
    }
    
    public void set(int k, byte x) {
        memory[k] = x;
    }
    
    public String getHex(short k) {
        //return byte as hex string (thanks stackoverflow)
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%02X", memory[k]));
        return sb.toString();
    }
    
    //font initialization
    //fonts are stored in memory from 0x50 to 0x9F (80 to 159)
    
    private void initializeFonts() {
        
        byte[] fontset = {
            (byte)0xF0, (byte)0x90, (byte)0x90, (byte)0x90, (byte)0xF0, // 0
            (byte)0x20, (byte)0x60, (byte)0x20, (byte)0x20, (byte)0x70, // 1
            (byte)0xF0, (byte)0x10, (byte)0xF0, (byte)0x80, (byte)0xF0, // 2
            (byte)0xF0, (byte)0x10, (byte)0xF0, (byte)0x10, (byte)0xF0, // 3
            (byte)0x90, (byte)0x90, (byte)0xF0, (byte)0x10, (byte)0x10, // 4
            (byte)0xF0, (byte)0x80, (byte)0xF0, (byte)0x10, (byte)0xF0, // 5
            (byte)0xF0, (byte)0x80, (byte)0xF0, (byte)0x90, (byte)0xF0, // 6
            (byte)0xF0, (byte)0x10, (byte)0x20, (byte)0x40, (byte)0x40, // 7
            (byte)0xF0, (byte)0x90, (byte)0xF0, (byte)0x90, (byte)0xF0, // 8
            (byte)0xF0, (byte)0x90, (byte)0xF0, (byte)0x10, (byte)0xF0, // 9
            (byte)0xF0, (byte)0x90, (byte)0xF0, (byte)0x90, (byte)0x90, // A
            (byte)0xE0, (byte)0x90, (byte)0xE0, (byte)0x90, (byte)0xE0, // B
            (byte)0xF0, (byte)0x80, (byte)0x80, (byte)0x80, (byte)0xF0, // C
            (byte)0xE0, (byte)0x90, (byte)0x90, (byte)0x90, (byte)0xE0, // D
            (byte)0xF0, (byte)0x80, (byte)0xF0, (byte)0x80, (byte)0xF0, // E
            (byte)0xF0, (byte)0x80, (byte)0xF0, (byte)0x80, (byte)0x80  // F
        };
        
        for (int k = 0; k < 80; k++) {
            memory[k + 80] = fontset[k];
        }
        
    }
    
}

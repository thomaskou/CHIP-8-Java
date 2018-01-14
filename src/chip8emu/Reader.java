package chip8emu;

import java.io.File;
import java.nio.file.Files;

public class Reader {
    
    public byte[] romToByteArray(String game) throws Exception {
        File file = new File("src/chip8emu/games/" + game);
        return Files.readAllBytes(file.toPath());
    }
    
}
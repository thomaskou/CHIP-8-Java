package chip8emu;

import javax.swing.*;

public class chip8emu {

    public static void main(String[] args) {
        
        //SETUP GRAPHICS, SOUND, INPUT
        JFrame f = new JFrame("CHIP-8 Emulator");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Panel p = new Panel();
        f.add(p);
        f.setSize(2560,1280);
        f.setVisible(true);
        
        //INITIALIZATION
        Game game = new Game("game");
        
        //EMULATION LOOP
        for (;;) {
            game.cycle();
            if (game.drawFlag);
            game.setKeys();
        }
        
    }
    
}
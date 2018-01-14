package chip8emu;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class chip8emu {
    
    static Panel p;
    static byte[][] gfx;
    static Game game;
    static JFrame f = new JFrame("CHIP-8 Emulator");

    public static void main(String[] args) {
        
        //SETUP GRAPHICS, SOUND, INPUT
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gfx = new byte[64][32];
        for (byte[] x : gfx)
            for (byte y : x)
                y = 0;
        
        //INITIAL DRAW
        p = new Panel(gfx);
        f.add(p);
        f.setSize(2586,1350); //2560x1280
        f.setVisible(true);
        
        //GAME INITIALIZATION
        game = new Game("PONG");
        
        //EMULATION LOOP
        for (;;) {
            game.cycle();
            if (game.drawFlag) draw();
            game.setKeys();
        }
        
    }
    
    public static void draw() {
        gfx = game.gfx.getGfx();
        f.remove(p);
        p = new Panel(gfx);
        f.add(p);
        game.drawFlag = false;
    }
    
}
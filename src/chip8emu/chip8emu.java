package chip8emu;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Timer;
import java.util.TimerTask;

public class chip8emu {
    
    static Panel p;
    static byte[][] gfx;
    static Game game;
    static JFrame f = new JFrame("CHIP-8 Emulator");
    
    //timers
    //static Timer timer;
    //static Countdown task;

    public static void main(String[] args) {
        
        //SETUP GRAPHICS, SOUND, INPUT
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gfx = new byte[64][32];
        for (byte[] x : gfx)
            for (byte y : x)
                y = 0;
        
        //TIMERS
        //timer = new Timer();
        //task = new Countdown();
        //timer.scheduleAtFixedRate(task, 1, 1);
        
        //GAME INITIALIZATION
        game = new Game("INVADERS");
        
        //INITIAL DRAW
        p = new Panel(game.gfx);
        f.setSize(2586,1350); //2560x1280
        f.setVisible(true);
        f.add(p);
        
        //EMULATION LOOP
        for (;;) {
            /*if (task.check()) {
                game.cycle();
                if (game.drawFlag) draw();
                game.setKeys();
            }*/
            game.cycle();
            if (game.drawFlag) draw();
            game.setKeys();
        }
        
    }
    
    public static void draw() {
        gfx = game.gfx.getGfx();
        game.drawFlag = false;
        f.repaint();
        //System.out.println("draw!");
    }
    
}
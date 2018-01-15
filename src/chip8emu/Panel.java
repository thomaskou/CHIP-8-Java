package chip8emu;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Panel extends JPanel {
    
    private Display gfx;
    
    public Panel(Display gfx) {
        this.gfx = gfx;
    }
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.setBackground(Color.BLACK);
        g.setColor(Color.WHITE);
        
        for (int c = 0; c < 64; c++) {
            for (int r = 0; r < 32; r++) {
                if (gfx.getGfx()[c][r] == 1)
                    g.fillRect(c*40, r*40, 40, 40);
            }
        }
    }
    
}

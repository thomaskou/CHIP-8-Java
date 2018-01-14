package chip8emu;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Panel extends JPanel {
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.setBackground(Color.BLACK);
    }
    
}

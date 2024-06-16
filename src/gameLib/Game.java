package gameLib;

import javax.swing.JFrame;
import java.awt.Graphics;

public class Game {
    private Graphics graphics;
    private JFrame frame;

    // Constructor
    public Game(Graphics graphics, JFrame frame) {
        this.graphics = graphics;
        this.frame = frame;
    }

    // Getter for graphics
    public Graphics getGraphics() {
        return graphics;
    }

    // Setter for graphics
    public void setGraphics(Graphics graphics) {
        this.graphics = graphics;
    }

    // Getter for frame
    public JFrame getFrame() {
        return frame;
    }

    // Setter for frame
    public void setFrame(JFrame frame) {
        this.frame = frame;
    }
}
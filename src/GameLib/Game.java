package GameLib;

import javax.swing.JFrame;
import java.awt.Graphics;

public class Game {
    private Graphics Graphics;
    private JFrame Frame;

    // Constructor
    public Game(Graphics Graphics, JFrame Frame) {
        this.Graphics = Graphics;
        this.Frame = Frame;
    }

    // Getter for Graphics
    public Graphics getGraphics() {
        return Graphics;
    }

    // Setter for Graphics
    public void setGraphics(Graphics Graphics) {
        this.Graphics = Graphics;
    }

    // Getter for JFrame
    public JFrame getFrame() {
        return Frame;
    }

    // Setter for JFrame
    public void setFrame(JFrame Frame) {
        this.Frame = Frame;
    }
}
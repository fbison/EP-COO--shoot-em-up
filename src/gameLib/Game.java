package gameLib;

import javax.swing.JFrame;
import java.awt.*;

public class Game {
    private MyFrame frame = null;
    private static Graphics graphics = null;

    // Constructor
    public Game(Graphics graphics, MyFrame frame) {
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
    public MyFrame getFrame() {
        return frame;
    }

    // Setter for frame
        public void setFrame(MyFrame frame) {
        this.frame = frame;
    }

    public static void setColor(Color c){
        graphics.setColor(c);
    }
}
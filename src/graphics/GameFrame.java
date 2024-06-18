package graphics;

import gameLib.Game;
import gameLib.Util;
import gameLib.MyFrame;

import java.awt.Graphics;
import java.util.ArrayList;

public class GameFrame extends Game {
    // Attributes
    private Background backgroundGame;
    private KeyAdapterGame keyAdapterGame;

    public GameFrame(Graphics graphics, MyFrame frame) {
        super(graphics, frame);
    }

    // Methods
    public void initGraphics() {
        this.getFrame().setDefaultCloseOperation(MyFrame.EXIT_ON_CLOSE);
        this.getFrame().setSize(Util.WIDTH, Util.HEIGHT);
        this.getFrame().setResizable(false);
        this.getFrame().setVisible(true);
        this.getFrame().addKeyListener(this.keyAdapterGame);
        this.getFrame().requestFocus();
        this.getFrame().createBufferStrategy(2);
        this.setGraphics(this.getFrame().getBufferStrategy().getDrawGraphics());
    }

    public void fillRect(double cx, double cy, double width, double height) {
        int x = (int) Math.round(cx - width/2);
        int y = (int) Math.round(cy - height/2);

        this.getGraphics().fillRect(x, y, (int) Math.round(width), (int) Math.round(height));
    }
}
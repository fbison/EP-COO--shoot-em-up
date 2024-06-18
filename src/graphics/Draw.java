package graphics;

import gameLib.Game;
import gameLib.MyFrame;
import gameLib.Util;

import javax.swing.JFrame;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Toolkit;

public class Draw extends Game {
    // Attributes
    private Background backgroundGame;

    public Draw(Graphics graphics, MyFrame frame, Background backgroundGame) {
        super(graphics, frame);
        this.backgroundGame = backgroundGame;
    }

    public Background getBackgroundGame() {
        return backgroundGame;
    }

    public void setBackgroundGame(Background backgroundGame) {
        this.backgroundGame = backgroundGame;
    }

    // Methods
    public void display() {
        this.getGraphics().dispose();
        this.getFrame().getBufferStrategy().show();
        Toolkit.getDefaultToolkit().sync();
        this.setGraphics(this.getFrame().getBufferStrategy().getDrawGraphics());

        this.getGraphics().setColor(Color.BLACK);
        this.getGraphics().fillRect(0, 0, this.getFrame().getWidth() - 1, this.getFrame().getHeight() - 1);
        this.getGraphics().setColor(Color.WHITE);
    }

    public void scene() {
        // Method implementation
    }

    public void background() {
        // Method implementation
    }

    public void line(double x1, double y1, double x2, double y2) {
        this.getGraphics().drawLine((int) Math.round(x1), (int) Math.round(y1), (int) Math.round(x2), (int) Math.round(y2));
    }

    public void diamond(double x, double y, double radius) {
        int x1 = (int) Math.round(x);
        int y1 = (int) Math.round(y - radius);

        int x2 = (int) Math.round(x + radius);
        int y2 = (int) Math.round(y);

        int x3 = (int) Math.round(x);
        int y3 = (int) Math.round(y + radius);

        int x4 = (int) Math.round(x - radius);
        int y4 = (int) Math.round(y);

        line(x1, y1, x2, y2);
        line(x2, y2, x3, y3);
        line(x3, y3, x4, y4);
        line(x4, y4, x1, y1);
    }

    public void circle(double cx, double cy, double radius) {
        int x = (int) Math.round(cx - radius);
        int y = (int) Math.round(cy - radius);
        int width = (int) Math.round(2 * radius);
        int height = (int) Math.round(2 * radius);
        this.getGraphics().drawOval(x, y, width, height);
    }

    public void player(double player_X, double player_Y, double player_size) {
        line(player_X - player_size, player_Y + player_size, player_X, player_Y - player_size);
        line(player_X + player_size, player_Y + player_size, player_X, player_Y - player_size);
        line(player_X - player_size, player_Y + player_size, player_X, player_Y + player_size * 0.5);
        line(player_X + player_size, player_Y + player_size, player_X, player_Y + player_size * 0.5);
    }

    public void explosion(double x, double y, double alpha) {
        int p = 5;
        int r = (int) (255 - Math.pow(alpha, p) * 255);
        int g = (int) (128 - Math.pow(alpha, p) * 128);
        int b = 0;

        this.getGraphics().setColor(new Color(r, g, b));
        circle(x, y, alpha * alpha * 40);
        circle(x, y, alpha * alpha * 40 + 1);
    }

    public void stars(int numStars, int countStars) {
        for (int i = 0; i < numStars; i++) {
            this.backgroundGame.getCoordinateX().add(Math.random() * Util.WIDTH);
            this.backgroundGame.getCoordinateY().add(Math.random() * Util.WIDTH);
        }
    }
}
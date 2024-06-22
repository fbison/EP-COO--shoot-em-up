package gameComponents.scenario;

import graphics.GameLib;
import graphics.Util;

import java.awt.Color;
import java.util.ArrayList;

public class BackgroundStars {
    private ArrayList<Star> stars;
    private double count;

    public BackgroundStars(ArrayList<Star> stars, double count) {
        this.stars = stars;
        this.count = count;
    }

    public BackgroundStars(double speed, double count, int numStars, Color color) {
        this.stars = new ArrayList<Star>(numStars);
        for (int i = 0; i < numStars; i++) {
            var star = new Star((Math.random() * Util.WIDTH), (Math.random() * Util.HEIGHT), speed, color);
            stars.add(star);
        }
        this.count = count;
    }

    public ArrayList<Star> getStars() {
        return stars;
    }

    public void setStars(ArrayList<Star> stars) {
        this.stars = stars;
    }

    public double getCount() {
        return count;
    }

    public void setCount(double count) {
        this.count = count;
    }

    public void updateCount(long delta) {
        this.count += stars.getFirst().getSpeed() * delta;
    }

    public void update(long delta) {
        GameLib.setColor(stars.getFirst().getColor());
        updateCount(delta);
        for (Star star : stars) {
            GameLib.fillRect(star.getCoordinateX(), (star.getCoordinateY() + getCount()) % Util.HEIGHT, 2, 2);
        }
    }
}

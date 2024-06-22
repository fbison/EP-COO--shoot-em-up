package components;

import graphics.Util;

import java.util.ArrayList;

public class BackgroundStars {
    private ArrayList<Star> stars;
    private double count;

    public BackgroundStars(ArrayList<Star> stars, double count) {
        this.stars = stars;
        this.count = count;
    }

    public BackgroundStars(double speed, double count, int numStars) {
        this.stars = new ArrayList<Star>(numStars);
        for (int i = 0; i < numStars; i++) {
            var star = new Star((Math.random() * Util.WIDTH), (Math.random() * Util.HEIGHT), speed);
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
}

package graphics;

import java.util.ArrayList;

public class Background {
    // Attributes
    private ArrayList<Double> coordinateX;
    private ArrayList<Double> coordinateY;
    private double speed;
    private double count;

    // Constructor
    public Background(ArrayList<Double> coordinateX, ArrayList<Double> coordinateY, double speed, double count) {
        this.coordinateX = coordinateX;
        this.coordinateY = coordinateY;
        this.speed = speed;
        this.count = count;
    }

    public ArrayList<Double> getCoordinateX() {
        return coordinateX;
    }

    public void setCoordinateX(ArrayList<Double> coordinateX) {
        this.coordinateX = coordinateX;
    }

    public ArrayList<Double> getCoordinateY() {
        return coordinateY;
    }

    public void setCoordinateY(ArrayList<Double> coordinateY) {
        this.coordinateY = coordinateY;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getCount() {
        return count;
    }

    public void setCount(double count) {
        this.count = count;
    }

    public void initializeStars() {
        for (int i = 0; i < coordinateX.size(); i++) {
            coordinateX.set(i, Math.random() * Util.WIDTH);
        }
        for (int i = 0; i < coordinateY.size(); i++) {
            coordinateY.set(i, Math.random() * Util.HEIGHT);
        }
    }
}
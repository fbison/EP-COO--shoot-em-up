package components;

import gameLib.Util;

import java.util.ArrayList;

public class Component {
    // Attributes
    private int state;
    private double coordinateX;
    private double coordinateY;
    private double speedX;
    private double speedY;
    private double radius;

    // Constructor
    public Component(int state, double coordinateX, double coordinateY, double speedX, double speedY, double radius) {
        this.radius = radius;
        this.state = state;
        this.coordinateX = coordinateX;
        this.coordinateY = coordinateY;
        this.speedX = speedX;
        this.speedY = speedY;
    }

    // Methods
    public void updateState() {
        // Method implementation
    }

    public void setColor() {
        // Method implementation
    }

    // Getters and setters
    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public double getCoordinateX() {
        return coordinateX;
    }

    public void setCoordinateX(double coordinateX) {
        this.coordinateX = coordinateX;
    }

    public double getCoordinateY() {
        return coordinateY;
    }

    public void setCoordinateY(double coordinateY) {
        this.coordinateY = coordinateY;
    }

    public double getSpeedX() {
        return speedX;
    }

    public void setSpeedX(double speedX) {
        this.speedX = speedX;
    }

    public double getSpeedY() {
        return speedY;
    }

    public void setSpeedY(double speedY) {
        this.speedY = speedY;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }
}
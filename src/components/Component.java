package components;

public class Component extends GameComponent{
    // Attributes
    private int state;

    private double speedX;
    private double speedY;
    private double radius;

    // Constructor
    public Component(int state, double coordinateX, double coordinateY, double speedX, double speedY, double radius) {
        super(coordinateX, coordinateY);
        this.radius = radius;
        this.state = state;
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
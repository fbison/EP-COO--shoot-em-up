package Components;

public class Component {
    // Attributes
    private int State;
    private double CoordinateX;
    private double CoordinateY;
    private double SpeedX;
    private double SpeedY;

    // Constructor
    public Component(int state, double coordinateX, double coordinateY, double speedX, double speedY) {
        this.State = state;
        this.CoordinateX = coordinateX;
        this.CoordinateY = coordinateY;
        this.SpeedX = speedX;
        this.SpeedY = speedY;
    }

    // Methods
    public void UpdateState() {
        // Method implementation
    }

    public void SetColor() {
        // Method implementation
    }

    // Getters and setters
    public int getState() {
        return State;
    }

    public void setState(int state) {
        State = state;
    }

    public double getCoordinateX() {
        return CoordinateX;
    }

    public void setCoordinateX(double coordinateX) {
        CoordinateX = coordinateX;
    }

    public double getCoordinateY() {
        return CoordinateY;
    }

    public void setCoordinateY(double coordinateY) {
        CoordinateY = coordinateY;
    }

    public double getSpeedX() {
        return SpeedX;
    }

    public void setSpeedX(double speedX) {
        SpeedX = speedX;
    }

    public double getSpeedY() {
        return SpeedY;
    }

    public void setSpeedY(double speedY) {
        SpeedY = speedY;
    }
}
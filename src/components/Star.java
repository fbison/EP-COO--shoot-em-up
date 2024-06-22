package components;

public class Star extends GameComponent {
    private double speed;

    public Star(double coordinateX, double coordinateY, double speed) {
        super(coordinateX, coordinateY);
        this.speed = speed;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }
}

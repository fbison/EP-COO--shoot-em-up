package Components;

public class Enemy extends Character {
    // Additional attributes
    private double Angle;
    private double RotationSpeed;
    private long Speed;

    // Constructor
    public Enemy(int state, double coordinateX, double coordinateY, double speedX, double speedY,
                 double radius, double explosionStart, double explosionEnd, long nextShoot,
                 double angle, double rotationSpeed, long speed) {
        super(state, coordinateX, coordinateY, speedX, speedY, radius, explosionStart, explosionEnd, nextShoot);
        this.Angle = angle;
        this.RotationSpeed = rotationSpeed;
        this.Speed = speed;
    }

    // Override Colide method
    @Override
    public void Colide() {
        // Override method implementation
        System.out.println("Enemy collided!");
        // Additional implementation if needed
    }

    // Getters and setters for additional attributes
    public double getAngle() {
        return Angle;
    }

    public void setAngle(double angle) {
        Angle = angle;
    }

    public double getRotationSpeed() {
        return RotationSpeed;
    }

    public void setRotationSpeed(double rotationSpeed) {
        RotationSpeed = rotationSpeed;
    }

    public long getSpeed() {
        return Speed;
    }

    public void setSpeed(long speed) {
        Speed = speed;
    }
}
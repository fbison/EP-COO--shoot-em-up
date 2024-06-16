package components;

public class Character extends Component {
    // Additional attributes
    private double radius;
    private double explosionStart;
    private double explosionEnd;
    private long nextShoot;

    // Constructor
    public Character(int state, double coordinateX, double coordinateY, double speedX, double speedY,
                     double radius, double explosionStart, double explosionEnd, long nextShoot) {
        super(state, coordinateX, coordinateY, speedX, speedY);
        this.radius = radius;
        this.explosionStart = explosionStart;
        this.explosionEnd = explosionEnd;
        this.nextShoot = nextShoot;
    }

    // Additional method
    public void collide() {
        // Method implementation
    }

    // Getters and setters for additional attributes
    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public double getExplosionStart() {
        return explosionStart;
    }

    public void setExplosionStart(double explosionStart) {
        this.explosionStart = explosionStart;
    }

    public double getExplosionEnd() {
        return explosionEnd;
    }

    public void setExplosionEnd(double explosionEnd) {
        this.explosionEnd = explosionEnd;
    }

    public long getNextShoot() {
        return nextShoot;
    }

    public void setNextShoot(long nextShoot) {
        this.nextShoot = nextShoot;
    }
}
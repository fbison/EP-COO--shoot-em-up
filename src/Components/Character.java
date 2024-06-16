package Components;

public class Character extends Component {
    // Additional attributes
    private double Radius;
    private double ExplosionStart;
    private double ExplosionEnd;
    private long NextShoot;

    // Constructor
    public Character(int state, double coordinateX, double coordinateY, double speedX, double speedY,
                     double radius, double explosionStart, double explosionEnd, long nextShoot) {
        super(state, coordinateX, coordinateY, speedX, speedY);
        this.Radius = radius;
        this.ExplosionStart = explosionStart;
        this.ExplosionEnd = explosionEnd;
        this.NextShoot = nextShoot;
    }

    // Additional method
    public void Colide() {
        // Method implementation
    }

    // Getters and setters for additional attributes
    public double getRadius() {
        return Radius;
    }

    public void setRadius(double radius) {
        Radius = radius;
    }

    public double getExplosionStart() {
        return ExplosionStart;
    }

    public void setExplosionStart(double explosionStart) {
        ExplosionStart = explosionStart;
    }

    public double getExplosionEnd() {
        return ExplosionEnd;
    }

    public void setExplosionEnd(double explosionEnd) {
        ExplosionEnd = explosionEnd;
    }

    public long getNextShoot() {
        return NextShoot;
    }

    public void setNextShoot(long nextShoot) {
        NextShoot = nextShoot;
    }
}
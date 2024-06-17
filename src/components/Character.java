package components;

import gameLib.Util;

import java.time.Instant;

public class Character extends Component {
    // Additional attributes
    private Instant explosionStart;
    private Instant explosionEnd;
    private long nextShoot;

    // Constructor
    public Character(int state, double coordinateX, double coordinateY, double speedX, double speedY,
                     double radius, Instant explosionStart, Instant explosionEnd, long nextShoot) {
        super(state, coordinateX, coordinateY, speedX, speedY);
        this.explosionStart = explosionStart;
        this.explosionEnd = explosionEnd;
        this.nextShoot = nextShoot;
    }

    // Additional method
    public void collide() {
        // Method implementation
    }

    public Instant getExplosionStart() {
        return explosionStart;
    }

    public void setExplosionStart(Instant explosionStart) {
        this.explosionStart = explosionStart;
    }

    public Instant getExplosionEnd() {
        return explosionEnd;
    }

    public void setExplosionEnd(Instant explosionEnd) {
        this.explosionEnd = explosionEnd;
    }

    public long getNextShoot() {
        return nextShoot;
    }

    public void setNextShoot(long nextShoot) {
        this.nextShoot = nextShoot;
    }

    public void colideCharacters(Component other) {
        double dx = this.getCoordinateX() - other.getCoordinateX();
        double dy = this.getCoordinateY() - other.getCoordinateY();
        double dist = Math.sqrt(dx * dx + dy * dy);

        if (dist < (this.getRadius() - other.getRadius())) {
            this.setState(Util.EXPLODE.getValue());
            this.explosionStart = Instant.now();
            this.explosionEnd = this.explosionStart.plusMillis(2000);
        }
    }

    public void colideProjectile(Projectile projectile) {
        double dx = this.getCoordinateX() - projectile.getCoordinateX();
        double dy = this.getCoordinateY() - projectile.getCoordinateY();
        double dist = Math.sqrt(dx * dx + dy * dy);

        if (dist < (this.getRadius())) {
            this.setState(Util.EXPLODE.getValue());
            this.explosionStart = Instant.now();
            this.explosionEnd = this.explosionStart.plusMillis(2000);
        }
    }
}
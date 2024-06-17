package components;

import gameLib.Util;

import java.time.Instant;

public class Character extends Component {
    // Additional attributes
    private Instant explosionStart;
    private Instant explosionEnd;
    private Instant nextShoot;

    // Constructor
    public Character(int state, double coordinateX, double coordinateY, double speedX, double speedY,
                     double radius, Instant explosionStart, Instant explosionEnd, Instant nextShoot) {
        super(coordinateX, coordinateY, speedX, speedY, radius);
        this.explosionStart = explosionStart;
        this.explosionEnd = explosionEnd;
        this.nextShoot = nextShoot;
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

    public Instant getNextShoot() {
        return nextShoot;
    }

    public void setNextShoot(Instant nextShoot) {
        this.nextShoot = nextShoot;
    }

    public void colideCharacters(Component other) {
        double dx = getCoordinateX() - other.getCoordinateX();
        double dy = getCoordinateY() - other.getCoordinateY();
        double dist = Math.sqrt(dx * dx + dy * dy);

        if (dist < (getRadius() - other.getRadius())) {
            setState(Util.EXPLODE.getValue());
            explosionStart = Instant.now();
            explosionEnd = explosionStart.plusMillis(2000);
        }
    }

    public void colideProjectile(Projectile projectile) {
        double dx = getCoordinateX() - projectile.getCoordinateX();
        double dy = getCoordinateY() - projectile.getCoordinateY();
        double dist = Math.sqrt(dx * dx + dy * dy);

        if (dist < (getRadius())) {
            setState(Util.EXPLODE.getValue());
            explosionStart = Instant.now();
            explosionEnd = explosionStart.plusMillis(2000);
        }
    }


}
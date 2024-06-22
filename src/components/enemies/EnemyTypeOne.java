package components.enemies;

import components.Player;
import components.Projectile;
import graphics.Util;

import java.time.Instant;
import java.util.ArrayList;

public class EnemyTypeOne extends Enemy {
    public EnemyTypeOne(int state, double coordinateX, double coordinateY, double speedX, double speedY, double radius, Instant explosionStart, Instant explosionEnd, Instant nextShoot, double angle, double rotationSpeed, double speed, ArrayList<Projectile> projectiles) {
        super(state, coordinateX, coordinateY, speedX, speedY, radius, explosionStart, explosionEnd, nextShoot, angle, rotationSpeed, speed, projectiles);
    }

    public EnemyTypeOne(int state, double coordinateX, double coordinateY, double speedX, double speedY,
                        double radius, Instant explosionStart, Instant explosionEnd, Instant nextShoot,
                        double angle, double rotationSpeed, double speed, int countProjectiles, int projectileRadius) {
        super(state, coordinateX, coordinateY, speedX, speedY, radius, explosionStart, explosionEnd, nextShoot, angle, rotationSpeed, speed, countProjectiles, projectileRadius);
    }

    @Override
    public void attack(Player player, Instant currentTime, long delta) {
        if (getState() == Util.EXPLODE) {
            if (currentTime.isAfter(getExplosionEnd()))
                setState(Util.INACTIVE);
        } else if (getState() == Util.ACTIVE) {
            if (getCoordinateY() > Util.HEIGHT + 10)
                setState(Util.INACTIVE);
            else {
                setCoordinateX(getSpeedX() * Math.cos(getAngle()) * delta);
                setCoordinateY(getSpeedY() * Math.sin(getAngle()) * delta * -1.0);
                setAngle(getRotationSpeed() * delta);

                if (currentTime.isAfter(getNextShoot()) && getCoordinateY() < player.getCoordinateY()) {
                    int free = findFreeIndex();
                    if (free < getProjectiles().size()) {
                        getProjectiles().get(free).setCoordinateX(getCoordinateX());
                        getProjectiles().get(free).setCoordinateY(getCoordinateY());
                        getProjectiles().get(free).setSpeedX(Math.cos(getAngle()) * 0.45);
                        getProjectiles().get(free).setSpeedY(Math.sin(getAngle()) * 0.45 * (-1));
                        getProjectiles().get(free).setState(Util.ACTIVE);

                        setNextShoot(currentTime.plusMillis((long) (200 + Math.random() * 500)));
                    }
                }
            }
        }
    }

    @Override
    public Instant cast(Instant currentTime) {
        setCoordinateX(Math.random() * (Util.WIDTH - 20.0) + 10.0);
        setCoordinateY(-10.0);
        setSpeed((long) (0.20 + Math.random() * 0.15));
        setAngle(3 * Math.PI / 2);
        setRotationSpeed(0);
        setState(Util.ACTIVE);
        setNextShoot(currentTime.plusMillis(500));
        return currentTime.plusMillis(500);
    }
}
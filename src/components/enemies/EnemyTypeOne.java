package components.enemies;

import components.Component;
import components.Player;
import components.Projectile;
import gameLib.Util;

import java.time.Instant;
import java.util.ArrayList;

public class EnemyTypeOne extends Enemy {
    public EnemyTypeOne(int state, double coordinateX, double coordinateY, double speedX, double speedY, double radius, Instant explosionStart, Instant explosionEnd, Instant nextShoot, double angle, double rotationSpeed, long speed, ArrayList<Component> projectiles) {
        super(state, coordinateX, coordinateY, speedX, speedY, radius, explosionStart, explosionEnd, nextShoot, angle, rotationSpeed, speed, projectiles);
    }

    @Override
    public void attack(Projectile projectile, Player player, Instant currentTime, long delta) {
        if (getState() == Util.EXPLODE.getValue()) {
            if (currentTime.isAfter(getExplosionEnd()))
                setState(Util.INACTIVE.getValue());
        } else if (getState() == Util.ACTIVE.getValue()) {
            if (getCoordinateY() > Util.HEIGHT.getValue() + 10)
                setState(Util.INACTIVE.getValue());
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
                        getProjectiles().get(free).setState(Util.ACTIVE.getValue());

                        setNextShoot(currentTime.plusMillis((long) (200 + Math.random() * 500)));
                    }
                }
            }
        }
    }
}
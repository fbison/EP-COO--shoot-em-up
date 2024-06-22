package components.enemies;

import components.Player;
import components.Projectile;
import graphics.Util;

import java.time.Instant;
import java.util.ArrayList;

public class EnemyTypeOne extends Enemy {
    public EnemyTypeOne() {
        super(Util.INACTIVE, 0, 0, 0, 0, 9.0, null, null, null, 0, 0, 0, 10, 2);
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
                setCoordinateX(getCoordinateX() + getSpeed() * Math.cos(getAngle()) * delta);
                setCoordinateY(getCoordinateY() + getSpeed() * Math.sin(getAngle()) * delta * -1.0);
                setAngle(getAngle() + getRotationSpeed() * delta);

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
        setSpeed(0.20 + Math.random() * 0.15);
        setAngle(3 * Math.PI / 2);
        setRotationSpeed(0);
        setState(Util.ACTIVE);
        setNextShoot(currentTime.plusMillis(500));
        return currentTime.plusMillis(500);
    }
}
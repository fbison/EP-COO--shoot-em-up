package gameComponents.character.enemies;

import gameComponents.character.Player;
import graphics.GameLib;
import graphics.Util;

import java.awt.*;
import java.time.Instant;

public class EnemyTypeOne extends Enemy {
    public EnemyTypeOne(Instant currentTime) {
        super(Util.ACTIVE,
                Math.random() * (Util.WIDTH - 20.0) + 10.0,
                -10.0, 0,
                0,
                9.0,
                null,
                null,
                currentTime.plusMillis(500),
                3 * Math.PI / 2,
                0,
                0.20 + Math.random() * 0.15,
                10,
                2,
                Color.CYAN,
                Color.RED);
    }

    @Override
    public Instant nextCast(Instant currentTime) {
        return currentTime.plusMillis(500);
    }

    @Override
    public void attack(Player player, Instant currentTime, long delta) {
        if (getState() == Util.EXPLODE) {
            if (currentTime.isAfter(getExplosionEnd()))
                setState(Util.INACTIVE);
        } else if (isActive()) {
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
    public void draw(Instant currentTime){
        if (getState() == Util.EXPLODE) {
            drawExplosion(currentTime);
        } else if (isActive()) {
            GameLib.setColor(getColor());
            GameLib.drawCircle(getCoordinateX(), getCoordinateY(), getRadius());
        }
    }
}
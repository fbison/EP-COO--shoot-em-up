package components;

import gameLib.Util;

import java.time.Instant;

public class Player extends Character {
    // Constructor
    public Player(int state, double coordinateX, double coordinateY, double speedX, double speedY,
                  double radius, Instant explosionStart, Instant explosionEnd, Instant nextShoot) {
        super(state, coordinateX, coordinateY, speedX, speedY, radius, explosionStart, explosionEnd, nextShoot);
    }

    public void type1enemy(EnemiesArmy army, Instant currentTime, long delta) {
        var enemies = army.getEnemies();
        for (int i = 0; i < enemies.size(); i++) {
            if (enemies.get(i).getState() == Util.EXPLODE.getValue()) {
                if (currentTime.isAfter(getExplosionEnd()))
                    enemies.get(i).setState(Util.INACTIVE.getValue());
            } else if (enemies.get(i).getState() == Util.ACTIVE.getValue()) {
                if (enemies.get(i).getCoordinateY() > Util.HEIGHT.getValue() + 10)
                    enemies.get(i).setState(Util.INACTIVE.getValue());
            } else {
                enemies.get(i).setCoordinateX(getSpeedX() * Math.cos(enemies.get(i).getAngle()) * delta);
                enemies.get(i).setCoordinateY(getSpeedY() * Math.sin(enemies.get(i).getAngle()) * delta * -1.0);

                if (currentTime.isAfter(enemies.get(i).getNextShoot()) && enemies.get(i).getCoordinateY() < getCoordinateY()) {

                }
            }
        }
    }
}
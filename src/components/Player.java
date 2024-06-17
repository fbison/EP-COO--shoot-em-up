package components;

import gameLib.Util;

import java.time.Instant;

public class Player extends Character {
    // Constructor
    public Player(int state, double coordinateX, double coordinateY, double speedX, double speedY,
                  double radius, Instant explosionStart, Instant explosionEnd, Instant nextShoot) {
        super(state, coordinateX, coordinateY, speedX, speedY, radius, explosionStart, explosionEnd, nextShoot);
    }

    public void attack(EnemiesArmy army, Instant currentTime, long delta) {
        var enemes = army.getEnemies();
        for (int i = 0; i < enemes.size(); i++) {
            if (enemes.get(i).getState() == Util.EXPLODE.getValue()) {
                if (currentTime.isAfter(getExplosionEnd()))
                    enemes.get(i).setState(Util.INACTIVE.getValue());
            } else if (enemes.get(i).getState() == Util.ACTIVE.getValue()) {
                if (enemes.get(i).getCoordinateY() > Util.HEIGHT.getValue() + 10)
                    enemes.get(i).setState(Util.INACTIVE.getValue());
            } else {
                enemes.get(i).setCoordinateX(getSpeedX() * Math.cos(enemes.get(i).getAngle()) * delta);
                enemes.get(i).setCoordinateY(getSpeedY() * Math.sin(enemes.get(i).getAngle()) * delta * -1.0);

                if (currentTime.isAfter(enemes.get(i).getNextShoot()) && enemes.get(i).getCoordinateY() < getCoordinateY()) {
                    //int free = freeIndex(enemes); PAREI AQUI
                }
            }
        }
    }
}
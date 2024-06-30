package gameComponents.character.enemies;

import gameComponents.character.Player;
import graphics.GameLib;
import graphics.Util;

import java.awt.*;
import java.time.Instant;

public class EnemyTypeTwo extends Enemy {
    private double spawnX;
    private static int count = 0;
    private static double nextCoordinateX= (Util.WIDTH * 0.20);

    public EnemyTypeTwo(Instant currentTime) {
        super(Util.ACTIVE, nextCoordinateX,
                -10.0, 0.0, 0.0, 12.0, null, null,
                null, (3 * Math.PI) / 2, 0.0, 0.42, 10, 2, Color.MAGENTA, Color.RED);
        incrementCount();
        if(count == 10){
            restartCount();
            nextCoordinateX = Math.random() > 0.5 ? Util.WIDTH * 0.2 : Util.WIDTH * 0.8;
        }
    }
    private static void incrementCount(){
        count++;
    }
    private static void decrementCount(){
        count--;
    }
    private static void restartCount(){
        count = 0;
    }
   @Override
    public Instant nextCast(Instant currentTime){
       if (count < 10) {
           return currentTime.plusMillis(120);
       } else {
           return currentTime.plusMillis((long) (3000 + Math.random() * 3000));
       }
    }

    public double getSpawnX() {
        return spawnX;
    }

    public void setSpawnX(double spawnX) {
        this.spawnX = spawnX;
    }

    @Override
    public void setState(int state) {
        super.setState(state);
        if (state == Util.INACTIVE) decrementCount();
    }

    @Override
    public void attack(Player player, Instant currentTime, long delta) {
        if (getState() == Util.EXPLODE) {
            if (currentTime.isAfter(getExplosionEnd()))
                setState(Util.INACTIVE);
        } else if (getState() == Util.ACTIVE) {
            if (getCoordinateX() < -10 || getCoordinateY() > Util.HEIGHT + 10)
                setState(Util.INACTIVE);
            else {
                boolean shootNow = false;
                double previousCoordinateY = getCoordinateY();
                double threshold = Util.HEIGHT * 0.30;

                setCoordinateX( getCoordinateX() + (getSpeed() * Math.cos(getAngle()) * delta));
                setCoordinateY( getCoordinateY() + getSpeed() * Math.sin(getAngle()) * delta * -1.0);
                setAngle( getAngle() + getRotationSpeed() * delta);

                if (previousCoordinateY < threshold && getCoordinateY() >= threshold) {
                    if (getCoordinateX() < (double) Util.WIDTH / 2) setRotationSpeed(0.003);
                    else setRotationSpeed(-0.003);
                }

                if (getRotationSpeed() > 0 && Math.abs(getAngle() - 3 * Math.PI) < 0.05) {
                    setRotationSpeed(0.0);
                    setAngle(3 * Math.PI);
                    shootNow = true;
                }

                if (getRotationSpeed() < 0 && Math.abs(getAngle()) < 0.05) {
                    setRotationSpeed(0.0);
                    setAngle(0.0);
                    shootNow = true;
                }

                if (shootNow) {
                    double[] angles = {Math.PI / 2 + Math.PI / 8, Math.PI / 2, Math.PI / 2 - Math.PI / 8};
                    int[] freeArray = findFreeIndex(angles.length);

                    for (int i = 0; i < freeArray.length; i++) {
                        int free = freeArray[i];

                        if (free < getProjectiles().size()) {//não tenho certeza dessa linha
                            double a = angles[i] + Math.random() * Math.PI / 6 - Math.PI / 12;
                            double vX = Math.cos(a);
                            double vY = Math.sin(a);

                            getProjectiles().get(free).setCoordinateX(getCoordinateX());
                            getProjectiles().get(free).setCoordinateY(getCoordinateY());
                            getProjectiles().get(free).setSpeedX(vX * 0.30);
                            getProjectiles().get(free).setSpeedY(vY * 0.30);
                            getProjectiles().get(free).setState(Util.ACTIVE);
                        }
                    }
                }
            }
        }
    }


    @Override
    public void draw(Instant currentTime){
        if (getState() == Util.EXPLODE) {
            drawExplosion(currentTime);
        } else if (getState() == Util.ACTIVE) {
            GameLib.setColor(getColor());
            GameLib.drawDiamond(getCoordinateX(), getCoordinateY(), getRadius());
        }
    }
}
package gameComponents.character.enemies;

import gameComponents.character.Player;
import graphics.GameLib;
import graphics.Util;

import java.awt.*;
import java.time.Instant;

public class EnemyTypeTwo extends Enemy {
    private double spawnX;
    private int count;

    public EnemyTypeTwo() {
        super(Util.INACTIVE, 0.0, 0.0, 0.0, 0.0, 12.0, null, null,
                null, 0.0, 0.0, 0.0, 10, 2, Color.MAGENTA, Color.RED);
        this.spawnX = (Util.WIDTH * 0.20);
        this.count = 0;
    }

    public double getSpawnX() {
        return spawnX;
    }

    public void setSpawnX(double spawnX) {
        this.spawnX = spawnX;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
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
    public Instant cast(Instant currentTime) {
        setCoordinateX(spawnX);
        setCoordinateY(-10.0);
        setSpeed(0.42);
        setAngle((3 * Math.PI) / 2);
        setRotationSpeed(0);
        setState(Util.ACTIVE);
        count ++;

        if (count < 10) {
            return currentTime.plusMillis(120);
        } else {
            count = 0;
            spawnX = Math.random() > 0.5 ? Util.WIDTH * 0.2 : Util.WIDTH * 0.8;
            return currentTime.plusMillis((long) (3000 + Math.random() * 3000));
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
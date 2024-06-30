package gameComponents.character.enemies;

import gameComponents.character.Character;
import gameComponents.essential.Component;
import gameComponents.character.Player;
import gameComponents.character.Projectile;
import graphics.Util;

import java.awt.*;
import java.time.Instant;

public abstract class Enemy extends Character {
    private double angle;
    private double rotationSpeed;
    private double speed;

    public Enemy(int state, double coordinateX, double coordinateY, double speedX, double speedY,
                 double radius, Instant explosionStart, Instant explosionEnd, Instant nextShoot,
                 double angle, double rotationSpeed, double speed, int countProjectiles, int projectileRadius, Color colorEnemy, Color colorProjectile) {
        super(state, coordinateX, coordinateY, speedX, speedY, radius, explosionStart, explosionEnd, nextShoot, countProjectiles, projectileRadius, colorEnemy, colorProjectile);
        this.angle = angle;
        this.rotationSpeed = rotationSpeed;
        this.speed = speed;
    }

    // Getters and setters for additional attributes
    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public double getRotationSpeed() {
        return rotationSpeed;
    }

    public void setRotationSpeed(double rotationSpeed) {
        this.rotationSpeed = rotationSpeed;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public abstract void attack(Player player, Instant currentTime, long delta);

    public abstract Instant nextCast(Instant currentTime);

    public abstract void draw(Instant currentTime);

    @Override
    public void colide(Component opponent, Instant now) {
        if (getState() != Util.ACTIVE)
            return;

        double dx = getCoordinateX() - opponent.getCoordinateX();
        double dy = getCoordinateY() - opponent.getCoordinateY();
        double dist = Math.sqrt(dx * dx + dy * dy);

        if (dist < getRadius()) {
            setState(Util.EXPLODE);
            this.setExplosionStart(now);
            this.setExplosionEnd(now.plusMillis(2000));
        }
    }

    @Override
    public void updateProjectiles(long delta) {
        for (Projectile projectile : this.getProjectiles()) {
            if (projectile.getCoordinateY() > Util.HEIGHT)
                projectile.setState(Util.INACTIVE);
            else {
                projectile.setCoordinateX(projectile.getCoordinateX() + (projectile.getSpeedX() * delta));
                projectile.setCoordinateY(projectile.getCoordinateY() + (projectile.getSpeedY() * delta));
            }
        }
    }


}
package components.enemies;

import components.Character;
import components.Component;
import components.Player;
import components.Projectile;
import graphics.GameLib;
import graphics.Util;

import java.awt.*;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;

public abstract class Enemy extends Character {
    // Additional attributes
    private double angle;
    private double rotationSpeed;
    private double speed;

    // Constructor
    public Enemy(int state, double coordinateX, double coordinateY, double speedX, double speedY,
                 double radius, Instant explosionStart, Instant explosionEnd, Instant nextShoot,
                 double angle, double rotationSpeed, double speed) {
        super(state, coordinateX, coordinateY, speedX, speedY, radius, explosionStart, explosionEnd, nextShoot);
        this.angle = angle;
        this.rotationSpeed = rotationSpeed;
        this.speed = speed;
    }

    public Enemy(int state, double coordinateX, double coordinateY, double speedX, double speedY,
                 double radius, Instant explosionStart, Instant explosionEnd, Instant nextShoot,
                 double angle, double rotationSpeed, double speed, int countProjectiles, int projectileRadius) {
        super(state, coordinateX, coordinateY, speedX, speedY, radius, explosionStart, explosionEnd, nextShoot, countProjectiles, projectileRadius);
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

    public abstract Instant cast(Instant currentTime);

    public abstract void draw(Color color, Instant currentTime);

    @Override
    public void colide(Component opponent) {
        if (getState() != Util.ACTIVE)
            return;

        double dx = getCoordinateX() - opponent.getCoordinateX();
        double dy = getCoordinateY() - opponent.getCoordinateY();
        double dist = Math.sqrt(dx * dx + dy * dy);

        if (dist < getRadius()) {
            setState(Util.EXPLODE);
            var now = Instant.now();
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
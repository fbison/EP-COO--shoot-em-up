package components;

import gameLib.Util;

import java.time.Instant;

public class Enemy extends Character {
    // Additional attributes
    private double angle;
    private double rotationSpeed;
    private long speed;

    // Constructor
    public Enemy(int state, double coordinateX, double coordinateY, double speedX, double speedY,
                 double radius, Instant explosionStart, Instant explosionEnd, Instant nextShoot,
                 double angle, double rotationSpeed, long speed) {
        super(state, coordinateX, coordinateY, speedX, speedY, radius, explosionStart, explosionEnd, nextShoot);
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

    public long getSpeed() {
        return speed;
    }

    public void setSpeed(long speed) {
        this.speed = speed;
    }


    public void colideProjectile(Projectile projectile, Instant currentTime, long delta, Player player) {

    }
}
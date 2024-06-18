package components.enemies;

import components.Character;
import components.Component;
import components.Player;
import components.Projectile;

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
                 double angle, double rotationSpeed, double speed, ArrayList<Projectile> projectiles) {
        super(state, coordinateX, coordinateY, speedX, speedY, radius, explosionStart, explosionEnd, nextShoot, projectiles);
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
}
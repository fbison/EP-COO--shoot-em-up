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

    // Override collide method
    @Override
    public void collide() {
        // Override method implementation
        System.out.println("Enemy collided!");
        // Additional implementation if needed
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

//    public void update(long delta, Instant currentTime) {
//        if (getState() == Util.EXPLODE.getValue()) {
//            setState(Util.INACTIVE.getValue());
//        } else if (getState() == Util.ACTIVE.getValue()) {
//            if (this.getCoordinateY() > Util.HEIGHT.getValue() + 10) {
//                setState(Util.INACTIVE.getValue());
//            } else {
//                this.setCoordinateX(getSpeedX() * Math.cos(getAngle() * delta));
//                this.setCoordinateY(getSpeedY() * Math.sin(getAngle() * delta * -1.0));
//                angle += rotationSpeed * delta;
//
//                if(currentTime.isAfter(getNextShoot()) && getCoordinateY())
//            }
//        }
//    }
}
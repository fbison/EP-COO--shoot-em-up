package components;

import java.time.Instant;

public class Player extends Character {
    // Constructor
    public Player(int state, double coordinateX, double coordinateY, double speedX, double speedY,
                  double radius, Instant explosionStart, Instant explosionEnd, Instant nextShoot) {
        super(state, coordinateX, coordinateY, speedX, speedY, radius, explosionStart, explosionEnd, nextShoot);
    }

    @Override
    public void collide() {
        // Override method implementation
        System.out.println("Player collided!");
        // Additional implementation if needed
    }
}
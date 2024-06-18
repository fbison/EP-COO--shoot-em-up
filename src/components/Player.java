package components;

import java.time.Instant;
import java.util.ArrayList;

public class Player extends Character {
    // Constructor
    public Player(int state, double coordinateX, double coordinateY, double speedX, double speedY,
                  double radius, Instant explosionStart, Instant explosionEnd, Instant nextShoot, ArrayList<Component> projectiles) {
        super(state, coordinateX, coordinateY, speedX, speedY, radius, explosionStart, explosionEnd, nextShoot, projectiles);
    }
}
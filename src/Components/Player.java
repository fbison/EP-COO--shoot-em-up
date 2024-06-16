package Components;

public class Player extends Character {
    // Constructor
    public Player(int state, double coordinateX, double coordinateY, double speedX, double speedY,
                  double radius, double explosionStart, double explosionEnd, long nextShoot) {
        super(state, coordinateX, coordinateY, speedX, speedY, radius, explosionStart, explosionEnd, nextShoot);
    }

    @Override
    public void Colide() {
        // Override method implementation
        System.out.println("Player collided!");
        // Additional implementation if needed
    }
}
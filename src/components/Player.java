package components;

import graphics.Util;

import java.time.Instant;
import java.util.ArrayList;

public class Player extends Character {
    // Constructor
    public Player(int state, double coordinateX, double coordinateY, double speedX, double speedY,
                  double radius, Instant explosionStart, Instant explosionEnd, Instant nextShoot, ArrayList<Projectile> projectiles) {
        super(state, coordinateX, coordinateY, speedX, speedY, radius, explosionStart, explosionEnd, nextShoot, projectiles);
    }

    public Player(int state, double coordinateX, double coordinateY, double speedX, double speedY,
                  double radius, Instant explosionStart, Instant explosionEnd, Instant nextShoot, int countProjectiles, int projectileRadius) {
        super(state, coordinateX, coordinateY, speedX, speedY, radius, explosionStart, explosionEnd, nextShoot, countProjectiles, projectileRadius);
    }

    //Verificação se as coordenadas do player estão dentro da tela
    public void keepInTheScren(){
        if (this.getCoordinateX() < 0) this.setCoordinateX(0);
        if (this.getCoordinateX() >= Util.WIDTH) this.setCoordinateX(Util.WIDTH - 1);
        if (this.getCoordinateY() < 25) this.setCoordinateY(25);
        if (this.getCoordinateY() >= Util.HEIGHT) this.setCoordinateY(Util.HEIGHT - 1);
    }
    public void updateProjectiles(long delta) {
        for (Projectile projectile : this.getProjectiles()) {
            if(projectile.getState() == Util.ACTIVE) {
                if (projectile.getCoordinateY() < 0)
                    projectile.setState(Util.INACTIVE);
                else {
                    projectile.setCoordinateX(projectile.getCoordinateX() + (projectile.getSpeedX() * delta));
                    projectile.setCoordinateY(projectile.getCoordinateY() + (projectile.getSpeedY() * delta));
                }
            }
        }
    }
}
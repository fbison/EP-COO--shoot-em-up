package gameComponents.character;

import gameComponents.essential.Component;
import graphics.Util;

import java.awt.Color;
import java.time.Instant;
import java.util.ArrayList;

public class Character extends Component {
    // Additional attributes
    private Instant explosionStart;
    private Instant explosionEnd;
    private Instant nextShoot;
    private ArrayList<Projectile> projectiles;

    // Constructor
    public Character(int state, double coordinateX, double coordinateY, double speedX, double speedY,
                     double radius, Instant explosionStart, Instant explosionEnd, Instant nextShoot, Color color) {
        super(state, coordinateX, coordinateY, speedX, speedY, radius, color);
        this.explosionStart = explosionStart;
        this.explosionEnd = explosionEnd;
        this.nextShoot = nextShoot;
        this.projectiles = new ArrayList<>();
    }

    public Character(int state, double coordinateX, double coordinateY, double speedX, double speedY,
                     double radius, Instant explosionStart, Instant explosionEnd, Instant nextShoot,
                     int countProjectiles, int projectileRadius, Color colorCharacter, Color colorProjectile) {
        super(state, coordinateX, coordinateY, speedX, speedY, radius, colorCharacter);
        this.explosionStart = explosionStart;
        this.explosionEnd = explosionEnd;
        this.nextShoot = nextShoot;
        this.projectiles = new ArrayList<>(countProjectiles);
        for (int i = 0; i < countProjectiles; i++) {
            this.projectiles.add(new Projectile(projectileRadius, colorProjectile));
        }
    }

    public Instant getExplosionStart() {
        return explosionStart;
    }

    public void setExplosionStart(Instant explosionStart) {
        this.explosionStart = explosionStart;
    }

    public Instant getExplosionEnd() {
        return explosionEnd;
    }

    public void setExplosionEnd(Instant explosionEnd) {
        this.explosionEnd = explosionEnd;
    }

    public Instant getNextShoot() {
        return nextShoot;
    }

    public void setNextShoot(Instant nextShoot) {
        this.nextShoot = nextShoot;
    }

    public ArrayList<Projectile> getProjectiles() {
        return projectiles;
    }

    public void setProjectiles(ArrayList<Projectile> projectiles) {
        this.projectiles = projectiles;
    }

    public void prepareExplosion(){
        setState(Util.EXPLODE);
        explosionStart = Instant.now();
        explosionEnd = explosionStart.plusMillis(2000);
    }

    public void colide(Component opponent) {
        if (getState() != Util.ACTIVE)
            return;

        double dx = getCoordinateX() - opponent.getCoordinateX();
        double dy = getCoordinateY() - opponent.getCoordinateY();
        double dist = Math.sqrt(dx * dx + dy * dy);

        if (dist < (getRadius() + opponent.getRadius()) * 0.8) {
            prepareExplosion();
        }
    }

    public void updateProjectiles(long delta) {
        for (Projectile projectile : projectiles) {
            if (projectile.getCoordinateY() < 0)
                projectile.setState(Util.INACTIVE);
            else {
                projectile.setCoordinateX(projectile.getCoordinateX() + (projectile.getSpeedX() * delta));
                projectile.setCoordinateY(projectile.getCoordinateY() + (projectile.getSpeedY() * delta));
            }
        }
    }

    public int findFreeIndex() {
        int i;
        for (i = 0; i < projectiles.size(); i++) {
            if (projectiles.get(i).getState() == Util.INACTIVE)
                return i;
        }
        return i;
    }

    public int[] findFreeIndex(int amount) {
        int i, k;
        int[] freeArray = {projectiles.size(), projectiles.size(), projectiles.size()};

        for (i = 0, k = 0; i < projectiles.size() && k < amount; i++) {
            if (projectiles.get(i).getState() == Util.INACTIVE) {
                freeArray[k] = i;
                k++;
            }
        }

        return freeArray;
    }
}
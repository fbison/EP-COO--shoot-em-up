package components;

import gameLib.Util;

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
                     double radius, Instant explosionStart, Instant explosionEnd, Instant nextShoot, ArrayList<Projectile> projectiles) {
        super(coordinateX, coordinateY, speedX, speedY, radius);
        this.explosionStart = explosionStart;
        this.explosionEnd = explosionEnd;
        this.nextShoot = nextShoot;
        this.projectiles = projectiles;
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

    public void colideCharacters(Component other) {
        double dx = getCoordinateX() - other.getCoordinateX();
        double dy = getCoordinateY() - other.getCoordinateY();
        double dist = Math.sqrt(dx * dx + dy * dy);

        if (dist < (getRadius() - other.getRadius())) {
            setState(Util.EXPLODE);
            explosionStart = Instant.now();
            explosionEnd = explosionStart.plusMillis(2000);
        }
    }

    public void colideProjectile(Projectile projectile) {
        double dx = getCoordinateX() - projectile.getCoordinateX();
        double dy = getCoordinateY() - projectile.getCoordinateY();
        double dist = Math.sqrt(dx * dx + dy * dy);

        if (dist < (getRadius())) {
            setState(Util.EXPLODE);
            explosionStart = Instant.now();
            explosionEnd = explosionStart.plusMillis(2000);
        }
    }

    public void updateProjectiles(long delta) {
        for (int i = 0; i < projectiles.size(); i++) {
            var currentProjectile = projectiles.get(i);
            if (currentProjectile.getState() == Util.ACTIVE)
                currentProjectile.setState(Util.INACTIVE);
            else {
                currentProjectile.setCoordinateX(currentProjectile.getCoordinateX() * delta);
                currentProjectile.setCoordinateY(currentProjectile.getCoordinateY() * delta);
            }

        }
    }

    public int findFreeIndex(){
        int i;
        for (i = 0; i < projectiles.size(); i++) {
            if(projectiles.get(i).getState() == Util.INACTIVE)
                return i;
        }
        return i;
    }

    public int [] findFreeIndex(int amount){
        int i, k;
        int [] freeArray = { projectiles.size(), projectiles.size(), projectiles.size() };

        for(i = 0, k = 0; i < projectiles.size() && k < amount; i++){
            if(projectiles.get(i).getState() == Util.INACTIVE) {
                freeArray[k] = i;
                k++;
            }
        }

        return freeArray;
    }
}
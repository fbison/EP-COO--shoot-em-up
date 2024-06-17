package components;

import gameLib.Util;

import java.util.ArrayList;

public class ProjectilesShoot {
    // ArrayList to hold projectiles
    private ArrayList<Component> projectiles;

    // Constructor
    public ProjectilesShoot() {
        projectiles = new ArrayList<>();
    }

    // Getter for projectiles
    public ArrayList<Component> getProjectiles() {
        return projectiles;
    }

    // Setter for projectiles
    public void setProjectiles(ArrayList<Component> projectiles) {
        projectiles = projectiles;
    }

    public void checkProjectiles(long delta) {
        for (int i = 0; i < projectiles.size(); i++) {
            var currentProjectile = projectiles.get(i);
            if (currentProjectile.getState() == Util.ACTIVE.getValue())
                currentProjectile.setState(Util.INACTIVE.getValue());
            else {
                currentProjectile.setCoordinateX(currentProjectile.getCoordinateX() * delta);
                currentProjectile.setCoordinateY(currentProjectile.getCoordinateY() * delta);
            }

        }
    }
}
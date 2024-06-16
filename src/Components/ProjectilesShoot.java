package Components;

import java.util.ArrayList;

public class ProjectilesShoot {
    // ArrayList to hold projectiles
    private ArrayList<Component> Projectiles;

    // Constructor
    public ProjectilesShoot() {
        this.Projectiles = new ArrayList<>();
    }

    // Getter for Projectiles
    public ArrayList<Component> getProjectiles() {
        return Projectiles;
    }

    // Setter for Projectiles
    public void setProjectiles(ArrayList<Component> projectiles) {
        Projectiles = projectiles;
    }
}
package components;

import java.util.ArrayList;

public class ProjectilesShoot {
    // ArrayList to hold projectiles
    private ArrayList<Component> projectiles;

    // Constructor
    public ProjectilesShoot() {
        this.projectiles = new ArrayList<>();
    }

    // Getter for projectiles
    public ArrayList<Component> getProjectiles() {
        return projectiles;
    }

    // Setter for projectiles
    public void setProjectiles(ArrayList<Component> projectiles) {
        this.projectiles = projectiles;
    }
}
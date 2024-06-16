package Components;

import java.util.ArrayList;

public class EnemiesArmy {
    // ArrayList to hold enemies
    private ArrayList<Enemy> Enemies;
    // Next enemy time
    private long NextEnemy;

    // Constructor
    public EnemiesArmy() {
        this.Enemies = new ArrayList<>();
        this.NextEnemy = 0; // Initialize to default value
    }

    // Getter for Enemies
    public ArrayList<Enemy> getEnemies() {
        return Enemies;
    }

    // Setter for Enemies
    public void setEnemies(ArrayList<Enemy> enemies) {
        Enemies = enemies;
    }

    // Getter for NextEnemy
    public long getNextEnemy() {
        return NextEnemy;
    }

    // Setter for NextEnemy
    public void setNextEnemy(long nextEnemy) {
        NextEnemy = nextEnemy;
    }
}
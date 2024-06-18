package components;

import components.enemies.Enemy;
import gameLib.Util;

import java.util.ArrayList;

public class EnemiesArmy {
    // ArrayList to hold enemies
    private ArrayList<Enemy> enemies;
    // Next enemy time
    private long nextEnemy;

    // Constructor
    public EnemiesArmy() {
        this.enemies = new ArrayList<>();
        this.nextEnemy = 0; // Initialize to default value
    }

    // Getter for Enemies
    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    // Setter for Enemies
    public void setEnemies(ArrayList<Enemy> enemies) {
        this.enemies = enemies;
    }

    // Getter for NextEnemy
    public long getNextEnemy() {
        return nextEnemy;
    }

    // Setter for NextEnemy
    public void setNextEnemy(long nextEnemy) {
        this.nextEnemy = nextEnemy;
    }

    public int freeIndex() {
        int i;
        for (i = 0; i < enemies.size(); i++) {
            if (enemies.get(i).getState() == Util.INACTIVE.getValue()) break;
        }
        return i;
    }
}
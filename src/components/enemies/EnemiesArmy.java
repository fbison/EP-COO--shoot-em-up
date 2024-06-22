package components.enemies;

import components.Player;
import graphics.Util;

import java.time.Instant;
import java.util.ArrayList;

public class EnemiesArmy {
    // ArrayList to hold enemies
    private ArrayList<Enemy> enemies;
    // Next enemy time
    private Instant nextEnemy;

    // Constructor
    public EnemiesArmy(ArrayList<Enemy> enemies, Instant nextEnemy) {
        this.enemies = enemies;
        this.nextEnemy = nextEnemy;
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
    public Instant getNextEnemy() {
        return nextEnemy;
    }

    // Setter for NextEnemy
    public void setNextEnemy(Instant nextEnemy) {
        this.nextEnemy = nextEnemy;
    }

    public int freeIndex() {
        int i;
        for (i = 0; i < enemies.size(); i++) {
            if (enemies.get(i).getState() == Util.INACTIVE) break;
        }
        return i;
    }

    public void castEnemies(Instant currentTime) {
        if (currentTime.isAfter(nextEnemy)) {
            int free = freeIndex();
            if (free < enemies.size()) {
                nextEnemy = enemies.get(free).cast(currentTime);
            }
        }
    }

    public void atack(Player player, Instant currentTime, long delta){
        for(Enemy enemy: getEnemies()) {
            enemy.attack(player, currentTime, delta);
        }
    }
    public void updateProjectiles(long delta){
        for(Enemy enemy: getEnemies()) {
            enemy.updateProjectiles(delta);
        }
    }
}
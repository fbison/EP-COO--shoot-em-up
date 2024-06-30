package gameComponents.character.enemies;

import gameComponents.character.Player;
import gameComponents.character.Projectile;
import graphics.GameLib;
import graphics.Util;

import java.time.Instant;
import java.util.ArrayList;

public class EnemiesArmy {
    private ArrayList<Enemy> enemies;
    private Instant nextEnemy;
    private int maxActive;

    // Constructor
    public EnemiesArmy(int quantity, Class<? extends Enemy> enemyClass, Instant nextEnemy) {
        this.enemies = createEnemies(quantity, enemyClass);
        this.maxActive = quantity;
        this.nextEnemy = nextEnemy;
    }

    private static Enemy createEnemyFromInstance(Class<? extends Enemy> enemyClass) {
        try {
            return (enemyClass.getDeclaredConstructor().newInstance());
        } catch (Exception e) {
            System.out.println("Inimigo construido sem construtor");
            return null;
        }
    }

    public static ArrayList<Enemy> createEnemies(int quantity, Class<? extends Enemy> enemyClass) {
        ArrayList<Enemy> enemies = new ArrayList<>();
        for (int i = 0; i < quantity; i++) {
            enemies.add(createEnemyFromInstance(enemyClass));
        }

        return enemies;
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
        if (countActiveEnemies() < maxActive  && currentTime.isAfter(nextEnemy)) {
            int free = freeIndex();
            if (free < enemies.size()) {
                nextEnemy = enemies.get(free).cast(currentTime);
            }
        }
    }

    public void atack(Player player, Instant currentTime, long delta) {
        for (Enemy enemy : getEnemies()) {
            enemy.attack(player, currentTime, delta);
        }
    }

    public void updateProjectiles(long delta) {
        for (Enemy enemy : getEnemies()) {
            enemy.updateProjectiles(delta);
        }
    }

    public void drawProjetiles() {
        for (Enemy enemy : getEnemies()) {
            if (enemy.getState() == Util.ACTIVE) {
                for (Projectile projectile : enemy.getProjectiles()) {
                    GameLib.setColor(projectile.getColor());
                    GameLib.drawCircle(projectile.getCoordinateX(), projectile.getCoordinateY(), projectile.getRadius());
                }
            }
        }
    }

    public void drawEnemys(Instant currentTime) {
        for (Enemy enemy : getEnemies()) {
            enemy.draw(currentTime);
        }
    }

    public void checkCollisions(Player player, Instant currentTime) {
        for (Projectile projectile : player.getProjectiles()) {
            for (Enemy enemy : getEnemies()) {
                enemy.colide(projectile, currentTime);
            }
        }
    }

    public int countActiveEnemies() {
        int activeCount = 0;
        for (Enemy enemy : enemies) {
            if (enemy.getState() == Util.ACTIVE) {
                activeCount++;
            }
        }
        return activeCount;
    }

}
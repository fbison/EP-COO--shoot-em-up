package gameComponents.character.enemies;

import gameComponents.character.Player;
import gameComponents.character.Projectile;
import graphics.GameLib;
import graphics.Util;

import java.time.Instant;
import java.util.ArrayList;

public class EnemiesArmy {
    // ArrayList to hold enemies
    private ArrayList<Enemy> enemies;
    // Next enemy time
    private Instant nextEnemy;


    // Constructor
    public EnemiesArmy(int quantity, Class<? extends Enemy> enemyClass, Instant nextEnemy) {
        this.enemies = createEnemies(quantity, enemyClass);
        this.nextEnemy = nextEnemy;
    }
    public static ArrayList<Enemy> createEnemies(int quantity, Class<? extends Enemy> enemyClass) {
        ArrayList<Enemy> enemies = new ArrayList<>();
        try {
            for (int i = 0; i < quantity; i++) {
                enemies.add(enemyClass.getDeclaredConstructor().newInstance());
            }
        } catch (Exception e) {
            e.printStackTrace();
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
    public void drawProjetiles(){
        for(Enemy enemy : getEnemies()){
            if (enemy.getState() == Util.ACTIVE) {
                for (Projectile projectile : enemy.getProjectiles()){
                    GameLib.setColor(projectile.getColor());
                    GameLib.drawCircle(projectile.getCoordinateX(), projectile.getCoordinateY(), projectile.getRadius());
                }
            }
        }
    }
    public void drawEnemys(Instant currentTime){
        for(Enemy enemy : getEnemies()){
            enemy.draw(currentTime);
        }
    }
    public void checkCollisions(Player player){
        for (Projectile projectile : player.getProjectiles()){
            for(Enemy enemy : getEnemies()){
                enemy.colide(projectile);
            }
        }
    }
}
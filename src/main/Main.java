package main;

import components.BackgroundStars;

import java.awt.Color;

import components.enemies.Enemy;
import components.enemies.EnemiesArmy;
import components.enemies.EnemyTypeOne;
import components.enemies.EnemyTypeTwo;
import components.Player;
import graphics.Util;
import graphics.GameLib;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;

public class Main {
    public static void busyWait(Instant endTime) {
        while (Instant.now().isBefore(endTime)) {
            Thread.yield();
        }
    }

    public static void main(String[] args) {

        final int enemyQuantity = 10;
        final int projectileQuantity = 10;
        var currentTime = Instant.now();
        boolean running = true;
        long delta;

        Player player = new Player((double) Util.WIDTH / 2,
                Util.HEIGHT * 0.90, 0.25, 0.25, 12.0,currentTime, projectileQuantity);
        EnemiesArmy armyEnemyOne = new EnemiesArmy(enemyQuantity, EnemyTypeOne.class, currentTime.plusMillis(2000), Color.CYAN);
        EnemiesArmy armyEnemyTwo = new EnemiesArmy(enemyQuantity, EnemyTypeTwo.class, currentTime.plusMillis(2000), Color.MAGENTA);

        BackgroundStars starsFirst = new BackgroundStars(0.07, 0.0, 20, Color.GRAY);
        BackgroundStars starsSecond = new BackgroundStars(0.045, 0.0, 50, Color.DARK_GRAY);

        GameLib.initGraphics();

        while (running) {
            delta = Duration.between(currentTime, Instant.now()).toMillis();
            currentTime = Instant.now();

            //verificação de colisões

            player.checkCollisions(armyEnemyOne);
            player.checkCollisions(armyEnemyTwo);

            armyEnemyOne.checkCollisions(player);
            armyEnemyTwo.checkCollisions(player);

            //atualização de projéteis
            player.updateProjectiles(delta);

            armyEnemyOne.updateProjectiles(delta);
            armyEnemyTwo.updateProjectiles(delta);

            armyEnemyOne.atack(player, currentTime, delta);
            armyEnemyTwo.atack(player, currentTime, delta);

            armyEnemyOne.castEnemies(currentTime);
            armyEnemyTwo.castEnemies(currentTime);

            player.backToLife(currentTime);

            if (GameLib.isKeyPressed(Util.KEY_ESCAPE)) running = false;
            player.verifyActions(currentTime, delta);

            player.keepInTheScren();
            starsSecond.update(delta);
            starsFirst.update(delta);
            player.draw(currentTime);

            armyEnemyOne.drawProjetiles();
            armyEnemyTwo.drawProjetiles();
            armyEnemyOne.drawEnemys(currentTime);
            armyEnemyTwo.drawEnemys(currentTime);

            GameLib.display();
            busyWait(currentTime.plusMillis(5));
        }
    }
}
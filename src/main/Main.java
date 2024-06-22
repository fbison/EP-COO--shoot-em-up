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

        final int enemyProjectiles = 10;
        var currentTime = Instant.now();
        boolean running = true;
        long delta;

        //inicialização do player
        Player player = new Player(Util.ACTIVE, (double) Util.WIDTH / 2, Util.HEIGHT * 0.90, 0.25, 0.25, 12.0, null, null, currentTime, enemyProjectiles, 0);
        //inicialização de Inimigo Tipo 1
        var enemiesOne = new ArrayList<Enemy>();
        //passar pro EnemiesArmy
        for (int i = 0; i < enemyProjectiles; i++) {
            var enemyOne = new EnemyTypeOne(Util.INACTIVE, 0, 0, 0, 0, 9.0, null, null, null, 0, 0, 0, 10, 2);
            enemiesOne.add(enemyOne);
        }
        EnemiesArmy armyEnemyOne = new EnemiesArmy(enemiesOne, currentTime.plusMillis(2000), Color.CYAN);

        //inicialização de Inimigo Tipo 2
        var enemiesTwo = new ArrayList<Enemy>();
        for (int i = 0; i < enemyProjectiles; i++) {
            var enemyTwo = new EnemyTypeTwo(Util.INACTIVE, 0.0, 0.0, 0.0, 0.0, 9.0, null, null,
                    null, 0.0, 0.0, 0.0, (int) (Util.WIDTH * 0.20), 10, 2);
            enemiesTwo.add(enemyTwo);
        }
        var armyEnemyTwo = new EnemiesArmy(enemiesTwo, currentTime.plusMillis(2000), Color.MAGENTA);

        // estrelas que formam o fundo de primeiro plano
        var starsFirst = new BackgroundStars(0.07, 0.0, 20, Color.GRAY);
        var starsSecond = new BackgroundStars(0.045, 0.0, 50, Color.DARK_GRAY);

        //Inicialização da interface
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
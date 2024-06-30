package main;

import gameComponents.character.PowerUp;
import gameComponents.character.enemies.EnemyTypeThree;
import gameComponents.scenario.BackgroundStars;

import java.awt.Color;

import gameComponents.character.enemies.EnemiesArmy;
import gameComponents.character.enemies.EnemyTypeOne;
import gameComponents.character.enemies.EnemyTypeTwo;
import gameComponents.character.Player;
import gameComponents.scenario.LifeBar;
import gameComponents.scenario.Message;
import graphics.Util;
import graphics.GameLib;

import java.time.Duration;
import java.time.Instant;

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
                Util.HEIGHT * 0.90, 0.25, 0.25, 12.0,currentTime, projectileQuantity, Color.BLUE, Color.GREEN);
        EnemiesArmy armyEnemyOne = new EnemiesArmy(enemyQuantity, EnemyTypeOne.class, currentTime.plusMillis(2000));
        EnemiesArmy armyEnemyTwo = new EnemiesArmy(enemyQuantity, EnemyTypeTwo.class, currentTime.plusMillis(2000));
        EnemiesArmy armyEnemyThree = new EnemiesArmy(enemyQuantity, EnemyTypeThree.class, currentTime.plusMillis(2000));

        BackgroundStars starsFirst = new BackgroundStars(0.07, 0.0, 20, Color.GRAY);
        BackgroundStars starsSecond = new BackgroundStars(0.045, 0.0, 50, Color.DARK_GRAY);

        LifeBar lifeBar = new LifeBar(player.getLife());
        Message GameOver = new Message("Game Over");
        PowerUp powerUp = new PowerUp();

        GameLib.initGraphics();

        while (running) {
            delta = Duration.between(currentTime, Instant.now()).toMillis();
            currentTime = Instant.now();

            //verificação de colisões

            player.checkCollisions(armyEnemyOne);
            player.checkCollisions(armyEnemyTwo);
            player.checkCollisions(armyEnemyThree);

            armyEnemyOne.checkCollisions(player);
            armyEnemyTwo.checkCollisions(player);
            armyEnemyThree.checkCollisions(player);

            //atualização de projéteis
            player.updateProjectiles(delta);

            armyEnemyOne.updateProjectiles(delta);
            armyEnemyTwo.updateProjectiles(delta);
            armyEnemyThree.updateProjectiles(delta);

            armyEnemyOne.atack(player, currentTime, delta);
            armyEnemyTwo.atack(player, currentTime, delta);
            armyEnemyThree.atack(player, currentTime, delta);

            armyEnemyThree.castSpecificEnemy(currentTime, 3);

            armyEnemyOne.castEnemies(currentTime);
            armyEnemyTwo.castEnemies(currentTime);

            player.setInactive(currentTime);

            if (GameLib.isKeyPressed(Util.KEY_ESCAPE)) running = false;
            if (player.getLife() == 0) {
                GameOver.update();
            }
            player.verifyActions(currentTime, delta);

            player.keepInScren();
            starsSecond.update(delta);
            starsFirst.update(delta);
            player.draw(currentTime);

            armyEnemyOne.drawProjetiles();
            armyEnemyTwo.drawProjetiles();
            armyEnemyThree.drawProjetiles();

            armyEnemyOne.drawEnemys(currentTime);
            armyEnemyTwo.drawEnemys(currentTime);
            armyEnemyThree.drawEnemys(currentTime);

            lifeBar.update(player.getLife());

            powerUp.draw();
            powerUp.checkCollision(player);

            if (currentTime.toEpochMilli() % 15000 < delta) {
                powerUp.activate();
            }
            player.updateImmunity(currentTime);

            GameLib.display();
            busyWait(currentTime.plusMillis(5));
        }

    }
}
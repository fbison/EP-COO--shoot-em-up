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

        var currentTime = Instant.now();
        boolean running = true;
        long delta;

        Player player = new Player(currentTime);

        EnemiesArmy armyEnemyOne = new EnemiesArmy(Util.ENEMY_QUANTITY, EnemyTypeOne.class, currentTime, 2000);
        EnemiesArmy armyEnemyTwo = new EnemiesArmy(Util.ENEMY_QUANTITY, EnemyTypeTwo.class, currentTime, 7000);
        EnemiesArmy armyEnemyThree = new EnemiesArmy(Util.ENEMY3_MAX_ACTIVE, EnemyTypeThree.class, currentTime, 2000);

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

            player.checkCollisions(armyEnemyOne, currentTime);
            player.checkCollisions(armyEnemyTwo, currentTime);
            player.checkCollisions(armyEnemyThree, currentTime);

            armyEnemyOne.checkCollisions(player, currentTime);
            armyEnemyTwo.checkCollisions(player, currentTime);
            armyEnemyThree.checkCollisions(player, currentTime);

            armyEnemyOne.cleanInactive();
            armyEnemyTwo.cleanInactive();
            armyEnemyThree.cleanInactive();

            //atualização de projéteis
            player.updateProjectiles(delta);

            armyEnemyOne.updateProjectiles(delta);
            armyEnemyTwo.updateProjectiles(delta);
            armyEnemyThree.updateProjectiles(delta);

            armyEnemyOne.atack(player, currentTime, delta);
            armyEnemyTwo.atack(player, currentTime, delta);
            armyEnemyThree.atack(player, currentTime, delta);

            armyEnemyOne.castEnemies(currentTime);
            armyEnemyTwo.castEnemies(currentTime);
            armyEnemyThree.castEnemies(currentTime);

            player.setInactive(currentTime);

            if (GameLib.isKeyPressed(Util.KEY_ESCAPE)) running = false;

            if (player.getLife() == 0) {
                GameOver.update();
            }

            player.verifyActions(currentTime, delta);
            player.keepInScren();
            player.draw(currentTime);

            starsSecond.update(delta);
            starsFirst.update(delta);

            armyEnemyOne.drawEnemies(currentTime);
            armyEnemyTwo.drawEnemies(currentTime);
            armyEnemyThree.drawEnemies(currentTime);

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
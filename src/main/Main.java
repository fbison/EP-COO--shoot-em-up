package main;

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

        //em todo lugar que ta escrito o numero doze, trocar por variavel nova que eu vou ler

        final int enemyQuantity = 10;
        final int projectileQuantity = 10;
        var currentTime = Instant.now();
        boolean running = true;
        long delta;

        Player player = new Player((double) Util.WIDTH / 2,
                Util.HEIGHT * 0.90, 0.25, 0.25, 12.0,currentTime, projectileQuantity, Color.BLUE, Color.GREEN, 12);
        EnemiesArmy armyEnemyOne = new EnemiesArmy(enemyQuantity, EnemyTypeOne.class, currentTime.plusMillis(2000));
        EnemiesArmy armyEnemyTwo = new EnemiesArmy(enemyQuantity, EnemyTypeTwo.class, currentTime.plusMillis(2000));

        BackgroundStars starsFirst = new BackgroundStars(0.07, 0.0, 20, Color.GRAY);
        BackgroundStars starsSecond = new BackgroundStars(0.045, 0.0, 50, Color.DARK_GRAY);

        LifeBar vida = new LifeBar(12);
        Message GameOver = new Message("Game Over");

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
            if (player.getLife() == 0) {
                player.setState(Util.INACTIVE);
                GameOver.update();
            }
            player.verifyActions(currentTime, delta);

            player.keepInScren();
            starsSecond.update(delta);
            starsFirst.update(delta);
            player.draw(currentTime);

            armyEnemyOne.drawProjetiles();
            armyEnemyTwo.drawProjetiles();
            armyEnemyOne.drawEnemys(currentTime);
            armyEnemyTwo.drawEnemys(currentTime);
            vida.update(player.getLife());

            GameLib.display();
            busyWait(currentTime.plusMillis(5));
        }

    }
}
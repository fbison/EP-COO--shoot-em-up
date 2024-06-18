package main;

import components.Projectile;
import components.enemies.Enemy;
import gameLib.MyFrame;
import graphics.Background;
import graphics.Draw;
import graphics.GameFrame;
import components.enemies.EnemiesArmy;
import components.enemies.EnemyTypeOne;
import components.enemies.EnemyTypeTwo;
import components.Player;
import gameLib.Util;
import gameLib.Game;

import javax.swing.*;
import java.awt.*;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;

public class Main {
    public static void busyWait(long time) {
        while (System.currentTimeMillis() < time) Thread.yield();
    }

    public static void main(String[] args) {
        final int enemyProjectiles = 10;
        MyFrame frame = new MyFrame("Shoot-em-up");
        Graphics graphics = null;
        var currentTime = Instant.now();
        boolean running = true;
        long delta = 0;

        var projectilesTemp = new ArrayList<Projectile>(); //lista temporária de projéteis para projéteis
        for (int i = 0; i < enemyProjectiles; i++) {
            projectilesTemp.add(new Projectile(0));
        }

        //inicialização do player
        Player player = new Player(Util.ACTIVE, Util.WIDTH / 2, Util.HEIGHT * 0.90, 0.25, 0.25, 12.0, Instant.EPOCH, Instant.EPOCH, currentTime, projectilesTemp);

        //Projéteis dos inimigos
        var projectilesEnemiesTemp = new ArrayList<Projectile>(); //lista temporária de projéteis para projéteis
        for (int i = 0; i < enemyProjectiles; i++) {
            projectilesEnemiesTemp.add(new Projectile(2));
        }

        //inicialização de Inimigo Tipo 1
        var enemyOne = new EnemyTypeOne(Util.INACTIVE, 0, 0, 0, 0, 9.0, Instant.EPOCH, Instant.EPOCH, Instant.EPOCH, 0, 0, 0, projectilesEnemiesTemp);
        var enemiesTempOne = new ArrayList<Enemy>();
        for (int i = 0; i < enemyProjectiles; i++) {
            enemiesTempOne.add(enemyOne);
        }
        var armyEnemyOne = new EnemiesArmy(enemiesTempOne, currentTime.plusMillis(2000));

        //inicialização de Inimigo Tipo 2
        var enemyTwo = new EnemyTypeTwo(Util.INACTIVE, 0, 0, 0, 0, 9.0, Instant.EPOCH, Instant.EPOCH, Instant.EPOCH, 0, 0, 0, projectilesEnemiesTemp, (Util.WIDTH * 0.20), 0);
        var enemiesTempTwo = new ArrayList<Enemy>();
        for (int i = 0; i < enemyProjectiles; i++) {
            enemiesTempTwo.add(enemyTwo);
        }
        var armyEnemyTwo = new EnemiesArmy(enemiesTempTwo, currentTime.plusMillis(2000));

        // estrelas que formam o fundo de primeiro plano
        var starsFrist = new Background(new ArrayList<Double>(20), new ArrayList<Double>(20), 0.070, 0);
        starsFrist.initializeStars();
        var starsSecond = new Background(new ArrayList<Double>(50), new ArrayList<Double>(50), 0.045, 0);
        starsSecond.initializeStars();

        //Inicialização da interface
        var gameFrame = new GameFrame(graphics, frame);
        gameFrame.initGraphics();

        while (running) {
            delta = Duration.between(Instant.now(), currentTime).toMillis();
            currentTime = Instant.now();

            //verificação de colisões

            if (player.getState() == Util.ACTIVE) {

                // colisões player - projeteis (inimigo 1)
                for (int i = 0; i < armyEnemyOne.getEnemies().size(); i++) {
                    for (int j = 0; j < armyEnemyOne.getEnemies().get(i).getProjectiles().size(); j++) {
                        player.colideProjectile(armyEnemyOne.getEnemies().get(i).getProjectiles().get(j));
                    }
                }

                // colisões player - projeteis (inimigo 2)
                for (int i = 0; i < armyEnemyTwo.getEnemies().size(); i++) {
                    for (int j = 0; j < armyEnemyTwo.getEnemies().get(i).getProjectiles().size(); j++) {
                        player.colideProjectile(armyEnemyTwo.getEnemies().get(i).getProjectiles().get(j));
                    }
                }

                // colisões player - inimigo 1
                for (int i = 0; i < armyEnemyOne.getEnemies().size(); i++) {
                    player.colideCharacters(armyEnemyOne.getEnemies().get(i));
                }

                // colisões player - inimigo 2
                for (int i = 0; i < armyEnemyTwo.getEnemies().size(); i++) {
                    player.colideCharacters(armyEnemyTwo.getEnemies().get(i));
                }

                //colisões projeteis (player) - inimigos
                for (int i = 0; i < player.getProjectiles().size(); i++) {
                    for (int j = 0; j < armyEnemyOne.getEnemies().size(); j++) {
                        armyEnemyOne.getEnemies().get(i).colideProjectile(player.getProjectiles().get(i));
                    }

                    for (int j = 0; j < armyEnemyTwo.getEnemies().size(); j++) {
                        armyEnemyTwo.getEnemies().get(i).colideProjectile(player.getProjectiles().get(i));
                    }
                }

                //atualização de projéteis (player)
                player.updateProjectiles(delta);

                for (int i = 0; i < armyEnemyOne.getEnemies().size(); i++) {
                    armyEnemyOne.getEnemies().get(i).updateProjectiles(delta);
                }

                for (int i = 0; i < armyEnemyTwo.getEnemies().size(); i++) {
                    armyEnemyTwo.getEnemies().get(i).updateProjectiles(delta);
                }

                for (int i = 0; i < armyEnemyOne.getEnemies().size(); i++) {
                    armyEnemyOne.getEnemies().get(i).attack(player, currentTime, delta);
                }

                for (int i = 0; i < armyEnemyTwo.getEnemies().size(); i++) {
                    armyEnemyTwo.getEnemies().get(i).attack(player, currentTime, delta);
                }
            }
            running = false;
        }
    }
}
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
        long delta = 0;

        //inicialização do player
        Player player = new Player(Util.ACTIVE, Util.WIDTH / 2, Util.HEIGHT * 0.90, 0.25, 0.25, 12.0, Instant.EPOCH, Instant.EPOCH, currentTime, enemyProjectiles, 2);

        //inicialização de Inimigo Tipo 1
        var enemiesOne = new ArrayList<Enemy>();
        for (int i = 0; i < enemyProjectiles; i++) {
            var enemyOne = new EnemyTypeOne(Util.INACTIVE, 0, 0, 0, 0, 9.0, Instant.EPOCH, Instant.EPOCH, Instant.EPOCH, 0, 0, 0, 10, 2);
            enemiesOne.add(enemyOne);
        }
        var armyEnemyOne = new EnemiesArmy(enemiesOne, currentTime.plusMillis(2000));

        //inicialização de Inimigo Tipo 2
        var enemiesTwo = new ArrayList<Enemy>();
        for (int i = 0; i < enemyProjectiles; i++) {
            var enemyTwo = new EnemyTypeTwo(Util.INACTIVE, 0.0, 0.0, 0.0, 0.0, 9.0, Instant.EPOCH, Instant.EPOCH,
                    Instant.EPOCH, 0.0, 0.0, 0.0, (int) (Util.WIDTH * 0.20), 10, 2);
            enemiesTwo.add(enemyTwo);
        }
        var armyEnemyTwo = new EnemiesArmy(enemiesTwo, currentTime.plusMillis(2000));

        // estrelas que formam o fundo de primeiro plano
        var starsFrist = new BackgroundStars(0.045, 0.0, 20);
        var starsSecond = new BackgroundStars(0.045, 0.0, 50);

        //Inicialização da interface
        GameLib.initGraphics();

        while (running) {
            delta = Duration.between(currentTime, Instant.now()).toMillis();
            currentTime = Instant.now();

            //verificação de colisões

            if (player.getState() == Util.ACTIVE) {

                // colisões player - projeteis inimigos
                for (int i = 0; i < armyEnemyOne.getEnemies().size(); i++) {
                    for (int j = 0; j < armyEnemyOne.getEnemies().get(i).getProjectiles().size(); j++) {
                        player.colide(armyEnemyOne.getEnemies().get(i).getProjectiles().get(j));
                    }
                }

                // colisões player - inimigos
                for (int i = 0; i < armyEnemyOne.getEnemies().size(); i++) {
                    player.colide(armyEnemyOne.getEnemies().get(i));
                }
                for (int i = 0; i < armyEnemyTwo.getEnemies().size(); i++) {
                    player.colide(armyEnemyTwo.getEnemies().get(i));
                }
            }

            //colisões projeteis (player) - inimigos
            for (int i = 0; i < player.getProjectiles().size(); i++) {
                for (int j = 0; j < armyEnemyOne.getEnemies().size(); j++) {
                    armyEnemyOne.getEnemies().get(i).colide(player.getProjectiles().get(i));
                }

                for (int j = 0; j < armyEnemyTwo.getEnemies().size(); j++) {
                    armyEnemyTwo.getEnemies().get(i).colide(player.getProjectiles().get(i));
                }
            }

            //atualização de projéteis
            player.updateProjectiles(delta);
            for (int i = 0; i < armyEnemyOne.getEnemies().size(); i++) {
                armyEnemyOne.getEnemies().get(i).updateProjectiles(delta);
            }
            for (int i = 0; i < armyEnemyTwo.getEnemies().size(); i++) {
                armyEnemyTwo.getEnemies().get(i).updateProjectiles(delta);
            }

            //inimigos tipo 1 e 2
            for (int i = 0; i < armyEnemyOne.getEnemies().size(); i++) {
                armyEnemyOne.getEnemies().get(i).attack(player, currentTime, delta);
            }
            for (int i = 0; i < armyEnemyTwo.getEnemies().size(); i++) {
                armyEnemyTwo.getEnemies().get(i).attack(player, currentTime, delta);
            }

            // verificando se novos inimigos devem ser lançados
            armyEnemyOne.castEnemies(currentTime);
            armyEnemyTwo.castEnemies(currentTime);

            // Verificando se a explosão do player já acabou. Ao final da explosão, o player volta a ser controlável
            if (player.getState() == Util.EXPLODE && currentTime.isAfter(player.getExplosionEnd())) {
                player.setState(Util.ACTIVE);
            }

            //Verificando entrada do usuário (teclado)
            if (player.getState() == Util.ACTIVE) {
                if (GameLib.isKeyPressed(Util.KEY_UP))
                    player.setCoordinateY(player.getCoordinateY() - delta * player.getSpeedY());
                if (GameLib.isKeyPressed(Util.KEY_DOWN))
                    player.setCoordinateY(player.getCoordinateY() + delta * player.getSpeedY());
                if (GameLib.isKeyPressed(Util.KEY_LEFT))
                    player.setCoordinateX(player.getCoordinateX() - delta * player.getSpeedX());
                if (GameLib.isKeyPressed(Util.KEY_RIGHT))
                    player.setCoordinateX(player.getCoordinateX() + delta * player.getSpeedX());
                if (GameLib.isKeyPressed(Util.KEY_CONTROL) && currentTime.isAfter(player.getNextShoot())) {
                    int free = player.findFreeIndex();
                    if (free < player.getProjectiles().size()) {
                        player.getProjectiles().get(free).setCoordinateX(player.getCoordinateX());
                        player.getProjectiles().get(free).setCoordinateY(player.getCoordinateY() - 2 * player.getRadius());
                        player.getProjectiles().get(free).setSpeedX(0);
                        player.getProjectiles().get(free).setSpeedY(-1.0);
                        player.getProjectiles().get(free).setState(Util.ACTIVE);
                        player.setNextShoot(currentTime.plusMillis(100));
                    }
                }
            }
            if (GameLib.isKeyPressed(Util.KEY_ESCAPE)) running = false;

            //Verificação se as coordenadas do player estão dentro da tela
            if (player.getCoordinateX() < 0) player.setCoordinateX(0);
            if (player.getCoordinateX() >= Util.WIDTH) player.setCoordinateX(Util.WIDTH - 1);
            if (player.getCoordinateY() < 25) player.setCoordinateY(25);
            if (player.getCoordinateY() >= Util.HEIGHT) player.setCoordinateY(Util.HEIGHT - 1);

            //Desenho da cena

            //Plano de fundo distante
            GameLib.setColor(Color.DARK_GRAY);
            starsSecond.setCount(starsSecond.getStars().getFirst().getSpeed() * delta);

            for (int i = 0; i < starsSecond.getStars().size(); i++) {
                var star = starsSecond.getStars().get(i);
                GameLib.fillRect(star.getCoordinateX(), (star.getCoordinateY() + starsSecond.getCount()) % Util.HEIGHT, 2, 2);
            }

            //Plano de fundo
            GameLib.setColor(Color.GRAY);
            starsFrist.setCount(starsFrist.getStars().getFirst().getSpeed() * delta);

            for (int i = 0; i < starsFrist.getStars().size(); i++) {
                var star = starsFrist.getStars().get(i);
                GameLib.fillRect(star.getCoordinateX(), (star.getCoordinateY() + starsFrist.getCount()) % Util.HEIGHT, 2, 2);
            }

            //desenho - player
            if (player.getState() == Util.EXPLODE) {
                var alpha = Duration.between(currentTime, player.getExplosionStart()).toMillis() / Duration.between(player.getExplosionEnd(), player.getExplosionStart()).toMillis();
                GameLib.drawExplosion(player.getCoordinateX(), player.getCoordinateY(), Math.abs(alpha));
            } else {
                GameLib.setColor(Color.BLUE);
                GameLib.drawPlayer(player.getCoordinateX(), player.getCoordinateY(), player.getRadius());
            }

            //projeteis - player
            for (int i = 0; i < player.getProjectiles().size(); i++) {
                var projectile = player.getProjectiles().get(i);
                if (projectile.getState() == Util.ACTIVE) {
                    GameLib.setColor(Color.GREEN);
                    GameLib.drawLine(projectile.getCoordinateX(), projectile.getCoordinateY() - 5, projectile.getCoordinateX(), projectile.getCoordinateY() + 5);
                    GameLib.drawLine(projectile.getCoordinateX() - 1, projectile.getCoordinateY() - 3, projectile.getCoordinateX() - 1, projectile.getCoordinateY() + 3);
                    GameLib.drawLine(projectile.getCoordinateX() + 1, projectile.getCoordinateY() - 3, projectile.getCoordinateX() + 1, projectile.getCoordinateY() + 3);
                }
            }

            //projéteis - inimigos
            for (int i = 0; i < armyEnemyOne.getEnemies().size(); i++) {
                if (armyEnemyOne.getEnemies().get(i).getState() == Util.ACTIVE) {
                    GameLib.setColor(Color.RED);
                    GameLib.drawCircle(armyEnemyOne.getEnemies().get(i).getCoordinateX(), armyEnemyOne.getEnemies().get(i).getCoordinateY(), armyEnemyOne.getEnemies().get(i).getRadius());
                }
            }

            for (int i = 0; i < armyEnemyTwo.getEnemies().size(); i++) {
                if (armyEnemyTwo.getEnemies().get(i).getState() == Util.ACTIVE) {
                    GameLib.setColor(Color.RED);
                    GameLib.drawCircle(armyEnemyTwo.getEnemies().get(i).getCoordinateX(), armyEnemyTwo.getEnemies().get(i).getCoordinateY(), armyEnemyTwo.getEnemies().get(i).getRadius());
                }
            }

            //inimigos tipo 1
            for (int i = 0; i < armyEnemyOne.getEnemies().size(); i++) {
                if (armyEnemyOne.getEnemies().get(i).getState() == Util.EXPLODE) {
                    double alpha = Duration.between(currentTime, armyEnemyOne.getEnemies().get(i).getExplosionStart()).toMillis() / Duration.between(armyEnemyOne.getEnemies().get(i).getExplosionStart(), armyEnemyOne.getEnemies().get(i).getExplosionEnd()).toMillis();
                    GameLib.drawExplosion(armyEnemyOne.getEnemies().get(i).getCoordinateX(), armyEnemyOne.getEnemies().get(i).getCoordinateY(), alpha);
                } else if (armyEnemyOne.getEnemies().get(i).getState() == Util.ACTIVE) {
                    GameLib.setColor(Color.CYAN);
                    GameLib.drawCircle(armyEnemyOne.getEnemies().get(i).getCoordinateX(), armyEnemyOne.getEnemies().get(i).getCoordinateY(), armyEnemyOne.getEnemies().get(i).getRadius());
                }
            }

            //inimigos tipo 2
            for (int i = 0; i < armyEnemyTwo.getEnemies().size(); i++) {
                if (armyEnemyTwo.getEnemies().get(i).getState() == Util.EXPLODE) {
                    double alpha = (double) Duration.between(currentTime, armyEnemyTwo.getEnemies().get(i).getExplosionStart()).toMillis() / Duration.between(armyEnemyTwo.getEnemies().get(i).getExplosionStart(), armyEnemyTwo.getEnemies().get(i).getExplosionEnd()).toMillis();
                    GameLib.drawExplosion(armyEnemyTwo.getEnemies().get(i).getCoordinateX(), armyEnemyTwo.getEnemies().get(i).getCoordinateY(), alpha);
                } else if (armyEnemyTwo.getEnemies().get(i).getState() == Util.ACTIVE) {
                    GameLib.setColor(Color.CYAN);
                    GameLib.drawCircle(armyEnemyTwo.getEnemies().get(i).getCoordinateX(), armyEnemyTwo.getEnemies().get(i).getCoordinateY(), armyEnemyTwo.getEnemies().get(i).getRadius());
                }
            }

            GameLib.display();
            busyWait(currentTime.plusMillis(5));
        }
    }
}
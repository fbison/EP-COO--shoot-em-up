package main;

import components.Projectile;
import components.enemies.Enemy;
import graphics.Background;
import components.enemies.EnemiesArmy;
import components.enemies.EnemyTypeOne;
import components.enemies.EnemyTypeTwo;
import components.Player;
import graphics.Util;
import graphics.GameLib;
import java.awt.*;
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
//        var gameFrame = new GameFrame(null, null);
        GameLib.initGraphics();

        while (running) {
            delta = Duration.between(Instant.now(), currentTime).toMillis();
            currentTime = Instant.now();

            //verificação de colisões

            if (player.getState() == Util.ACTIVE) {

                // colisões player - projeteis inimigos
                for (int i = 0; i < armyEnemyOne.getEnemies().size(); i++) {
                    for (int j = 0; j < armyEnemyOne.getEnemies().get(i).getProjectiles().size(); j++) {
                        player.colideProjectile(armyEnemyOne.getEnemies().get(i).getProjectiles().get(j));
                    }
                }
                for (int i = 0; i < armyEnemyTwo.getEnemies().size(); i++) {
                    for (int j = 0; j < armyEnemyTwo.getEnemies().get(i).getProjectiles().size(); j++) {
                        player.colideProjectile(armyEnemyTwo.getEnemies().get(i).getProjectiles().get(j));
                    }
                }

                // colisões player - inimigos
                for (int i = 0; i < armyEnemyOne.getEnemies().size(); i++) {
                    player.colideCharacters(armyEnemyOne.getEnemies().get(i));
                }
                for (int i = 0; i < armyEnemyTwo.getEnemies().size(); i++) {
                    player.colideCharacters(armyEnemyTwo.getEnemies().get(i));
                }
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

                if (currentTime.isAfter(player.getNextShoot())) {
                    int free = player.findFreeIndex();
                    if (free < player.getProjectiles().size()) {
                        player.getProjectiles().get(free).setCoordinateX(player.getCoordinateX());
                        player.getProjectiles().get(free).setCoordinateY(player.getCoordinateX() - 2 * player.getRadius());
                        player.getProjectiles().get(free).setSpeedX(0);
                        player.getProjectiles().get(free).setSpeedY(-1.0);
                        player.getProjectiles().get(free).setState(Util.ACTIVE);
                        player.setNextShoot(currentTime.plusMillis(100));
                    }
                }
            }
            if (GameLib.isKeyPressed(Util.KEY_UP)) running = false;

            //Verificação se as coordenadas do player estão dentro da tela
            if (player.getCoordinateX() < 0) player.setCoordinateX(0);
            if (player.getCoordinateX() >= Util.WIDTH) player.setCoordinateX(Util.WIDTH - 1);
            if (player.getCoordinateY() < 25) player.setCoordinateY(25);
            if (player.getCoordinateY() >= Util.HEIGHT) player.setCoordinateY(Util.HEIGHT - 1);

            //Desenho da cena
            GameLib.setColor(Color.DARK_GRAY);
            starsSecond.setCount(starsSecond.getSpeed() * delta);

            for (int i = 0; i < starsSecond.getCoordinateX().size(); i++) {
                GameLib.fillRect(starsSecond.getCoordinateX().get(i), (starsSecond.getCoordinateY().get(i) + starsSecond.getCount()) % Util.HEIGHT, 2, 2);
            }

            //desenho - player
            if (player.getState() == Util.EXPLODE) {
                var alpha = Duration.between(currentTime, player.getExplosionStart()).toMillis() / Duration.between(player.getExplosionEnd(), player.getExplosionStart()).toMillis();
                GameLib.drawExplosion(player.getCoordinateX(), player.getCoordinateY(), alpha);
            } else {
                GameLib.setColor(Color.BLUE);
                GameLib.drawPlayer(player.getCoordinateX(), player.getCoordinateY(), player.getRadius());
            }

            //projeteis - player
            for (int i = 0; i < player.getProjectiles().size(); i++) {
                if (player.getProjectiles().get(i).getState() == Util.ACTIVE) {
                    GameLib.setColor(Color.GREEN);
                    GameLib.drawLine(player.getProjectiles().get(i).getCoordinateX(), player.getProjectiles().get(i).getCoordinateY() - 5, player.getProjectiles().get(i).getCoordinateX(), player.getProjectiles().get(i).getCoordinateY() + 5);
                    GameLib.drawLine(player.getProjectiles().get(i).getCoordinateX() - 1, player.getProjectiles().get(i).getCoordinateY() - 3, player.getProjectiles().get(i).getCoordinateX() - 1, player.getProjectiles().get(i).getCoordinateY() + 3);
                    GameLib.drawLine(player.getProjectiles().get(i).getCoordinateX() + 1, player.getProjectiles().get(i).getCoordinateY() + 3, player.getProjectiles().get(i).getCoordinateX() + 1, player.getProjectiles().get(i).getCoordinateY() + 3);
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
                if (armyEnemyOne.getEnemies().get(i).getState() == Util.EXPLODE){
                    double alpha = Duration.between(currentTime, armyEnemyOne.getEnemies().get(i).getExplosionStart()).toMillis() /Duration.between(armyEnemyOne.getEnemies().get(i).getExplosionStart(), armyEnemyOne.getEnemies().get(i).getExplosionEnd()).toMillis();
                    GameLib.drawExplosion(armyEnemyOne.getEnemies().get(i).getCoordinateX(), armyEnemyOne.getEnemies().get(i).getCoordinateY(), alpha);
                } else if(armyEnemyOne.getEnemies().get(i).getState() == Util.ACTIVE){
                    GameLib.setColor(Color.CYAN);
                    GameLib.drawCircle(armyEnemyOne.getEnemies().get(i).getCoordinateX(), armyEnemyOne.getEnemies().get(i).getCoordinateY(), armyEnemyOne.getEnemies().get(i).getRadius());
                }
            }

            //inimigos tipo 2
            for (int i = 0; i < armyEnemyTwo.getEnemies().size(); i++) {
                if (armyEnemyTwo.getEnemies().get(i).getState() == Util.EXPLODE){
                    double alpha = Duration.between(currentTime, armyEnemyTwo.getEnemies().get(i).getExplosionStart()).toMillis() /Duration.between(armyEnemyTwo.getEnemies().get(i).getExplosionStart(), armyEnemyTwo.getEnemies().get(i).getExplosionEnd()).toMillis();
                    GameLib.drawExplosion(armyEnemyTwo.getEnemies().get(i).getCoordinateX(), armyEnemyTwo.getEnemies().get(i).getCoordinateY(), alpha);
                } else if(armyEnemyTwo.getEnemies().get(i).getState() == Util.ACTIVE){
                    GameLib.setColor(Color.CYAN);
                    GameLib.drawCircle(armyEnemyTwo.getEnemies().get(i).getCoordinateX(), armyEnemyTwo.getEnemies().get(i).getCoordinateY(), armyEnemyTwo.getEnemies().get(i).getRadius());
                }
            }

            GameLib.display();
            busyWait(currentTime.plusMillis(5));
        }
    }
}
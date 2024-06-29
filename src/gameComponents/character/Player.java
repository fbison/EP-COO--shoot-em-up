package gameComponents.character;

import gameComponents.character.enemies.EnemiesArmy;
import gameComponents.character.enemies.Enemy;
import graphics.GameLib;
import graphics.Util;

import java.awt.*;
import java.time.Duration;
import java.time.Instant;
import java.util.Scanner;

public class Player extends Character {

    private int life;

    public Player(double coordinateX, double coordinateY, double speedX, double speedY,
                  double radius,Instant nextShoot, int countProjectiles, Color colorPlayer, Color colorProjectile) {
        super(Util.ACTIVE, coordinateX, coordinateY, speedX, speedY, radius, null, null,
                nextShoot, countProjectiles, 0, colorPlayer, colorProjectile);
        life = lifeFromComamndLine();
    }

    @Override
    public void prepareExplosion(){
        life--;
        super.prepareExplosion();
    }

    // Verificando se a explosão do player já acabou. Ao final da explosão, o player volta a ser controlável
    public void backToLife(Instant currentTime){
        if (getState() == Util.EXPLODE && currentTime.isAfter(getExplosionEnd())) {
            setState(Util.ACTIVE);
        }
    }

    //Verificação se as coordenadas do player estão dentro da tela
    public void keepInScren(){
        if (this.getCoordinateX() < 0) this.setCoordinateX(0);
        if (this.getCoordinateX() >= Util.WIDTH) this.setCoordinateX(Util.WIDTH - 1);
        if (this.getCoordinateY() < 25) this.setCoordinateY(25);
        if (this.getCoordinateY() >= Util.HEIGHT) this.setCoordinateY(Util.HEIGHT - 1);
    }

    public void updateProjectiles(long delta) {
        for (Projectile projectile : this.getProjectiles()) {
            if(projectile.getState() == Util.ACTIVE) {
                if (projectile.getCoordinateY() < 0)
                    projectile.setState(Util.INACTIVE);
                else {
                    projectile.setCoordinateX(projectile.getCoordinateX() + (projectile.getSpeedX() * delta));
                    projectile.setCoordinateY(projectile.getCoordinateY() + (projectile.getSpeedY() * delta));
                }
            }
        }
    }

    public void atack(Instant currentTime){
        int free = findFreeIndex();
        if (free < getProjectiles().size()) {
            getProjectiles().get(free).setCoordinateX(getCoordinateX());
            getProjectiles().get(free).setCoordinateY(getCoordinateY() - 2 * getRadius());
            getProjectiles().get(free).setSpeedX(0);
            getProjectiles().get(free).setSpeedY(-1.0);
            getProjectiles().get(free).setState(Util.ACTIVE);
            setNextShoot(currentTime.plusMillis(100));
        }
    }

    public void verifyActions(Instant currentTime, long delta){
        if (getState() == Util.ACTIVE) {
            if (GameLib.isKeyPressed(Util.KEY_UP))
                setCoordinateY(getCoordinateY() - delta * getSpeedY());
            if (GameLib.isKeyPressed(Util.KEY_DOWN))
                setCoordinateY(getCoordinateY() + delta * getSpeedY());
            if (GameLib.isKeyPressed(Util.KEY_LEFT))
                setCoordinateX(getCoordinateX() - delta * getSpeedX());
            if (GameLib.isKeyPressed(Util.KEY_RIGHT))
                setCoordinateX(getCoordinateX() + delta * getSpeedX());
            if (GameLib.isKeyPressed(Util.KEY_CONTROL)) {
                if(currentTime.isAfter(getNextShoot())) {
                    atack(currentTime);
                }
            }
        }
    }

    public void drawProjectiles(){
        for (Projectile projectile : getProjectiles()){
            if (projectile.getState() == Util.ACTIVE) {
                GameLib.setColor(projectile.getColor());
                GameLib.drawLine(projectile.getCoordinateX(), projectile.getCoordinateY() - 5, projectile.getCoordinateX(), projectile.getCoordinateY() + 5);
                GameLib.drawLine(projectile.getCoordinateX() - 1, projectile.getCoordinateY() - 3, projectile.getCoordinateX() - 1, projectile.getCoordinateY() + 3);
                GameLib.drawLine(projectile.getCoordinateX() + 1, projectile.getCoordinateY() - 3, projectile.getCoordinateX() + 1, projectile.getCoordinateY() + 3);
            }
        }
    }

    public void draw(Instant currentTime){
        if (getState() == Util.EXPLODE) {
            var alpha = Duration.between(currentTime, getExplosionStart()).toMillis() / Duration.between(getExplosionEnd(), getExplosionStart()).toMillis();
            GameLib.drawExplosion(getCoordinateX(), getCoordinateY(), Math.abs(alpha));
        } else if (getLife() > 0){
            GameLib.setColor(getColor());
            GameLib.drawPlayer(getCoordinateX(), getCoordinateY(), getRadius());
            drawProjectiles();
        }

    }

    public void checkCollisions(EnemiesArmy army)
    {
        if(getState() == Util.ACTIVE) {
            checkCollisionsWithProjectiles(army);
            checkCollisionsWithEnemys(army);
        }
    }

    public void checkCollisionsWithProjectiles(EnemiesArmy army)
    {
        for(Enemy enemy : army.getEnemies()){
            for(Projectile projectile: enemy.getProjectiles()) {
                colide(projectile);
            }
        }
    }

    public void checkCollisionsWithEnemys(EnemiesArmy army)
    {
        for(Enemy enemy : army.getEnemies()){
            colide(enemy);
        }
    }

    //getter do atributo life que sera utilizado para printar a barra de vida
    public int getLife() {
        return life;
    }

    public void setLife (int life){
        this.life = life;
    }

    public int lifeFromComamndLine(){
        Scanner scanner = new Scanner(System.in);

        System.out.println("Insira a quantidade de vidas desejada!");
        return scanner.nextInt();
    }
}


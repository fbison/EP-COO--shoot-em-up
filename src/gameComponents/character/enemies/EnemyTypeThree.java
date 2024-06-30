package gameComponents.character.enemies;

import gameComponents.character.Player;
import graphics.GameLib;
import graphics.Util;

import java.awt.*;
import java.time.Instant;

public class EnemyTypeThree extends Enemy {
    private boolean movingRight;

    public EnemyTypeThree(Instant currentTime) {
        super(Util.ACTIVE, Math.random() * (Util.WIDTH - 20.0) + 10.0,
                Math.random() * (Util.HEIGHT / 2.0) + 10.0, 0, 0, 12.0,
                null, null, currentTime.plusMillis(500), 0, 0, 0.20 + Math.random() * 0.15,
                10, 2, Color.GREEN, Color.YELLOW);
        this.movingRight = true;
    }
    @Override
    public Instant nextCast(Instant currentTime) {
        return currentTime.plusMillis(500);
    }
    @Override
    public void attack(Player player, Instant currentTime, long delta) {
        if (getState() == Util.EXPLODE) {
            if (currentTime.isAfter(getExplosionEnd())) {
                setState(Util.INACTIVE);
            }
        } else if (isActive()) {
            // Movimentação horizontal
            if (movingRight) {
                setCoordinateX(getCoordinateX() + getSpeed() * delta);
                if (getCoordinateX() > Util.WIDTH - 10) {
                    movingRight = false;
                }
            } else {
                setCoordinateX(getCoordinateX() - getSpeed() * delta);
                if (getCoordinateX() < 10) {
                    movingRight = true;
                }
            }

            // Calcular a direção do disparo em direção ao jogador com variação aleatória
            if (currentTime.isAfter(getNextShoot()) && getCoordinateY() < player.getCoordinateY()) {
                int free = findFreeIndex();
                if (free < getProjectiles().size()) {
                    double dx = player.getCoordinateX() - getCoordinateX();
                    double dy = player.getCoordinateY() - getCoordinateY();
                    double angle = Math.atan2(dy, dx) + (Math.random() - 0.5) * 0.1; // Variação de -0,05 a 0,05 radianos

                    getProjectiles().get(free).setCoordinateX(getCoordinateX());
                    getProjectiles().get(free).setCoordinateY(getCoordinateY());
                    getProjectiles().get(free).setSpeedX(Math.cos(angle) * 0.45);
                    getProjectiles().get(free).setSpeedY(Math.sin(angle) * 0.45);
                    getProjectiles().get(free).setState(Util.ACTIVE);

                    setNextShoot(currentTime.plusMillis((long) (200 + Math.random() * 500)));
                }
            }
        }
    }

    @Override
    public void draw(Instant currentTime){
        if (getState() == Util.EXPLODE) {
            drawExplosion(currentTime);
        } else if (isActive()) {
            GameLib.setColor(getColor());
            GameLib.drawHexagon(getCoordinateX(), getCoordinateY(), getRadius());
        }
    }
}
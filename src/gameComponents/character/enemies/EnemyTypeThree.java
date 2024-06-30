package gameComponents.character.enemies;

import gameComponents.character.Player;
import graphics.GameLib;
import graphics.Util;

import java.awt.*;
import java.time.Instant;

public class EnemyTypeThree extends Enemy {
    private boolean movingRight;

    public EnemyTypeThree() {
        super(Util.INACTIVE, 0, 0, 0, 0, 12.0, null, null, null, 0, 0, 0, 10, 2, Color.GREEN, Color.YELLOW);
        this.movingRight = true;
    }

    @Override
    public void attack(Player player, Instant currentTime, long delta) {
        if (getState() == Util.EXPLODE) {
            if (currentTime.isAfter(getExplosionEnd())) {
                setState(Util.INACTIVE);
            }
        } else if (getState() == Util.ACTIVE) {
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
                    double angle = Math.atan2(dy, dx) + (Math.random() - 0.5) * 0.1; // Variação de -0.05 a 0.05 radianos

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
    public Instant cast(Instant currentTime) {
        // Garante que o inimigo não nasça fora da área visível
        setCoordinateX(Math.random() * (Util.WIDTH - 20.0) + 10.0);
        setCoordinateY(Math.random() * (Util.HEIGHT / 2.0) + 10.0); // Ajuste para garantir que não nasça fora da tela

        setSpeed(0.20 + Math.random() * 0.15);
        setAngle(0); // Ângulo inicial não é relevante para movimento horizontal
        setRotationSpeed(0);
        setState(Util.ACTIVE);
        setNextShoot(currentTime.plusMillis(500));
        movingRight = true; // Inicia movendo para a direita
        return currentTime.plusMillis(500);
    }


    @Override
    public void draw(Instant currentTime){
        if (getState() == Util.EXPLODE) {
            drawExplosion(currentTime);
        } else if (getState() == Util.ACTIVE) {
            GameLib.setColor(getColor());
            GameLib.drawHexagon(getCoordinateX(), getCoordinateY(), getRadius());
        }
    }
}
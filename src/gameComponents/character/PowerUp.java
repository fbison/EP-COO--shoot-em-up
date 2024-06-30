package gameComponents.character;
import gameComponents.essential.Component;
import graphics.GameLib;
import graphics.Util;

import java.awt.*;
import java.util.Random;

public class PowerUp extends Component {
    private boolean active;

    public PowerUp() {
        super(Util.INACTIVE, 0, 0, 0, 0, 10, Color.YELLOW);
        this.active = false;
    }

    public void activate() {
        Random random = new Random();
        this.setCoordinateX(random.nextDouble() * Util.WIDTH);
        this.setCoordinateY(random.nextDouble() * Util.HEIGHT);
        this.setState(Util.ACTIVE);
        this.active = true;
    }

    public void deactivate() {
        this.setState(Util.INACTIVE);
        this.active = false;
    }

    public boolean isActive() {
        return this.active;
    }

    public void draw() {
        if (this.getState() == Util.ACTIVE) {
            GameLib.setColor(this.getColor());
            GameLib.drawStar(this.getCoordinateX(), this.getCoordinateY(), this.getRadius());
        }
    }

    public void checkCollision(Player player) {
        if (this.getState() == Util.ACTIVE && player.getState() == Util.ACTIVE) {
            double dx = this.getCoordinateX() - player.getCoordinateX();
            double dy = this.getCoordinateY() - player.getCoordinateY();
            double dist = Math.sqrt(dx * dx + dy * dy);

            if (dist < (this.getRadius() + player.getRadius()) * 0.8) {
                player.activateImmunity();
                this.deactivate();
            }
        }
    }
}

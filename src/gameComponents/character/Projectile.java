package gameComponents.character;

import gameComponents.essential.Component;
import graphics.Util;

import java.awt.*;

public class Projectile extends Component {
    // Constructor
    public Projectile(int state, double coordinateX, double coordinateY, double speedX, double speedY, double radius, Color color) {
        super(state, coordinateX, coordinateY, speedX, speedY, radius, color);
    }

    public Projectile(int radius, Color color){
        this(Util.INACTIVE,0, 0, 0, 0,radius, color);
    }
}
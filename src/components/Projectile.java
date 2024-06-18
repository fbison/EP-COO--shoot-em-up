package components;

import gameLib.Util;

public class Projectile extends Component {
    // Constructor
    public Projectile(int state, double coordinateX, double coordinateY, double speedX, double speedY, double radius) {
        super(state, coordinateX, coordinateY, speedX, speedY, radius);
    }

    public Projectile(int radius){
        this(Util.INACTIVE,0, 0, 0, 0,radius);
    }
}
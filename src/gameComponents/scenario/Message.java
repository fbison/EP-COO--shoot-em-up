package gameComponents.scenario;

import gameComponents.essential.GameObject;
import graphics.GameLib;
import graphics.Util;

import java.awt.*;

public class Message extends GameObject {

    private String message;
    private static final double width = 200;
    private static final double height = 100;

    public Message(String message){
        super(Util.WIDTH/2,Util.HEIGHT/2, Color.darkGray);
        this.message = message;
    }

    public void update(){
        //desenha o quadrado de fundo
        GameLib.setColor(this.getColor());
        GameLib.fillRect(this.getCoordinateX(), this.getCoordinateY(), this.width, this.height);

        //escreve a mensagem
        GameLib.setColor(Color.black);
        GameLib.setFont(new Font("Arial", Font.BOLD, 16));

        FontMetrics metrics = GameLib.getFontMetrics(GameLib.getFont());
        int textWidth = metrics.stringWidth(message);
        int textHeight = metrics.getHeight();

        int textX = (int)getCoordinateX() - textWidth / 2;
        int textY = (int)getCoordinateY() - textHeight / 2 + metrics.getAscent();

        GameLib.drawString(message, textX, textY);
    }

}

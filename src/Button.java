import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.EventListener;
import java.util.Vector;

public class Button{
    private String text;
    private Vector<Float> position;
    private boolean pressed;


    public Button(String text, Vector<Float> position) {
    this.text = text;
    this.position = position;
    pressed = false;

    }



    public boolean update(Vector<Float> mousePos,boolean mouseDown){
        if(mouseDown && (mousePos.get(0) >= position.get(0)&& mousePos.get(0) <= (position.get(0)+100) && (mousePos.get(1) >= position.get(1)&& mousePos.get(1) <= (position.get(1)+50)))){
            pressed = true;
        }
        return  pressed;
    }

    public void draw(Graphics2D g){
        g.setColor(Color.WHITE);
        g.drawRect(Math.round((position.get(0))), Math.round((position.get(1))),100,50);
        g.fillRect(Math.round((position.get(0))), Math.round((position.get(1))),100,50);
        g.setColor(Color.BLACK);
        g.drawString(this.text,Math.round((position.get(0)+20)), Math.round((position.get(1)+20)));
    }
}

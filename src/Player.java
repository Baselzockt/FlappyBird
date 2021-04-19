import javafx.geometry.BoundingBox;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.Vector;

public class Player {

    private Vector<Float> position;
    private boolean spacePressed;
    private Vector<Float> forceVector;
    private float jumpTimeOut;
    private Rectangle size;
    private double scale;

    public Player(){
        position = new Vector<>();
        position.add(200.0f);
        position.add(250.0f);
        size = new Rectangle(70,50);
        scale = 0.8;

        jumpTimeOut = 0;

        forceVector = new Vector<>();
        forceVector.add(0f);
        forceVector.add(0f);

        spacePressed = false;


        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {
            int pressed = 0;
            @Override
            public boolean dispatchKeyEvent(KeyEvent ke) {

                    switch (ke.getID()) {
                        case KeyEvent.KEY_PRESSED:
                            if (ke.getKeyCode() == KeyEvent.VK_SPACE) {
                                if(pressed == 0) {
                                    spacePressed = true;
                                    pressed++;
                                }
                            }
                            break;

                        case KeyEvent.KEY_RELEASED:
                            if (ke.getKeyCode() == KeyEvent.VK_SPACE) {
                                spacePressed = false;
                                pressed = 0;
                            }
                            break;
                    }
                    return false;
                }

        });

    }

    public void draw(Graphics2D g){
        g.setColor(Color.BLUE);
        AffineTransform old = g.getTransform();
        g.scale(scale,scale);
        g.translate(position.get(0),position.get(1));
        g.rotate(Math.toRadians((double)forceVector.get(1)*2));

        g.fillOval(0,0,size.width,size.height);

        g.setColor(Color.ORANGE);
        g.fillOval(60,25,20,10);

        g.setColor(Color.BLACK);
        g.fillOval(60,15,5,5);


        g.setColor(Color.lightGray);
        g.fillOval(0,10,50,25);

        g.setTransform(old);
    }



    public boolean update(double deltaTime, ArrayList<Enemy> walls,int height){


        for(Enemy wall : walls){
            if(this.position.get(0) >= wall.getPosition() && this.position.get(0) <= (wall.getPosition()+wall.getWidth())){
               if(this.position.get(1) >= (wall.getGapPosition()+(wall.getGapSize()/2)) || this.position.get(1) <= (wall.getGapPosition()-(wall.getGapSize()/2))){
                   return false;
               }
            }
        }

        if(position.get(1) >= height ){
            return false;
        }


        if(spacePressed){

                forceVector.set(1, -6f);
                jumpTimeOut = 50;
                spacePressed = false;

        }

        if(position.get(1) <= ((float)size.height/2)+20){
            jumpTimeOut = 0;
            forceVector.set(1,0f);
        }





        if(forceVector.get(1)+0.3f < 5f && jumpTimeOut <= 0) {
            forceVector.set(1, (forceVector.get(1) + 0.3f));
        }else{
            jumpTimeOut -= 0.475 *deltaTime;
        }

        position.set(1,position.get(1)+((forceVector.get(1)*(float)deltaTime)/20));
        return true;
    }
}

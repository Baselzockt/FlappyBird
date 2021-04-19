import java.awt.*;
import java.awt.event.MouseListener;
import java.util.Random;
import java.util.Vector;

public class Enemy {
    private float position;
    private float gapPosition;
    private float gapSize;
    private int width;

    public Enemy(float windowWidth,float windowHeight){
        position = windowWidth + 110;
        Random r = new Random();
        gapSize = 110;

        width = 100;

        gapPosition =(float)( 100 + Math.random() * ((windowHeight-100) - 100));
    }
    public void update(double deltaTime){
        position-= (0.1*deltaTime);
        System.out.println(position);
    }

    public float getGapPosition(){
        return gapPosition;
    }




    public void draw(Graphics2D g){
        g.setColor(Color.GREEN);
        g.fillRect(Math.round(position),0,100,Math.round(gapPosition-gapSize/2));
        g.fillRect(Math.round(position),Math.round(gapPosition+55),width,10000000);

    }

   public float getPosition(){
        return position;
   }

   public int getWidth(){
        return width;
   }

   public float getGapSize(){
        return  gapSize;
   }

}

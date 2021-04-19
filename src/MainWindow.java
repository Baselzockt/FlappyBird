import javafx.scene.input.MouseButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Vector;

public class MainWindow extends JFrame{
    public static void main(String[] args) {
        MainWindow mainWindow = new MainWindow();
    }
    private double deltaTime;
   private BufferedImage  bf;

    public MainWindow(){
        System.setProperty("sun.java2d.opengl","true");
        this.setBackground(Color.BLACK);
        this.setTitle("Flappy Bird");
        this.setSize(600,500);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
        bf = new BufferedImage( this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g = (Graphics2D)bf.getGraphics();
        setup();
        deltaTime = 0;
        setIgnoreRepaint(true);

        while(true){
            double before = System.currentTimeMillis();
            update(deltaTime);
            draw(g);
            deltaTime = System.currentTimeMillis() - before;
        }




    }

    private Button startBtn;
    private Button retryButton;
    private Player flappy;
    private ArrayList<Enemy> walls;
    private Vector<Float> mousePosition;
    private  boolean mousePressed;
    private double deltaTimeWallSpawn;
    private Vector<Float> pos;

    private void setup(){
        flappy = new Player();
        walls = new ArrayList<>();
        pos =  new Vector<Float>();
       pos.add(200.0f);
       pos.add(250.0f);
       mousePosition = new Vector<>();
       mousePosition.add(0f);
       mousePosition.add(0f);
       mousePressed = false;
        startBtn = new Button("Start",pos);
        retryButton = new Button("Retry",pos);
        deltaTimeWallSpawn = 2500;
      this.addMouseListener(new MouseListener() {
          @Override
          public void mouseClicked(MouseEvent mouseEvent) {

          }

          @Override
          public void mousePressed(MouseEvent mouseEvent) {
           if(mouseEvent.getButton() == 1){
               System.out.println("x:"+mouseEvent.getX());
               System.out.println("y:"+mouseEvent.getY());
               mousePosition.set(0,(float)mouseEvent.getX());
               mousePosition.set(1,(float)mouseEvent.getY());
               mousePressed = true;
           }
          }

          @Override
          public void mouseReleased(MouseEvent mouseEvent) {
              mousePressed = false;
          }

          @Override
          public void mouseEntered(MouseEvent mouseEvent) {

          }

          @Override
          public void mouseExited(MouseEvent mouseEvent) {

          }
      });
    }

private boolean start = false;
private  boolean alive = false;

    private void update(double deltaTime){
        if(start && alive) {
            if(walls.size() < 4 && deltaTimeWallSpawn > 3000){
                walls.add(new Enemy(this.getWidth(),this.getHeight()));
                deltaTimeWallSpawn = 0;
            }
            deltaTimeWallSpawn += deltaTime;
            for (int i = walls.size()-1; i>0; i--) {
               walls.get(i).update(deltaTime);
                if(walls.get(i).getPosition() < -100){
                    walls.remove(i);
                }
            }
           alive = flappy.update(deltaTime,walls,this.getHeight());
        }else if (!start){
          start =  startBtn.update(mousePosition,mousePressed);
          alive = start;
          mousePressed = false;
        }else if(!alive){
          alive =  retryButton.update(mousePosition,mousePressed);
          if(alive){
              flappy = new Player();
              retryButton = new Button("Retry",pos);
              walls = new ArrayList<>();
          }
        }
    }

    private void drawBackground(Graphics2D g){
        g.setColor(Color.BLACK);
        g.drawRect(0,0,this.getWidth(),this.getHeight());
        g.fillRect( 0,0,this.getWidth(),this.getHeight());
    }



    private  void draw(Graphics2D g){
        drawBackground(g);

        if(start && alive) {
            flappy.draw(g);
            for (Enemy wall : walls) {
                wall.draw(g);
            }
        }else if (!start){
            startBtn.draw(g);
        }else if (!alive){
            retryButton.draw(g);
        }
        getGraphics().drawImage(bf,0,0,null);
    }


}

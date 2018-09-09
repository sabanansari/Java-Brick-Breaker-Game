package setup;
import display.Display;

import java.awt.*;
import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;


public class gameSetup implements Runnable, KeyListener {
    private Display display;
    private String title;
    private int width,height;
    private BufferStrategy buffer;
    private Thread thread;
    private Graphics g;
    private int ballx=203;
    private int bally=480;
    public int score;

    int batx=203;
    int baty=530;
    boolean left,right,gameOver,space,n;
    int s;
    //array for bricks

    private Rectangle[] Bricks;
    int brickx=30, bricky=60;
    int movex=1;
    int movey=-1;

    Rectangle Ball= new Rectangle(ballx,bally,15,15);
    Rectangle Bat= new Rectangle(batx,baty,40,10);

    public gameSetup(String title,int width,int height)
    {
       this.title=title;
       this.height=height;
       this.width=width;
       this.score=score;
       this.s=s;
       gameOver=false;
    }
    //overloaded Constructor
    public gameSetup(String title,int width, int height, int score)
    { this.title=title;
        this.height=height;
        this.width=width;
        this.score=score;
        gameOver=false;}

    public void init() {
        display = new Display(height, width, title);
        display.frame.addKeyListener(this);

        if(score==15)
        {score=0;
         s=20;}
        else
        s=15;
        ourbricks(s);

    }
    public void ourbricks(int i){
         Bricks = new Rectangle[i];
         for ( int j = 0; j < Bricks.length; j++) {
             if(i==15){
             Bricks[j] = new Rectangle(brickx, bricky, 50, 20);

             if (j == 6) {
                 brickx = 60;
                 bricky = 60 + 25;

             }
             if (j == 11) {
                 brickx = 80;
                 bricky = 60 + 25 + 25;
             }}
             else
             { Bricks[j] = new Rectangle(brickx,bricky ,50,20);

                 if (j ==7) {
                 brickx = 25;
                 bricky = 60 + 25;

             }
                 if (j == 13) {
                     brickx = 100;
                     bricky = 60 + 25 + 25;}}
             brickx += 52;
         }
     }
    public synchronized void start(){
         thread=new Thread(this);
         thread.start();
    }
    public synchronized  void stop()
    {
       try{
           thread.join();}
           catch(InterruptedException e)
           { e.printStackTrace();
       }
    }

    public void drawBall(Graphics g){
        g.setColor(Color.blue);
        g.fillOval(Ball.x,Ball.y,15,15);
    }
    public void drawBat(Graphics g){
        g.setColor(Color.green);
        g.fillRect(Bat.x,Bat.y,40,10);
    }
    public void drawBricks(Graphics g) {
        for (int i = 0; i < Bricks.length; i++) {
            if(Bricks[i]!=null) {
                g.setColor(Color.gray);
                g.fillRect(Bricks[i].x - 2, Bricks[i].y - 2, 60, 22);
                g.setColor(Color.red);
                g.fillRect(Bricks[i].x, Bricks[i].y, 50, 20);
            }
        }
    }

    public void drawScore(Graphics g){
        String a= Integer.toString(score);
        g.setColor(Color.black);
        g.setFont(new Font("arial",Font.PLAIN,30));
        g.drawString("Score: "+ a,180,570);

    }
    //next level
    public void nextLevel(Graphics g){

        g.setColor(Color.red);
            g.setFont(new Font("arial",Font.BOLD,40));
            g.drawString("Press n for next Level",25,350);
        }


   //Game Over
    public void gameOver(Graphics g){
        g.setColor(Color.red);
        g.setFont(new Font("arial",Font.BOLD,40));
        g.drawString("Game Over",180,300);
        g.drawString("Press Space to Restart",25,350);
    }
    public void tick(){
        if(Ball.x>=450|| Ball.x<=22){
            movex= -movex;
        }
        if (Ball.y<=40 ){
            movey=-movey;
        }
        Ball.x+=movex;
        Ball.y+=movey;
        //here we get to know how game overed
        if(Ball.y>530){
            gameOver=true;
            movey=-movey;

        }
        if(left){
            if(Bat.x>=22)
            Bat.x-=1;
        }
        if(right){
            if(Bat.x<=430)
            Bat.x+=1;
        }
        //ball and bat collision
        if(Bat.intersects(Ball)){
               movey=-movey;
        }
        //ball and bricks collision
         if(gameOver==false)
         { for(int i=0;i<Bricks.length;i++){
            if(Bricks[i]!=null) {
                if (Bricks[i].intersects(Ball)) {
                    movey = -movey;
                    Bricks[i] = null;
                    score += 1;
                }
            }
           }
        }
        if(space) {
            space = false;
            gameSetup setUp = new gameSetup("Brick Breaker", 600, 500);
              setUp.start();
        }
        //for next level
        if(score==15&&n){ n=false;
            gameSetup setUp = new gameSetup("Brick Breaker", 600, 500,15);
            setUp.start();
        }
    }


    public void draw() {
        buffer = display.canvas.getBufferStrategy();
        if (buffer == null) {
            display.canvas.createBufferStrategy(3);
            return;
        }
          g=buffer.getDrawGraphics();
          g.clearRect(0,0,width,height);
          g.setColor(Color.white);
          g.fillRect(22,40,450,550);

          drawBall(g);
          drawBat(g);
          drawBricks(g);
          drawScore(g);
          if(gameOver && score<15)
              gameOver(g);
          if(score==15 && s==15)
              nextLevel(g);
          buffer.show();
          g.dispose();
    }


    @Override
    public void run() {
        init();

        while (true) {
            thread.currentThread();
            try {
                if(s==15){
                thread.sleep(5);}
                else{
                    thread.sleep(1);

                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            tick();
            draw();

        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

         int source=e.getKeyCode();


    }

    @Override
    public void keyPressed(KeyEvent e) {
        int source=e.getKeyCode();
        if(source==KeyEvent.VK_RIGHT){
            right=true;
        }
        if (source==KeyEvent.VK_LEFT){
            left=true;
        }
        if(gameOver)
        { if(source==KeyEvent.VK_SPACE)
         space=true;
        }
        if(score==15)
        { if(source==KeyEvent.VK_N)
            n=true;}
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int source=e.getKeyCode();
        if(source==KeyEvent.VK_RIGHT){
            right=false;
        }
        if (source==KeyEvent.VK_LEFT){
            left=false;
        }

    }
}





package main;
import setup.gameSetup;
public class main {

    public static void main(String[]args)
    {
          gameSetup prevscore=new gameSetup("Brick Breaker",600,500);
          if(prevscore.score==15)
          {  gameSetup setUp=new gameSetup("Brick Breaker",600,500,15);
        setUp.start();}
        else
          {gameSetup setUp=new gameSetup("Brick Breaker",600,500);
                setUp.start();}

    }
}

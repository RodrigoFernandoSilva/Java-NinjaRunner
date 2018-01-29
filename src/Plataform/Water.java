
package Plataform;

//Jplay imports
import jplay.Animation;

//Variables imports
import static Main.DeltaTime.allThreadSleep;
import static Main.DeltaTime.deltaTime;
import static Main.Main.floor;
import static Main.Main.playing;
import static Main.Main.someMethods;
import static Main.Main.window;


//Others imports
import javax.swing.JOptionPane;

/**
 *
 * @author Rodrigo Fernando da Silva
 */
public class Water extends Thread {
    
    //Java variables
    public boolean moveWaterUp = true;
    public double limitWaterMove = 10;
    public double waterInitialPosition;
    public double speedUpDown; //Speed that the water move up or down
    public double y;
    private final String FILE_WAY = "Images/Plataform/";
    
    //Jplay variables
    public Animation water;
    
    @Override
    @SuppressWarnings("SleepWhileInLoop")
    public void run() {
        
        water = new Animation(FILE_WAY + "Water.png");
        y = someMethods.SetPositionBelowWindow(water, window) + 80;
        waterInitialPosition = y;
        
        playing.waterOk = true;
        
        WaitAllThreadsFinish();
        
        while (playing.GetIsPlaying()) {
            
            speedUpDown = playing.GetSpeed() / 5;
            
            //Move the water
            water.x -= floor.GetSpeed() * deltaTime;
            //Reposition the water after it exit the windows
            if (water.x + water.width / 2 < 0)
                water.x += water.width / 2;
            
            //Move the water up and down
            if (moveWaterUp) {
                water.x -= (speedUpDown / 2) * deltaTime;
                y -= speedUpDown * deltaTime;
                if (waterInitialPosition - limitWaterMove > y)
                    moveWaterUp = false;
            } else if (!moveWaterUp) {
                water.x += (speedUpDown / 2) * deltaTime;
                y += speedUpDown * deltaTime;
                if (waterInitialPosition + limitWaterMove < y)
                    moveWaterUp = true;
            }
            
            //This sleep is equals for all threads
            try {
                sleep(allThreadSleep);
            } catch (InterruptedException ex) {
                JOptionPane.showMessageDialog(null ,"Maybe the game crash because: " + ex.getMessage());
            }
            
        }
        
    }
    
    /*----  Classe methods  ----*/
    private void WaitAllThreadsFinish() {
        //Wait some threads be created for do not have 'NullPointerExeption'
        while (true) {
            if (playing.allThreadsOk) {
                break;
            }
            
            try { //If do not have this 'sleep' the 'while' maybe never will exit
                sleep(100);
            } catch (InterruptedException ex) {
                JOptionPane.showMessageDialog(null, "Maybe the game crash in loading because: " + ex.getMessage());
            }
        }
    }
    
    public void DrawWater() {
        water.draw();
    }
    
}

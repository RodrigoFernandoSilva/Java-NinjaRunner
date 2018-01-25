
package Plataform;

//Jplay imports
import jplay.Animation;
import jplay.GameImage;

//Variables imports
import static Main.DeltaTime.allThreadSleep;
import static Main.DeltaTime.deltaTime;
import static Main.Main.ninja;
import static Main.Main.playing;
import static Main.Main.someMethods;
import static Main.Main.window;

//Others imports
import javax.swing.JOptionPane;
import java.util.Random;

/**
 *
 * @author Rodrigo Fernando da Silva
 */
public class Floor extends Thread {
    
    //Java variables
    private double speed;
    private int firstFloor;
    private int lastFloor;
    private int line;
    private final String FILE_WAY = "Images/Plataform/";
    private Random generator;
    
    //Jplay variables
    Animation[] floorLeft;
    GameImage floorRight;
    
    @Override
    @SuppressWarnings("SleepWhileInLoop")
    public void run() {
        
        generator = new Random();
        
        floorLeft = new Animation[7];
        
        for (line = 0; line < floorLeft.length; line ++)
            floorLeft[line] = new Animation(FILE_WAY + "TiledMapLeft.png");
        
        floorRight = new Animation(FILE_WAY + "TiledMapRight.png");
        
        firstFloor = 0;
        lastFloor = floorLeft.length - 1;
        
        InitializeFloors();
        
        playing.SetFloorOk(true);
        
        WaitAllThreadsFinish();
        
        floorLeft[6].y = 100;
        
        while (playing.GetIsPlaying()) {
            
            speed = (playing.GetSpeed() * 1.3) * playing.GetSubSpeedMax();
            
            //Move the floor
            for (line = 0; line < floorLeft.length; line ++) {
                floorLeft[line].x -= (speed) * deltaTime;
                //Reposition the floor after it exit the window
                if (someMethods.ExitWindow(floorLeft[line], floorRight.width))
                    RepositionFloor(line);
            }
            
            if ((floorLeft[firstFloor].x + floorLeft[firstFloor].width + (floorRight.width * 2)) <
                 ninja.GetSoul().x + ninja.GetSoul().width) {
                firstFloor ++;
                if (firstFloor > floorLeft.length - 1)
                    firstFloor = 0;
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
    
    private void InitializeFloors() {
        for (line = 0; line < floorLeft.length; line ++) {
            floorLeft[line].x = 0;
            floorLeft[line].width = (generator.nextInt(19) + 1) * floorRight.width;
            floorLeft[line].y = window.getHeight() - (generator.nextInt(8) + 1) * floorRight.width;
            if (line != 0)
                floorLeft[line].x = floorLeft[line - 1].x + floorLeft[line - 1].width + (floorRight.width * 2);
            else
                floorLeft[line].width = (generator.nextInt(14) + 6) * floorRight.width;
            floorLeft[line].y = floorLeft[line].height - 192;
        }
    }
    
    private void RepositionFloor(int index) {
        floorLeft[index].x = floorLeft[lastFloor].x + floorLeft[lastFloor].width + (floorRight.width * 2);
        floorLeft[index].width = (generator.nextInt(19) + 1) * floorRight.width;
        lastFloor = index;
    }
    
    public double GetSpeed() {
        return speed;
    }
    
    public void DrawLeftFloor() {
        for (Animation index : floorLeft)
            index.draw();
    }
    
    public void DrawRightFloor() {
        for (Animation index : floorLeft) {
            floorRight.x = index.x + index.width;
            floorRight.y =  index.y;
            floorRight.draw();
        }
    }
    
    public double GetFirstFloorY() {
        return floorLeft[firstFloor].y;
    }
    
}

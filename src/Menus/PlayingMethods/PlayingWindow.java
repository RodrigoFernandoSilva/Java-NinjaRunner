
package Menus.PlayingMethods;

//Jplay import
import jplay.GameImage;

//Variables impors
import static Main.DeltaTime.allThreadSleep;
import static Main.Main.enemy;
import static Main.Main.floor;
import static Main.Main.ninja;
import static Main.Main.playing;
import static Main.Main.someMethods;
import static Main.Main.water;
import static Main.Main.window;

//Others imports
import static java.lang.Thread.sleep;
import javax.swing.JOptionPane;

/**
 * Control the 'y' position of all images.
 * 
 * @author Rodrigo Fernando da Silva
 */
public class PlayingWindow extends Thread {
    
    //Java variables
    /**
     * Windows height
     */
    public double positionY = window.getHeight();
    private int line;
    private final String FILE_WAY = "Images/Playing/Others/";
    
    //Jplay variables
    public GameImage camera;
    
    @Override
    @SuppressWarnings("SleepWhileInLoop")
    public void run() {
        
        positionY = 0;
        
        camera = new GameImage(FILE_WAY + "Camera.png");
        camera.width = window.getWidth();
        camera.height = window.getHeight();
        
        playing.playingWindowOk = true;
        
        someMethods.WaitAllThreadsFinish();
        
        while (playing.GetIsPlaying()) {
            
            //Makes the camera move when the ninja is above of the middle of the window
            if (ninja.soul.y - (ninja.soul.height) < camera.height / 2) {
                positionY = (ninja.soul.y - ninja.soul.height) - (camera.height / 2);
            } else {
                positionY = (ninja.soul.height - camera.height) / 4;
            }
            positionY -= camera.y;
            
            
            //Puts the objects that is on the scene on the camera position
            for (line = 0; line < enemy.length; line ++) {
                enemy[line].spriteSheet[enemy[line].spriteSheetEnable].y = camera.y + enemy[line].y;
            }
            
            for (line = 0; line < floor.floorLeft.length; line ++) {
                floor.floorLeft[line].y = camera.y + floor.y[line];
            }
            
            water.water.y = camera.y + water.y;
            
            for (line = 0; line < ninja.GetNinjaKunai().length; line ++) {
                if (ninja.GetNinjaKunai(line) != null && ninja.GetNinjaKunai(line).getState() == State.TIMED_WAITING) {
                    ninja.GetNinjaKunai(line).SetY(ninja.GetNinjaKunai(line).y + camera.y);
                }
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
    public void DrawCamera() {
        camera.draw();
    }
    
    public void ApplyForceOnCamera(double force) {
        camera.y -= force;
    }
    
}


package Menus.PlayingMethods;

//Jplay import
import jplay.GameImage;

//Variables impors
import static Main.DeltaTime.allThreadSleep;
import static Main.Main.ninja;
import static Main.Main.playing;
import static Main.Main.someMethods;
import static Main.Main.window;

//Others imports
import static java.lang.Thread.sleep;
import javax.swing.JOptionPane;

/**
 *
 * @author Rodrigo Fernando da Silva
 */
public class PlayingWindow extends Thread {
    
    //Java variables
    public double positionY;
    private final String FILE_WAY = "Images/Playing/Others/";
    
    
    //Jplay variables
    private GameImage camera;
    
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
            
           positionY = (ninja.soul.y + ninja.soul.height)- window.getHeight();
           
            if (ninja.soul.y < window.getHeight() / 2) {
                
                //System.out.println(positionY);
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
    
}

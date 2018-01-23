
package Menus.PlayingMethods;

//Variables imports
import static Main.DeltaTime.allThreadSleep;
import static Main.Main.window;
import static Main.Main.ninja;
import static Main.Main.playing;

//Others imports
import static java.lang.Thread.sleep;
import javax.swing.JOptionPane;

/**
 *
 * @author Rodrigo Fernando da Silva
 */
public class PlayingDraw extends Thread {
    
    @Override
    @SuppressWarnings("SleepWhileInLoop")
    public void run() {
        
        while (playing.GetIsPlaying()) {
            
            playing.DrawBackground();
            
            ninja.DrawSould();
            ninja.DrawSpriteSheet();
            
            window.update();
            
            //This sleep is equals for all threads
            try {
                sleep(allThreadSleep);
            } catch (InterruptedException ex) {
                JOptionPane.showMessageDialog(null ,"Maybe the game crash because: " + ex.getMessage());
            }
            
        }
        
    }
    
}

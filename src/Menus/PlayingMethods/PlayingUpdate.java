
package Menus.PlayingMethods;

//Variables imports
import static Main.DeltaTime.allThreadSleep;
import static Main.Main.enemy;
import static Main.Main.ninja;
import static Main.Main.playing;

//Others imports
import static java.lang.Thread.sleep;
import javax.swing.JOptionPane;

/**
 *
 * @author Rodrigo Fernando da Silva
 */
public class PlayingUpdate extends Thread {
    
    @Override
    @SuppressWarnings("SleepWhileInLoop")
    public void run() {
        
        int line; //It is used in 'for'.
        while (playing.GetIsPlaying()) {
            
            if (ninja.GetNinjaAnimation().CanUpdateSprite())
                ninja.UpdateSpriteSheet();
            
            for (line = 0; line < enemy.length; line ++) {
                enemy[line].UpdateSpriteSheet();
            }
            
            //This sleep is equals for all threads
            try {
                sleep(allThreadSleep);
            } catch (InterruptedException ex) {
                JOptionPane.showMessageDialog(null ,"Maybe the game crash because: " + ex.getMessage());
            }
            
        }
        
    }
    
}

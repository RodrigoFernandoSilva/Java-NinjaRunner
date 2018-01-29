
package Characters;

//Variables imports
import static Main.DeltaTime.allThreadSleep;
import static Main.Main.playing;
import static Main.Main.someMethods;

//Others imports
import javax.swing.JOptionPane;
import jplay.Animation;

/**
 *
 * @author Rodrigo Fernando da Silva
 */
public class Enemy extends Characters {
    
    private final int ANIMATION_SPEED = 900;
    private final String FILE_WAY = "Images/Playing/Enemy/";
    
    @Override
    public void run() {
        
        spriteSheet = new Animation[1];
        spriteSheet[0] = new Animation(FILE_WAY + "Enemy.png", 11);
        spriteSheet[0].setSequenceTime(0, 10, true, ANIMATION_SPEED);
        spriteSheetEnable = 0;
        
        y = 0;
        
        playing.enemyOk = true;
        
        someMethods.WaitAllThreadsFinish();
        
        while (playing.GetIsPlaying()) {
            
            
            
            //This sleep is equals for all threads
            try {
                sleep(allThreadSleep);
            } catch (InterruptedException ex) {
                JOptionPane.showMessageDialog(null ,"Maybe the game crash because: " + ex.getMessage());
            }
            
        }
        
    }
    
}

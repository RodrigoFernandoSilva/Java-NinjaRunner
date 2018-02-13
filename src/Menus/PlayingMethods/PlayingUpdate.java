
package Menus.PlayingMethods;

//Variables imports
import static Main.DeltaTime.allThreadSleep;
import static Main.Main.coinThread;
import static Main.Main.enemy;
import static Main.Main.ninja;
import static Main.Main.playing;
import static Main.Main.someMethods;

//Others imports
import static java.lang.Thread.sleep;
import javax.swing.JOptionPane;

/**
 *
 * @author Rodrigo Fernando da Silva
 */
public class PlayingUpdate extends Thread {
    
    //Java variables
    public boolean itOver = false;
    
    @Override
    @SuppressWarnings("SleepWhileInLoop")
    public void run() {
        
        int line; //It is used in 'for'.
        while (playing.GetIsPlaying()) {
            
            someMethods.PauseTheGame();
            
            if (ninja.GetNinjaAnimation().CanUpdateSprite())
                ninja.UpdateSpriteSheet();
            
            for (line = 0; line < enemy.length; line ++) {
                if (enemy[line] != null && enemy[line].isOk) {
                    enemy[line].UpdateSpriteSheet();
                }
            }
            
            for (line = 0; line < coinThread.length; line ++) {
                if (coinThread[line] != null) {
                    coinThread[line].UpdadteCoin();
                }
            }
            
            //This sleep is equals for all threads
            try {
                sleep(allThreadSleep);
            } catch (InterruptedException ex) {
                JOptionPane.showMessageDialog(null ,"Maybe the game crash because: " + ex.getMessage());
            }
            
        }
        
        itOver = true;
        if (!playing.GetIsPlaying()) {
            someMethods.WaitSomeThreadOver();
        }
        
    }
    
}

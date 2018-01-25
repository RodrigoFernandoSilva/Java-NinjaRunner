
package Menus.PlayingMethods;

//Variables imports
import static Main.DeltaTime.allThreadSleep;
import static Main.Main.window;
import static Main.Main.ninja;
import static Main.Main.playing;
import static Main.Main.floor;
import static Main.Main.water;

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
            
            water.DrawWater();
            
            floor.DrawLeftFloor();
            floor.DrawRightFloor();
            
            ninja.DrawSould();
            //Only draw this sprite if the keyboard was not pressed, because after press it the
            //sprite animation is changed and its position is reseted
            if (!ninja.GetNinjaKeyboard().changeAnimation)
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

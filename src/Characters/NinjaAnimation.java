
package Characters;

//Variables imports
import static Main.DeltaTime.allThreadSleep;
import static Main.Main.ninja;
import static Main.Main.playing;

//Others imports
import static java.lang.Thread.sleep;
import javax.swing.JOptionPane;

/**
 *
 * @author Rodrigo Fernando da Silva
 */
public class NinjaAnimation extends Thread {
    
    //Java variables
    public boolean isRunning = false;
    public boolean isRunThrow = false;
    public boolean isRunAttack = true;
    public boolean isJumping = false;
    public boolean isJumpThrow = false;
    public boolean isJumpAttack = false;
    public boolean isSliding = false;
    public boolean isGliding = false;
    
    @Override
    @SuppressWarnings("SleepWhileInLoop")
    public void run() {
        
        ninja.spriteSheetEnable = -1;
        
        while (playing.GetIsPlaying()) {
            
            SetCorrectAnimation();
            
            //Set the animation speed to sprite sheet that is enable
            ninja.animationSpeed = (int) (playing.GetSpeed() * 40);
            ninja.spriteSheet[ninja.spriteSheetEnable].setTotalDuration(ninja.animationSpeed);
            
            //This sleep is equals for all threads
            try {
                sleep(allThreadSleep);
            } catch (InterruptedException ex) {
                JOptionPane.showMessageDialog(null ,"Maybe the game crash because: " + ex.getMessage());
            }
            
        }
    }
    
    private void SetCorrectAnimation() {
        if (isRunning && ninja.spriteSheetEnable != 0) {
            ninja.spriteSheetEnable = 0;
            ninja.spriteSheet[ninja.spriteSheetEnable].setSequence(0, 10, true);
        } else if (isRunThrow && ninja.spriteSheetEnable != 0) {
            ninja.spriteSheetEnable = 0;
            ninja.spriteSheet[ninja.spriteSheetEnable].setSequence(10, 20, true);
        }  else if (isRunAttack&& ninja.spriteSheetEnable != 0) {
            ninja.spriteSheetEnable = 0;
            ninja.spriteSheet[ninja.spriteSheetEnable].setSequence(20, 30, true);
        } else if (isJumping && ninja.spriteSheetEnable != 1) {
            ninja.spriteSheetEnable = 1;
            ninja.spriteSheet[ninja.spriteSheetEnable].setSequence(0, 10, true);
        } else if (isJumpThrow && ninja.spriteSheetEnable != 1) {
            ninja.spriteSheetEnable = 1;
            ninja.spriteSheet[ninja.spriteSheetEnable].setSequence(10, 20, true);
        } else if (isJumpAttack&& ninja.spriteSheetEnable != 1) {
            ninja.spriteSheetEnable = 1;
            ninja.spriteSheet[ninja.spriteSheetEnable].setSequence(20, 30, true);
        } else if (isSliding && ninja.spriteSheetEnable != 2) {
            ninja.spriteSheetEnable = 2;
            ninja.spriteSheet[ninja.spriteSheetEnable].setSequence(0, 10, true);
        } else if (isGliding && ninja.spriteSheetEnable != 2) {
            ninja.spriteSheetEnable = 2;
            ninja.spriteSheet[ninja.spriteSheetEnable].setSequence(10, 20, true);
        }
    }
    
}

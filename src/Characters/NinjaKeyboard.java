
package Characters;

//Variables imports
import static Main.DeltaTime.allThreadSleep;
import static Main.Main.keyboard;
import static Main.Main.ninja;
import static Main.Main.playing;

//Ohters imports
import java.awt.event.KeyEvent;
import static java.lang.Thread.sleep;
import javax.swing.JOptionPane;

/**
 *
 * @author Rodrigo Fernando da Silva
 */
public class NinjaKeyboard extends Thread{
    
    //Java variables
    public boolean isRunning = true;
    public boolean isRunThrow = false;
    public boolean isRunAttack = false;
    public boolean isJumping = false;
    public boolean isJumpThrow = false;
    public boolean isJumpAttack = false;
    public boolean isSliding = false;
    public boolean isGliding = false;
    
    public boolean animationTransition = false;
    public boolean changeAnimation = false;

    @Override
    @SuppressWarnings("SleepWhileInLoop")
    public void run() {
        
        ninja.SetNinjaKeyboardOk(true);
        
        while (playing.GetIsPlaying()) {
            
            if (keyboard.keyDown(KeyEvent.VK_UP)) {
                //If the ninja jump during floor attack, the animation need be change to jump attack,
                //but in the same frame
                if (!changeAnimation & !animationTransition & (isRunAttack | isRunThrow)) {
                    animationTransition = true;
                    ninja.soul.jumpWithoutPress();
                } else if (isRunning | isRunAttack | isRunThrow) { //The ninja jumped but was not attacking
                    SetFalseBoolean();
                    isJumping = true;
                    changeAnimation = true;
                    ninja.soul.jumpWithoutPress();
                }
            } else if (keyboard.keyDown(KeyEvent.VK_DOWN)) {
                if (!changeAnimation & isRunning) {
                    SetFalseBoolean();
                    isSliding = true;
                    changeAnimation = true;
                }
            } else if (keyboard.keyDown(KeyEvent.VK_SPACE)) {
                if (!changeAnimation & isJumping) {
                    SetFalseBoolean();
                    isGliding = true;
                    changeAnimation = true;
                }
            } else if (keyboard.keyDown(KeyEvent.VK_X)) {
                if (!changeAnimation & isRunning) {
                    SetFalseBoolean();
                    isRunThrow = true;
                    changeAnimation = true;
                } else if (!changeAnimation & isJumping) {
                    SetFalseBoolean();
                    isJumpThrow = true;
                    changeAnimation = true;
                }
            } else if (keyboard.keyDown(KeyEvent.VK_Z)) {
                if (!changeAnimation & isRunning) {
                    SetFalseBoolean();
                    isRunAttack = true;
                    changeAnimation = true;
                } else if (!changeAnimation & isJumping) {
                    SetFalseBoolean();
                    isJumpAttack = true;
                    changeAnimation = true;
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
    public void SetFalseBoolean() {
        isRunning = false;
        isRunThrow = false;
        isRunAttack = false;
        isJumping = false;
        isJumpThrow = false;
        isJumpAttack = false;
        isSliding = false;
        isGliding = false;
    }
    
}

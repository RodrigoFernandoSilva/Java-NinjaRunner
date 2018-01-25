
package Characters;

//Jplay imports
import jplay.Sprite;
import jplay.Animation;

//Variables imports
import static Main.DeltaTime.allThreadSleep;
import static Main.Main.floor;
import static Main.Main.playing;

//Others imports
import javax.swing.JOptionPane;

/**
 *
 * @author Rodrigo Fernando da Silva
 */
public class Ninja extends Characters {
    
    //Java variables
    public boolean ninjaAnimationOk;
    public boolean ninjaKeyboardOk;
    private final String FILE_WAY = "Images/Playing/Ninja/";
    public int line;
    
    //Classe variables
    private NinjaAnimation ninjaAnimation;
    private NinjaKeyboard ninjaKeyboard;
    
    @Override
    public void run() {
        
        ninjaAnimationOk = false;
        ninjaKeyboardOk = false;
        
        soul = new Sprite(FILE_WAY + "soul.png");
        spriteSheet = new Animation[3];
        spriteSheet[0] = new Animation(FILE_WAY + "SpriteSheet1.png", 31);
        spriteSheet[1] = new Animation(FILE_WAY + "SpriteSheet2.png", 31);
        spriteSheet[2] = new Animation(FILE_WAY + "SpriteSheet3.png", 21);
        
        spriteAdjustX = new int[spriteSheet.length][3];
        spriteAdjustY = new int[spriteSheet.length][3];
        
        //Start all ninja threads
        ninjaAnimation = new NinjaAnimation();
        ninjaAnimation.start();
        
        InitializeSpriteAdjust();
        
        soul.x = 100;
        soul.y = 100;
        
        //Wait some threads be created for do not have 'NullPointerExeption'
        while (true) {
            if (ninjaAnimationOk & ninjaKeyboardOk) {
                break;
            }
            
            try { //If do not have this 'sleep' the 'while' maybe never will exit
                sleep(100);
            } catch (InterruptedException ex) {
                JOptionPane.showMessageDialog(null, "Maybe the game crash in loading because: " + ex.getMessage());
            }
        }
        
        soul.y = (floor.GetFirstFloorY() - soul.height);
        
        playing.SetNinjaOk(true);
        
        WaitAllThreadsFinish();
        
        while (playing.GetIsPlaying()) {
            
            //Set the floor to sprite and makes it jump
            soul.setFloor((int) (floor.GetFirstFloorY()));
            soul.jumpApplyForce();
            soul.fall();
            
            if (!ninjaKeyboard.changeAnimation) {
                spriteSheet[spriteSheetEnable].x = soul.x + spriteAdjustX[spriteSheetEnable][spriteSheet[spriteSheetEnable].getInitialFrame() / 10];
                spriteSheet[spriteSheetEnable].y = soul.y + spriteAdjustY[spriteSheetEnable][spriteSheet[spriteSheetEnable].getInitialFrame() / 10];
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
    
    private void InitializeSpriteAdjust() {
        //Running
        spriteAdjustX[0][0] = -45; //x
        spriteAdjustY[0][0] = -30; //y
        //Running tattack
        spriteAdjustX[0][1] = -45;
        spriteAdjustY[0][1] = -40;
        //Running hrow
        spriteAdjustX[0][2] = -20;
        spriteAdjustY[0][2] = -30;
        //Jumping
        spriteAdjustX[1][0] = -45;
        spriteAdjustY[1][0] = -30;
        //Jumping attack
        spriteAdjustX[1][1] = -25;
        spriteAdjustY[1][1] = -23;
        //Jumping throw
        spriteAdjustX[1][2] = -47;
        spriteAdjustY[1][2] = -35;
        //Slide
        spriteAdjustX[2][0] = -45;
        spriteAdjustY[2][0] = -13;
        //Glide
        spriteAdjustX[2][1] = -40;
        spriteAdjustY[2][1] = -30;
    }
    
    public void SetNinjaAnimationOk(boolean value) {
        ninjaAnimationOk = value;
    }
    
    public void SetNinjaKeyboardOk(boolean value) {
        ninjaKeyboardOk = value;
    }
    
    public NinjaAnimation GetNinjaAnimation() {
        return ninjaAnimation;
    }
    
    public NinjaKeyboard GetNinjaKeyboard() {
        return ninjaKeyboard;
    }
    
    public void StartNinjaKeyboard() {
        ninjaKeyboard = new NinjaKeyboard();
        ninjaKeyboard.start();
    }
    
}

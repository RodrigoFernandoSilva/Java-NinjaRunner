
package Characters;

//Jplay imports
import jplay.GameImage;
import jplay.Animation;

//Variables imports
import static Main.DeltaTime.allThreadSleep;
import static Main.Main.playing;

//Others imports
import javax.swing.JOptionPane;

/**
 *
 * @author Rodrigo Fernando da Silva
 */
public class Ninja extends Characters {
    
    public final String FILE_WAY = "Images/Playing/Ninja/";
    
    public int line;
    
    public boolean ninjaOk;
    
    public Ninja() {
        ninjaOk = false;
    }
    
    @Override
    public void run() {
        
        soul = new GameImage(FILE_WAY + "soul.png");
        spriteSheet = new Animation[3];
        spriteSheet[0] = new Animation(FILE_WAY + "SpriteSheet1.png", 31);
        spriteSheet[1] = new Animation(FILE_WAY + "SpriteSheet2.png", 31);
        spriteSheet[2] = new Animation(FILE_WAY + "SpriteSheet3.png", 21);
        
        spriteAdjustX = new int[spriteSheet.length][3];
        spriteAdjustY = new int[spriteSheet.length][3];
        
        //Start all ninja threads
        NinjaAnimation ninjaAnimation = new NinjaAnimation();
        ninjaAnimation.start();
        
        InitializeSpriteAdjust();
        
        soul.x = 100;
        soul.y = 100;
        
        ninjaOk = true;
        
        while (playing.GetIsPlaying()) {
            
            spriteSheet[spriteSheetEnable].x = soul.x + spriteAdjustX[spriteSheetEnable][spriteSheet[spriteSheetEnable].getInitialFrame() / 10];
            spriteSheet[spriteSheetEnable].y = soul.y + spriteAdjustY[spriteSheetEnable][(spriteSheet[spriteSheetEnable].getInitialFrame() / 10)];
            
            //This sleep is equals for all threads
            try {
                sleep(allThreadSleep);
            } catch (InterruptedException ex) {
                JOptionPane.showMessageDialog(null ,"Maybe the game crash because: " + ex.getMessage());
            }
            
        }
        
    }
    
    /*----  Classe methods  ----*/
    private void InitializeSpriteAdjust() {
        spriteAdjustX[0][0] = -45;
        spriteAdjustY[0][0] = -30;
        spriteAdjustX[0][1] = -45;
        spriteAdjustY[0][1] = -40;
        spriteAdjustX[0][2] = -20;
        spriteAdjustY[0][2] = -30;
    }
    
    public boolean getNinjaOk() {
        return ninjaOk;
    }
    
}

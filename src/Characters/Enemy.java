
package Characters;

//Variables imports
import static Main.DeltaTime.allThreadSleep;
import static Main.Main.floor;
import static Main.Main.ninja;
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
    private int father = -1;
    private final int myId;
    public boolean isOk = false;
    public boolean wasPutOnPosition = false;
    private final String FILE_WAY = "Images/Playing/Enemy/";
    
    public Enemy(int father, double x, double y, int myId) {
        this.father = father;
        this.x = x;
        this.y = y;
        this.myId = myId;
    }
    
    @Override
    public void run() {
        
        spriteSheet = new Animation[2];
        spriteSheet[0] = new Animation(FILE_WAY + "Enemy.png", 11);
        spriteSheet[1] = new Animation("Images/Playing/Others/Explosion.png", 12);
        spriteSheet[0].setSequenceTime(0, 10, true, ANIMATION_SPEED);
        spriteSheet[1].setSequenceTime(0, 11, true, ANIMATION_SPEED);
        spriteSheetEnable = 0;
        
        spriteAdjustX = new double[spriteSheet.length][1];
        spriteAdjustY = new double[spriteSheet.length][1];
        
        spriteSheet[spriteSheetEnable].x = -100;
        y -= spriteSheet[spriteSheetEnable].height - 3;
        
        InitializeSpriteAdjust();
        
        playing.enemyOk = true;
        
        someMethods.WaitAllThreadsFinish();
        
        isOk = true;
        while (playing.GetIsPlaying()) {
            
            spriteSheet[spriteSheetEnable].x = floor.floorLeft[father].x + x;
            
            if (someMethods.ExitWindow(spriteSheet[spriteSheetEnable], 0)) {
                break;
            } else if (spriteSheetEnable == 1 && spriteSheet[spriteSheetEnable].getCurrFrame() >= (spriteSheet[spriteSheetEnable].getFinalFrame() - 1)) {
                break;
            } else if (spriteSheetEnable == 0 && ninja.GetNinjaKeyboard().isRunAttack || ninja.GetNinjaKeyboard().isJumpAttack) {
                if (ninja.spriteSheet[ninja.spriteSheetEnable].getCurrFrame() > 8 && spriteSheet[spriteSheetEnable].collided(ninja.spriteSheet[ninja.spriteSheetEnable])) {
                    spriteSheetEnable = 1;
                    x -= spriteSheet[spriteSheetEnable].width / 2;
                    wasPutOnPosition = false;
                }
            }
            
            if (!wasPutOnPosition) {
                wasPutOnPosition = true;
            }
            
            //This sleep is equals for all threads
            try {
                sleep(allThreadSleep);
            } catch (InterruptedException ex) {
                JOptionPane.showMessageDialog(null ,"Maybe the game crash because: " + ex.getMessage());
            }
            
        }
        
        floor.SetNullEnemyThread(myId);
        
    }
    
    /*----  Classe methods  ----*/
    private void InitializeSpriteAdjust() {
        spriteAdjustX[0][0] = 0;
        spriteAdjustX[1][0] = -160;
        spriteAdjustY[0][0] = 0;
        spriteAdjustY[1][0] = (spriteSheet[1].height * 0.35);
    }
    
}

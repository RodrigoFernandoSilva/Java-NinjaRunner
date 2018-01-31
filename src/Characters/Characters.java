
package Characters;

//Jplay imports
import jplay.Animation;
import jplay.Sprite;

//Variables imports
import static Main.Main.someMethods;
import static Main.Main.window;

/**
 *
 * @author Rodrigo Fernando da Silva
 */
public class Characters extends Thread {
    
    //Java variables
    public int spriteSheetEnable; //It for known which sprite sheet is going to be draw and update
    public int animationSpeed;
    public int[][] spriteAdjustX;
    public int[][] spriteAdjustY;
    public double x;
    public double y;
    
    //Jplay variables
    public Animation[] spriteSheet;
    public Sprite soul;
    
    /*----  Classe Methods  ----*/
    public void DrawSould() {
        if (someMethods.IsOnScene(soul, window)) {
            soul.draw();
        }
    }
    
    public void DrawSpriteSheet() {
        if (someMethods.IsOnScene(spriteSheet[spriteSheetEnable], window)) {
            spriteSheet[spriteSheetEnable].draw();
        }
    }
    
    public void UpdateSpriteSheet() {
        spriteSheet[spriteSheetEnable].update();
    }
    
    public Sprite GetSoul() {
        return soul;
    }
    
    /**
     * Returns if the frame that was passed is higher than the correct frame that the sprite is
     * playing.
     * 
     * @param frame
     * @return 
     */
    public boolean FrameHigherThan(int frame) {
        return (spriteSheet[spriteSheetEnable].getCurrFrame() > frame);
    }
}

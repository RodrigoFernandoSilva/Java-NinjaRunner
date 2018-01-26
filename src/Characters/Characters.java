
package Characters;

//Jplay imports
import jplay.Animation;
import jplay.Sprite;

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
    public double y;
    
    //Jplay variables
    public Animation[] spriteSheet;
    public Sprite soul;
    
    /*----  Classe Methods  ----*/
    public void DrawSould() {
        soul.draw();
    }
    
    public void DrawSpriteSheet() {
        spriteSheet[spriteSheetEnable].draw();
    }
    
    public void UpdateSpriteSheet() {
        spriteSheet[spriteSheetEnable].update();
    }
    
    public Sprite GetSoul() {
        return soul;
    }
}

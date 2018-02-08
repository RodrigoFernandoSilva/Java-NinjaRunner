
package Characters;

//Jplay imports
import jplay.Animation;
import jplay.GameImage;

//Variables imports
import static Main.DeltaTime.allThreadSleep;
import static Main.DeltaTime.deltaTime;
import static Main.Main.enemy;
import static Main.Main.floor;
import static Main.Main.ninja;
import static Main.Main.playing;
import static Main.Main.someMethods;
import static Main.Main.window;

//Others imports
import javax.swing.JOptionPane;

/**
 *
 * @author Rodrigo Fernando da Silva
 */
public class NinjaKunai extends Thread {
    
    //Java variables
    private boolean canDraw = false;
    private boolean isAlive = true;
    private final double SPEED = 250;
    public double y;
    private final String FILE_WAY = "Images/Playing/Ninja/";
    
    //Jplay variables
    private final GameImage kunai = new GameImage(FILE_WAY + "KunaiThrow.png");;
    
    @Override
    public void run() {
        
        int line;
        
        //Waits the frame of the ninja be the frame that the kunais is leave his hand.
        while (true) {
            if (ninja.FrameHigherThan(12)) {
                break;
            }
            
            try { //If do not have this 'sleep' the 'while' maybe never will exit
                sleep(100);
            } catch (InterruptedException ex) {
                JOptionPane.showMessageDialog(null, "Maybe the game crash in loading because: " + ex.getMessage());
            }
        }
        
        canDraw = true;
        
        while (playing.GetIsPlaying()) {
            
            kunai.x += (playing.GetSpeed() + SPEED) * deltaTime;
            
            for (line = 0; line < enemy.length; line++) {
                if (enemy[line] != null && enemy[line].isOk && enemy[line].spriteSheetEnable == 0 && kunai.collided(enemy[line].GetSpriteSheetEnable())) {
                    enemy[line].spriteSheetEnable = 1;
                    enemy[line].x -= enemy[line].spriteSheet[1].width / 2;
                    enemy[line].wasPutOnPosition = false;
                    isAlive = false;
                    ninja.enemy++;
                    break;
                }
            }
            
            if (kunai.x > window.getWidth() + window.getWidth() / 2 || floor.Hit(kunai)) {
                break;
            }
            if (!isAlive) {
                break;
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
    public void SetInitialPosition(double x, double y) {
        kunai.x = x;
        this.y = y;
    }
    
    public void SetY(double value) {
        kunai.y = value;
    }
    
    public void DrawKunai() {
        if (someMethods.IsOnScene(kunai, window) && canDraw)
            kunai.draw();
    }
    
}

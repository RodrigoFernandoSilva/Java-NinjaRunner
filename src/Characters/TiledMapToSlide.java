
package Characters;

//Jplay imports
import jplay.GameImage;

//Variables imports
import static Main.DeltaTime.allThreadSleep;
import static Main.Main.floor;
import static Main.Main.playing;
import static Main.Main.someMethods;

//Others imports
import static java.lang.Thread.sleep;
import javax.swing.JOptionPane;

/**
 *
 * @author Rodrigo Fernando da Silva
 */
public class TiledMapToSlide extends Thread {
    
    //Java variables
    public boolean isOk = false;
    public boolean itOver = false;
    public int fahter;
    private final int MY_ID;
    public double x;
    private final String FILE_WAY = "Images/Playing/Plataform/";
    
    //Kplay variables
    private final GameImage floorDarkSky = new GameImage(FILE_WAY + "DarkSky/TiledMapUp.png");
    private final GameImage floorSky = new GameImage(FILE_WAY + "Sky/TiledMapUp.png");
    
    public TiledMapToSlide(int father, double x, int myId) {
        this.fahter = father;
        this.x = x;
        this.MY_ID = myId;
    }
    
    @Override
    public void run() {
        
        someMethods.WaitAllThreadsFinish();
        
        isOk = true;
        
        while (playing.GetIsPlaying()) {
            
            floorSky.x = floor.floorLeft[fahter].x + x;
            floorSky.y = floor.floorLeft[fahter].y - floorSky.height - 90;
            floorDarkSky .x = floorSky.x;
            floorDarkSky .y = floorSky.y;
            
            if (floorSky.x + floorSky.width < 0) {
                break;
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
        
        floor.SetNullTiledMapToSlide(MY_ID);
        
    }
    
    /*----  Classe methods  ----*/
    public void DrawFloorToSlide() {
        if (playing.day) {
            floorSky.draw();
        } else {
            floorDarkSky.draw();
        }
    }
    
    public GameImage GetFloorSky() {
        return floorSky;
    }
    
}

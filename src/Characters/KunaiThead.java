
package Characters;

//Jplay imports
import jplay.GameImage;
import jplay.Sound;

//Variables imports
import static Main.DeltaTime.allThreadSleep;
import static Main.Main.floor;
import static Main.Main.ninja;
import static Main.Main.playing;
import static Main.Main.someMethods;

//Others imports
import static java.lang.Thread.sleep;
import javax.swing.JOptionPane;

/**
 *
 * @author Rodrigo Fernando da Silva
 */
public class KunaiThead extends Thread {
    
    //Java variables
    public boolean isOk = false;
    public boolean itOver = false;
    public int fahter;
    private final int MY_ID;
    public double x;
    public double y;
    private final String FILE_WAY = "Images/Playing/Ninja/";
    private final String SOUND_FILE = "Sounds/Ninja/Kunai_Take.wav";
    
    //Jplay variables
    public final GameImage COIN = new GameImage(FILE_WAY + "KunaiTake.png");
    private Sound kunaiSound;
    
    public KunaiThead(int father, double x , double y, int myId) {
        this.fahter = father;
        this.x = x;
        this.y = y;
        this.MY_ID = myId;
    }
    
    @Override
    public void run() {
        
        y -= COIN.height + 18;
        
        someMethods.WaitAllThreadsFinish();
        
        isOk = true;
        
        while (playing.GetIsPlaying()) {
            
            COIN.x = floor.floorLeft[fahter].x + x;
            
            if (COIN.collided(ninja.soul)) {
                ninja.kunai++;
                kunaiSound = new Sound(SOUND_FILE);
                kunaiSound.play();
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
        
        floor.SetNullKunaiThread(MY_ID);
        
    }
    
    /*----  Classe methods  ----*/
    public void DrawKunai() {
        COIN.draw();
    }
    
}

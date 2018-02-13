
package Characters;

//Jplay imports
import jplay.Animation;

//Variables imports
import static Main.DeltaTime.allThreadSleep;
import static Main.Main.floor;
import static Main.Main.ninja;
import static Main.Main.playing;
import static Main.Main.someMethods;

//Others imports
import static java.lang.Thread.sleep;
import java.util.Random;
import javax.swing.JOptionPane;

/**
 *
 * @author Rodrigo Fernando da Silva
 */
public class CoinThread extends Thread {
    
    //Java variables
    public boolean isOk = false;
    public boolean itOver = false;
    public int fahter;
    private final int MY_ID;
    private int timeUpdate = 0;
    private int timeUpdateFinal = 100;
    public double x;
    public double y;
    private final String FILE_WAY = "Images/Playing/Ninja/";
    
    //Kplay variables
    public final Animation COIN = new Animation(FILE_WAY + "Coin.png", 11);
    
    public CoinThread(int father, double x , double y, int myId) {
        this.fahter = father;
        this.x = x;
        this.y = y;
        this.MY_ID = myId;
    }
    
    @Override
    public void run() {
        
        timeUpdateFinal = CreatUpdateTime();
        
        COIN.setSequenceTime(0, 10, true, 500);
        
        y -= COIN.height + 18;
        
        someMethods.WaitAllThreadsFinish();
        
        isOk = true;
        
        while (playing.GetIsPlaying()) {
            
            COIN.x = floor.floorLeft[fahter].x + x;
            
            if (COIN.collided(ninja.soul)) {
                ninja.coin++;
                break;
            }
            
            //This sleep is equals for all threads
            try {
                sleep(allThreadSleep);
            } catch (InterruptedException ex) {
                JOptionPane.showMessageDialog(null ,"Maybe the game crash because: " + ex.getMessage());
            }
            
        }
        
        isOk = false;
        itOver = true;
        if (!playing.GetIsPlaying()) {
            someMethods.WaitSomeThreadOver();
        }
        
        floor.SetNullCoinThread(MY_ID);
        
    }
    
    /*----  Classe methods  ----*/
    private int CreatUpdateTime() {
        Random generator = new Random();
        
        return generator.nextInt(50) + 50;
    }
    
    public void DrawCoin() {
        COIN.draw();
    }
    
    public void UpdadteCoin() {
        if (timeUpdate > timeUpdateFinal && isOk) {
            COIN.update();
            if (COIN.getCurrFrame() >= (COIN.getFinalFrame() - 1)) {
                timeUpdate = 0;
                COIN.setCurrFrame(0);
                timeUpdateFinal = CreatUpdateTime();
            }
        } else {
            timeUpdate++;
        }
    }
    
}


package Menus.PlayingMethods;

//Jplay imports
import jplay.GameImage;

//Variables imports
import static Main.DeltaTime.allThreadSleep;
import static Main.Main.coinThread;
import static Main.Main.enemy;
import static Main.Main.floor;
import static Main.Main.kunaiThead;
import static Main.Main.ninja;
import static Main.Main.playing;
import static Main.Main.someMethods;
import static Main.Main.water;
import static Main.Main.window;

//Others imports
import static java.lang.Thread.sleep;
import javax.swing.JOptionPane;

/**
 *
 * @author Rodrigo Fernando da Silva
 */
public class PlayingDraw extends Thread {
    
    //Java variables
    public boolean itOver = false;
    
    //Jplay variables
    private final GameImage PAUSE = new GameImage("Images/Playing/Others/Pause.png");
    
    @Override
    @SuppressWarnings("SleepWhileInLoop")
    public void run() {
        
        PAUSE.width = window.getWidth();
        PAUSE.height = window.getHeight();
        
        int line; //It is used in 'for'.
        while (playing.GetIsPlaying()) {
            
            playing.DrawBackground();
            
            water.DrawWater();
            
            floor.DrawObjects();
            
            for (line = 0; line < coinThread.length; line ++) {
                if (coinThread[line] != null) {
                    coinThread[line].DrawCoin();
                }
            }
            
            for (line = 0; line < kunaiThead.length; line ++) {
                if (kunaiThead[line] != null) {
                    kunaiThead[line].DrawKunai();
                }
            }
            
            ninja.DrawKunai();
            
            floor.DrawLeftFloor();
            floor.DrawRightFloor();
            
            ninja.DrawSould();
            //Only draw this sprite if the keyboard was not pressed, because after press it the
            //sprite animation is changed and its position is reseted
            if (!ninja.GetNinjaKeyboard().changeAnimation)
                ninja.DrawSpriteSheet();
            
            //playing.DrawPlayingWindow();
            
            for (line = 0; line < enemy.length; line ++) {
                if (enemy[line] != null && enemy[line].isOk && enemy[line].wasPutOnPosition) {
                    enemy[line].DrawSpriteSheet();
                }
            }
            
            playing.DrawHUD();
            
            someMethods.DrawNumbersOnMiddle(ninja.coin, playing.GetCoinHUD(), 16.5f, 0);
            someMethods.DrawNumbersOnMiddle(ninja.enemy, playing.GetEnemyHUD(), 16.5f, 0);
            someMethods.DrawNumbersOnMiddle(ninja.kunai, playing.GetKunaiHUD(), 16.5f, 0);
            someMethods.DrawNumbersOnMiddle(ninja.meters, playing.GetMetersHUD(), 16.5f, 0);
            
            if (playing.isPaused) {
                PAUSE.draw();
            }
            
            window.update();
            
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
        
    }
    
}

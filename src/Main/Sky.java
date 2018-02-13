
package Main;

//Variables imports
import static Main.DeltaTime.allThreadSleep;
import static Main.Main.playing;
import static Main.Main.someMethods;

//Others imports
import javax.swing.JOptionPane;

/**
 *
 * @author Rudrigo Fernando da Silva
 */
public class Sky extends Thread {
    
    @Override
    public void run() {
        
        int line;
        int timeChangeSky = 0;
        final int TIME_CHANGE_SKY = 1500;
        
        playing.skyOk = true;
        
        someMethods.WaitAllThreadsFinish();
        
        while (playing.GetIsPlaying()) {
            
            if (timeChangeSky > TIME_CHANGE_SKY) {
                playing.day = !playing.day;
                timeChangeSky = 0;
                
                if (playing.day) {
                    for (line = 0; line < playing.backgroundSky.length; line ++) {
                        playing.backgroundSky[line].setSequence(0, 4);
                        playing.backgroundDarkSky[line].setSequence(4, 8);
                        playing.backgroundDarkSky[line].setTotalDuration(playing.backgroundSky[line].getTotalDuration());
                    }
                } else {
                    for (line = 0; line < playing.backgroundSky.length; line ++) {
                        playing.backgroundSky[line].setSequence(4, 8);
                        playing.backgroundDarkSky[line].setSequence(0, 4);
                        playing.backgroundDarkSky[line].setTotalDuration(playing.backgroundSky[line].getTotalDuration());
                    }
                }
            }
            timeChangeSky ++;
            
            playing.UpdateBackground();
            
            //This sleep is equals for all threads
            try {
                sleep(allThreadSleep);
            } catch (InterruptedException ex) {
                JOptionPane.showMessageDialog(null ,"Maybe the game crash because: " + ex.getMessage());
            }
            
        }
        
    }
    
}

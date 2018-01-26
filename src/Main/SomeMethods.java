
package Main;

//Jplay imports
import jplay.Animation;
import jplay.GameImage;
import jplay.Window;

//Variables impors
import static Main.Main.playing;

//Others imports
import static java.lang.Thread.sleep;
import javax.swing.JOptionPane;

/**
 *
 * @author Rodrigo Fernando da Silva
 */
public class SomeMethods {
    
    public boolean ExitWindow(Animation image, double addition) {
        return (image.x + image.width + addition) < 0;
    }
            
    public double SetPositionBelowWindow(Animation image, Window window) {
        return window.getHeight() - image.height;
    }
    
    public double SetPositionBelowWindow(GameImage image, Window window) {
        return window.getHeight() - image.height;
    }
    
    /**
     * Distributes the 'GameImages' on the middle of 'Animation'. 
     * 
     * @param imageToPut
     * @param imageBase
     * @param additionLenght
     * @param numberObject
     * @param part
     * @return double
     */
    public double PutImageOnMiddle (GameImage imageToPut, Animation imageBase, int additionLenght, int numberObject, int part) {
        double value;
        double lenght = ((imageBase.width + additionLenght) / numberObject);
        
        value = imageBase.x + (lenght / 2);
        value += lenght * part;
        value -= (imageToPut.width / 2);
        
        return value;
    }
    
    public void WaitAllThreadsFinish() {
        //Wait some threads be created for do not have 'NullPointerExeption'
        while (true) {
            if (playing.allThreadsOk) {
                break;
            }
            
            try { //If do not have this 'sleep' the 'while' maybe never will exit
                sleep(100);
            } catch (InterruptedException ex) {
                JOptionPane.showMessageDialog(null, "Maybe the game crash in loading because: " + ex.getMessage());
            }
        }
    }
}

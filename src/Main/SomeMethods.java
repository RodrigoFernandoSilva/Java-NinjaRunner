
package Main;

//Jplay imports
import jplay.Animation;
import jplay.GameImage;
import jplay.Window;

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
    
}

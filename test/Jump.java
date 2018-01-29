
import static java.lang.Thread.sleep;
import jplay.Window;
import jplay.Keyboard;
import jplay.GameImage;

/**
 *
 * @author Rodrigo Fernando da Silva
 */
public class Jump {
    
    //Java variables
    private static boolean isJumping = false;
    private static double ninjaFloor;
    private static final double GRAVITY = 0.098;
    private static final double JUMP_FORCE = 5.3;
    private static double velocityY;
    private static double y;
    
    //Jplay variables
    private static Window window;
    private static Keyboard keyboard;
    private static GameImage camera;
    
    public static void jump() {
        if (keyboard.keyDown(Keyboard.UP_KEY)) {
            if (!isJumping) {
                velocityY = -JUMP_FORCE;
                isJumping = true;
            }
        }
        
        if (isJumping) {
            velocityY += GRAVITY;
            
            if (y - camera.y > camera.height / 2) {
                camera.y = 0;
                y += velocityY;
            } else {
                y = camera.height / 2;
                PassForce(velocityY);
            }
        }
        
        if (y > ninjaFloor) {
            isJumping = false;
            y = ninjaFloor;
        }
    }
    
    private static void PassForce(double force) {
        camera.y -= force;
    }
    
    public static void main(String args[]) throws InterruptedException {
        
        window = new Window(800, 600);
        keyboard = window.getKeyboard();
        
        GameImage background = new GameImage("Images/Playing/Background/Background4.png");
        GameImage floor = new GameImage("Images/Plataform/TiledMapLeft.png");
        floor.x -= 64;
        floor.y = window.getHeight() - 128;
        GameImage ninja = new GameImage("Images/Playing/Ninja/Soul.png");
        ninja.x = (window.getWidth() - ninja.width) / 2;
        y = floor.y - ninja.height;
        camera = new GameImage("Images/Playing/Others/Camera.png");
        camera.width = window.getWidth();
        camera.height = window.getHeight();
        
        ninjaFloor = y;
        
        while (true) {
            
            ninja.y = y;
            floor.y = camera.y + camera.height - 128;
            
            background.draw();
            floor.draw();
            ninja.draw();
            camera.draw();
            
            jump();
            
            window.update();
            
            sleep(16);
            
            if (keyboard.keyDown(Keyboard.ESCAPE_KEY))
                window.exit();
            
        }
        
    }
    
}

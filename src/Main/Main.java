
package Main;

//Jplay imports
import jplay.InputBase;
import jplay.Keyboard;
import jplay.Mouse;
import jplay.Sound;
import jplay.Window;

//Classe imports
import Characters.CoinThread;
import Characters.Enemy;
import Characters.KunaiThead;
import Characters.Ninja;
import Characters.TiledMapToSlide;
import Menus.Menu;
import Menus.Playing;
import Plataform.Floor;
import Plataform.Water;


//Others imports
import com.sun.glass.events.KeyEvent;
import javax.swing.ImageIcon;
import jplay.GameImage;

/**
 *
 * @author Rodrigo Fernando da Silva
 */
public class Main {
    
    //Java variables
    public static boolean isRunning = true;
    
    //Jplay variables
    public static Keyboard keyboard;
    public static Mouse mouse;
    public static Window window;
    
    //Classe variables
    public static CoinThread[] coinThread;
    public static Enemy[] enemy;
    public static KunaiThead[] kunaiThead;
    public static Ninja ninja;
    public static Floor floor;
    public static Playing playing;
    public static SomeMethods someMethods;
    public static TiledMapToSlide[] tiledMapToSlide;
    public static Water water;
    
    public static void main(String[] args) {
        
        Menu menu = new Menu();
        
        window = new Window(300, 400);
        mouse = window.getMouse();
        keyboard = window.getKeyboard();
        
        GameImage longButton = new GameImage("Images/Others/Long.png");
        GameImage shortButton = new GameImage("Images/Others/Short.png");
        GameImage ChoseResolution = new GameImage("Images/Others/ChoseResolution.png");
        
        longButton.y = 188;
        longButton.x = 12;
        shortButton.y = longButton.y;
        shortButton.x = 183;
        
        ImageIcon imageIcon = new ImageIcon("Images/Icon/Icon.png");
        window.setIconImage(imageIcon.getImage());
        
        while (true) {
            
            ChoseResolution.draw();
            
            longButton.draw();
            shortButton.draw();
            
            window.update();
            
            if (mouse.isLeftButtonPressed()) {
                if (mouse.isOverObject(longButton)) {
                    window.setSize(1280, 720);
                    break;
                } else if (mouse.isOverObject(shortButton)) {
                    window.setSize(800, 600);
                    break;
                }
            }
            
            if (keyboard.keyDown(Keyboard.ESCAPE_KEY)) {
                window.exit();
            }
        }
        
        playing = new Playing();
        
        window.setFullScreen();
        
        //Add the keys that is going to be used on game
        keyboard.addKey(KeyEvent.VK_ESCAPE, InputBase.DETECT_INITIAL_PRESS_ONLY);
        keyboard.addKey(KeyEvent.VK_SPACE, InputBase.DETECT_EVERY_PRESS);
        keyboard.addKey(KeyEvent.VK_UP, InputBase.DETECT_EVERY_PRESS);
        keyboard.addKey(KeyEvent.VK_DOWN, InputBase.DETECT_INITIAL_PRESS_ONLY);
        keyboard.addKey(KeyEvent.VK_LEFT, InputBase.DETECT_INITIAL_PRESS_ONLY);
        keyboard.addKey(KeyEvent.VK_RIGHT, InputBase.DETECT_INITIAL_PRESS_ONLY);
        keyboard.addKey(KeyEvent.VK_Z, InputBase.DETECT_INITIAL_PRESS_ONLY);
        keyboard.addKey(KeyEvent.VK_X, InputBase.DETECT_INITIAL_PRESS_ONLY);
        keyboard.addKey(KeyEvent.VK_P, InputBase.DETECT_INITIAL_PRESS_ONLY);
        
        //Create all variables methods
        someMethods = new SomeMethods();
        
        //Create all threads
        DeltaTime deltaTime = new DeltaTime();
        deltaTime = new DeltaTime();
        
        Sound music = new Sound("Sounds/Music/Music.wav");
        music.setRepeat(true);
        music.play();
        
        boolean playAgain = false;
        while (isRunning) {
            
            if (!playAgain) {
                menu.Menu();
            }
            
            playing.InitializeVariables();
            playing.InitializeThreads();
            playing.Playing();
            playAgain = playing.PlayAgain();
            
        }
        
        window.exit();
        
    }
    
}

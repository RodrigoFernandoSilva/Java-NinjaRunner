
package Menus;

//Jplay imports
import jplay.GameImage;

//Classe imports
import Characters.Ninja;
import Menus.PlayingMethods.PlayingDraw;
import Menus.PlayingMethods.PlayingUpdate;

//Variables imports
import static Main.DeltaTime.allThreadSleep;
import static Main.DeltaTime.deltaTime;
import static Main.Main.keyboard;
import static Main.Main.mouse;
import static Main.Main.ninja;
import static Main.Main.window;

//Others imports
import com.sun.glass.events.KeyEvent;
import static java.lang.Thread.sleep;
import javax.swing.JOptionPane;

/**
 *
 * @author Rodrigo Fernando da Silva
 */
public class Playing {
    
    //These variables is going to be used to initialize others variables
    public final String FILE_WAY = "Images/Playing/";
    
    //Java variables
    public boolean isPlaying;
    public double speed = 50;
    
    //Jplay variables
    public GameImage[] background;
    
    //Methods
    public PlayingDraw playingDraw;
    public PlayingUpdate playingUpdate;
    
    @SuppressWarnings("SleepWhileInLoop")
    public void Playing() {
        
        background[3].y = window.getHeight() - background[3].height + 30;
        background[2].y = window.getHeight() - background[2].height- 30;
        background[1].y = window.getHeight() - background[1].height- 60;
        background[0].y = window.getHeight() - background[0].height;
        
        int line; //It is used in 'for'
        double subSpeed; //It is used to make the backgrounds move in different speed
        //Game loop
        while (isPlaying) {
            
            //Move the background
            subSpeed = 1;
            for (line = 0; line < background.length; line ++) {
                background[line].x -= (speed * deltaTime) * subSpeed;
                subSpeed += 0.3;
            }
            
            //Reposition the background  after it exit the windows
            for (line = 0; line < background.length; line ++) {
                if (background[line].x + (background[line].width / 2) < 1) {
                    background[line].x += (background[line].width / 2);
                }
            }
            
            //Exit the game
            if (keyboard.keyDown(KeyEvent.VK_ESCAPE)) {
                isPlaying = false;
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
    @SuppressWarnings("SleepWhileInLoop")
    public void InitializeThreads() {
        ninja = new Ninja();
        ninja.start();
        
        //Wait some threads be created for do not have 'NullPointerExeption'
        while (true) {
            if (ninja.getNinjaOk()) {
                break;
            }
            
            try { //If do not have this 'sleep' the 'while' maybe never will exit
                sleep(100);
            } catch (InterruptedException ex) {
                JOptionPane.showMessageDialog(null, "Maybe the game crash in loading because: " + ex.getMessage());
            }
        }
        
        playingDraw = new PlayingDraw();
        playingDraw.start();
        playingUpdate = new PlayingUpdate();
        playingUpdate.start();
        
    }
    
    public void InitializeVariables() {
        isPlaying = true;
        
        background = new GameImage[4];
        background[3] = new GameImage(FILE_WAY + "Background/Background1.png");
        background[2] = new GameImage(FILE_WAY + "Background/Background2.png");
        background[1] = new GameImage(FILE_WAY + "Background/Background3.png");
        background[0] = new GameImage(FILE_WAY + "Background/Background4.png");
    }
    
    public void DrawBackground() {
        for (GameImage background1 : background) {
            background1.draw();
        }
    }
    
    public boolean GetIsPlaying() {
        return isPlaying;
    }
    
    public double GetSpeed() {
        return speed;
    }
}

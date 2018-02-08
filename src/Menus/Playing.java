
package Menus;

//Jplay imports
import jplay.GameImage;

//Classe imports
import Characters.Enemy;
import Characters.Ninja;
import Menus.PlayingMethods.PlayingDraw;
import Menus.PlayingMethods.PlayingUpdate;
import Menus.PlayingMethods.PlayingWindow;
import Plataform.Floor;
import Plataform.Water;

//Variables imports
import static Main.DeltaTime.allThreadSleep;
import static Main.DeltaTime.deltaTime;
import static Main.Main.enemy;
import static Main.Main.floor;
import static Main.Main.keyboard;
import static Main.Main.ninja;
import static Main.Main.someMethods;
import static Main.Main.water;
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
    
    //Java variables
    public boolean allThreadsOk; //Some threads need wait all threads be create to do not have 'NullPointerException'
    public boolean isPlaying;
    public boolean enemyOk;
    public boolean floorOk;
    public boolean ninjaOk;
    public boolean playingWindowOk;
    public boolean waterOk;
    private double speed = 150; //50
    private double subSpeedMax;
    private int timeNextSpeed = 0;
    private final int TIME_NEXT_SPEED = 50;
    private final String FILE_WAY = "Images/Playing/"; //These variables is going to be used to initialize others variables
    /**
     * It is used in 'for'.
     */
    public int line;
    
    //Jplay variables
    private GameImage[] background;
    private GameImage coinHUD;
    private GameImage enemyHUD;
    private GameImage kunaiHUD;
    private GameImage metersHUD;
    
    //Classe variables
    private PlayingDraw playingDraw;
    private PlayingUpdate playingUpdate;
    private PlayingWindow playingWindow;
    
    @SuppressWarnings("SleepWhileInLoop")
    public void Playing() {
        
        background[3].y = someMethods.SetPositionBelowWindow(background[3], window) + 30;
        background[2].y = someMethods.SetPositionBelowWindow(background[2], window)- 30;
        background[1].y = someMethods.SetPositionBelowWindow(background[1], window) - 60;
        background[0].y = someMethods.SetPositionBelowWindow(background[0], window);
        
        coinHUD = new GameImage(FILE_WAY + "HUD/Coin.png");
        coinHUD.x = 17;
        coinHUD.y = 15;
        
        kunaiHUD = new GameImage(FILE_WAY + "HUD/Kunai.png");
        kunaiHUD.x = coinHUD.x + coinHUD.x + coinHUD.width;
        kunaiHUD.y = coinHUD.y;
        
        enemyHUD = new GameImage(FILE_WAY + "HUD/Enemy.png");
        enemyHUD.x = coinHUD.x + kunaiHUD.x + kunaiHUD.width;
        enemyHUD.y = coinHUD.y;
        
        metersHUD = new GameImage(FILE_WAY + "HUD/Meters.png");
        metersHUD.x = coinHUD.x + enemyHUD.x + enemyHUD.width;
        metersHUD.y = coinHUD.y;
        
        subSpeedMax = 1 + ((background.length - 1) * 0.3);
        
        double subSpeed; //It is used to make the backgrounds move in different speed
        //Game loop
        while (isPlaying) {
            
            if (speed < 150) {
                if (timeNextSpeed > TIME_NEXT_SPEED) {
                    speed ++;
                    timeNextSpeed = 0;
                }
                timeNextSpeed++;
            }
            
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
        allThreadsOk = false;
        enemyOk = false;
        floorOk = false;
        ninjaOk = false;
        playingWindowOk = false;
        waterOk = false;
        
        enemy = new Enemy[20];
        
        floor = new Floor();
        floor.start();
        
        ninja = new Ninja();
        ninja.start();
        
        water = new Water();
        water.start();
        
        playingWindow = new PlayingWindow();
        playingWindow.start();
        
        //Wait some threads be created for do not have 'NullPointerExeption'
        while (true) {
            if (ninjaOk && floorOk && waterOk && playingWindowOk) {
                break;
            }
            
            try { //If do not have this 'sleep' the 'while' maybe never will exit
                sleep(100);
            } catch (InterruptedException ex) {
                JOptionPane.showMessageDialog(null, "Maybe the game crash in loading because: " + ex.getMessage());
            }
        }
        
        allThreadsOk = true;
        
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
    
    public void DrawHUD() {
        coinHUD.draw();
        enemyHUD.draw();
        kunaiHUD.draw();
        metersHUD.draw();
    }
    
    public void DrawPlayingWindow() {
        playingWindow.DrawCamera();
    }
    
    public double GetSpeed() {
        return speed;
    }
    
    public double GetSubSpeedMax() {
        return subSpeedMax;
    }
    
    public boolean GetIsPlaying() {
        return isPlaying;
    }
    
    public GameImage GetCoinHUD() {
        return coinHUD;
    }
    
    public GameImage GetEnemyHUD() {
        return enemyHUD;
    }
    
    public GameImage GetKunaiHUD() {
        return kunaiHUD;
    }
    
    public GameImage GetMetersHUD() {
        return metersHUD;
    }
    
    public PlayingWindow GetPlayingWindow() {
        return playingWindow;
    }
}


package Menus;

//Jplay imports
import jplay.Animation;
import jplay.Sound;
import jplay.GameImage;
import jplay.Keyboard;

//Classe imports
import Characters.CoinThread;
import Characters.Enemy;
import Characters.KunaiThead;
import Characters.Ninja;
import Characters.TiledMapToSlide;
import Main.Sky;
import Menus.PlayingMethods.PlayingDraw;
import Menus.PlayingMethods.PlayingUpdate;
import Menus.PlayingMethods.PlayingWindow;
import Plataform.Floor;
import Plataform.Water;

//Variables imports
import static Main.DeltaTime.allThreadSleep;
import static Main.DeltaTime.deltaTime;
import static Main.Main.coinThread;
import static Main.Main.enemy;
import static Main.Main.floor;
import static Main.Main.kunaiThead;
import static Main.Main.keyboard;
import static Main.Main.ninja;
import static Main.Main.mouse;
import static Main.Main.someMethods;
import static Main.Main.tiledMapToSlide;
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
    public boolean day;
    public boolean isPaused;
    public boolean isPlaying;
    public boolean enemyOk;
    public boolean floorOk;
    public boolean ninjaOk;
    public boolean playingWindowOk;
    public boolean skyOk;
    public boolean waterOk;
    private double speed;
    private double subSpeedMax;
    private int timeNextSpeed ;
    private final int TIME_NEXT_SPEED = 50;
    private final String BUTTON_FILE = "Sounds/Others/Button.wav";
    private final String FILE_WAY = "Images/Playing/"; //These variables is going to be used to initialize others variables
    /**
     * It is used in 'for'.
     */
    public int line;
    
    //Jplay variables
    public Animation[] backgroundDarkSky;
    public Animation[] backgroundSky;
    private GameImage coinHUD;
    private GameImage enemyHUD;
    private GameImage kunaiHUD;
    private GameImage metersHUD;
    
    //Classe variables
    private PlayingDraw playingDraw;
    private PlayingUpdate playingUpdate;
    private PlayingWindow playingWindow;
    private Sky sky;
    
    @SuppressWarnings("SleepWhileInLoop")
    public void Playing() {
        
        speed = 50;
        timeNextSpeed = 0;
        
        day = true;
        
        backgroundSky[3].y = someMethods.SetPositionBelowWindow(backgroundSky[3], window) + 30;
        backgroundSky[2].y = someMethods.SetPositionBelowWindow(backgroundSky[2], window)- 30;
        backgroundSky[1].y = someMethods.SetPositionBelowWindow(backgroundSky[1], window) - 60;
        backgroundSky[0].y = someMethods.SetPositionBelowWindow(backgroundSky[0], window);
        
        backgroundDarkSky[3].y = someMethods.SetPositionBelowWindow(backgroundDarkSky[3], window) + 35;
        backgroundDarkSky[2].y = someMethods.SetPositionBelowWindow(backgroundDarkSky[2], window) + 10;
        backgroundDarkSky[1].y = someMethods.SetPositionBelowWindow(backgroundDarkSky[1], window) + 40;
        backgroundDarkSky[0].x = (window.getWidth() - backgroundDarkSky[0].width) / 2;
        backgroundDarkSky[0].y = someMethods.SetPositionBelowWindow(backgroundDarkSky[0], window);
        
        
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
        
        subSpeedMax = 1 + ((backgroundSky.length - 1) * 0.3);
        
        double subSpeed; //It is used to make the backgrounds move in different speed
        //Game loop
        while (isPlaying) {
            
            if (!isPaused) {
                if (speed < 150) {
                    if (timeNextSpeed > TIME_NEXT_SPEED) {
                        speed ++;
                        timeNextSpeed = 0;
                    }
                    timeNextSpeed++;
                }

                //Move the background
                subSpeed = 1;
                for (line = 0; line < backgroundSky.length; line ++) {
                    backgroundSky[line].x -= (speed * deltaTime) * subSpeed;
                    if (line != 0)
                        backgroundDarkSky[line].x -= (speed * deltaTime) * subSpeed;
                    subSpeed += 0.3;
                }

                //Reposition the background  after it exit the windows
                for (line = 0; line < backgroundSky.length; line ++) {
                    if (backgroundSky[line].x + (backgroundSky[line].width / 2) < 1) {
                        backgroundSky[line].x += (backgroundSky[line].width / 2);
                        if (line != 0)
                            backgroundDarkSky[line].x += (backgroundSky[line].width / 2);
                    }
                }
            }
            
            if (keyboard.keyDown(KeyEvent.VK_P)) {
                isPaused = !isPaused;
            }
            /*Exit the game
            else if (keyboard.keyDown(KeyEvent.VK_ESCAPE)) {
                isPlaying = false;
            }*/
            
            
            //This sleep is equals for all threads
            try {
                sleep(allThreadSleep);
            } catch (InterruptedException ex) {
                JOptionPane.showMessageDialog(null ,"Maybe the game crash because: " + ex.getMessage());
            }
            
        }
        
        someMethods.WaitSomeThreadOver();
        
    }
    
    /*----  Classe methods  ----*/
    @SuppressWarnings("SleepWhileInLoop")
    public void InitializeThreads() {
        isPaused = false;
        
        allThreadsOk = false;
        enemyOk = false;
        floorOk = false;
        ninjaOk = false;
        playingWindowOk = false;
        skyOk = false;
        waterOk = false;
        
        coinThread = new CoinThread[20];
        enemy = new Enemy[20];
        kunaiThead = new KunaiThead[20];
        tiledMapToSlide = new TiledMapToSlide[20];
        
        floor = new Floor();
        floor.start();
        
        ninja = new Ninja();
        ninja.start();
        
        sky = new Sky();
        sky.start();
        
        water = new Water();
        water.start();
        
        playingWindow = new PlayingWindow();
        playingWindow.start();
        
        //Wait some threads be created for do not have 'NullPointerExeption'
        while (true) {
            if (ninjaOk && floorOk && waterOk && playingWindowOk && skyOk) {
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
        GameImage loading = new GameImage(FILE_WAY + "Others/Carregando.png");
        loading.x = (window.getWidth() - loading.width) / 2;
        loading.y = (window.getHeight()- loading.height) / 2;
        loading.draw();
        window.update();
        
        isPlaying = true;
        
        backgroundSky = new Animation[4];
        backgroundSky[3] = new Animation(FILE_WAY + "Background/Sky/1/Bc_1.png", 8, true);
        backgroundSky[2] = new Animation(FILE_WAY + "Background/Sky/2/Bc_2.png", 8, true);
        backgroundSky[1] = new Animation(FILE_WAY + "Background/Sky/3/Bc_3.png", 8, true);
        backgroundSky[0] = new Animation(FILE_WAY + "Background/Sky/4/Bc_4.png", 8, true);
        
        backgroundDarkSky = new Animation[4];
        backgroundDarkSky[3] = new Animation(FILE_WAY + "Background/DarkSky/1/Bc_1.png", 8, true);
        backgroundDarkSky[2] = new Animation(FILE_WAY + "Background/DarkSky/2/Bc_2.png", 8, true);
        backgroundDarkSky[1] = new Animation(FILE_WAY + "Background/DarkSky/3/Bc_3.png", 8, true);
        backgroundDarkSky[0] = new Animation(FILE_WAY + "Background/DarkSky/4/Bc_4.png", 8, true);
        
        for (int i = 0; i < backgroundSky.length; i++) {
            backgroundSky[i].setSequence(0, 4);
            backgroundDarkSky[i].setSequence(4, 8);
            backgroundSky[i].setTotalDuration(2500);
            backgroundDarkSky[i].setTotalDuration(backgroundSky[i].getTotalDuration());
        }
    }
    
    public void DrawBackground() {
        if (day) {
            for (GameImage background1 : backgroundSky) {
                background1.draw();
            }
        } else {
            for (GameImage background1 : backgroundDarkSky) {
                background1.draw();
            }
        }
    }
    
    public void UpdateBackground() {
        if (backgroundSky[0].getCurrFrame() < backgroundSky[0].getFinalFrame() - 1) {
            for (Animation backgroundSky1 : backgroundSky) {
                backgroundSky1.update();
            }
        }
        if (backgroundDarkSky[0].getCurrFrame() < backgroundDarkSky[0].getFinalFrame() - 1) {
            for (Animation backgroundDarkSky1 : backgroundDarkSky) {
                backgroundDarkSky1.update();
            }
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
    
    public PlayingDraw GetPlayingDraw() {
        return playingDraw;
    }
    
    public PlayingUpdate GetPlayingUpdate() {
        return playingUpdate;
    }
    
    /**
     * Return true if the player want to play again and false if he do not want to play again.
     * 
     * @return 
     */
    public boolean PlayAgain() {
        Sound buttonSound;
        GameImage gameOver = new GameImage(FILE_WAY + "GameOVer/GameOver.png");
        GameImage menu = new GameImage(FILE_WAY + "GameOVer/Menu.png");
        GameImage restart = new GameImage(FILE_WAY + "GameOVer/Restart.png");
        GameImage bc = new GameImage(FILE_WAY + "Others/Pause.png");
        
        gameOver.x = (window.getWidth() - gameOver.width) / 2;
        gameOver.y = (window.getHeight()- gameOver.height) / 2;
        
        menu.x = gameOver.x + ((gameOver.width - menu.width) / 2) - 62;
        menu.y = gameOver.y + gameOver.height - menu.height- 12;
        
        restart.x = menu.x + menu.width + 50;
        restart.y = menu.y;
        
        bc.width = window.getWidth();
        bc.height = window.getHeight();
        
        bc.draw();
        gameOver.draw();
        menu.draw();
        restart.draw();
        someMethods.DrawBigNumbersOnMiddle(ninja.coin, gameOver, 35, -110);
        someMethods.DrawBigNumbersOnMiddle(ninja.enemy, gameOver, 35, -4);
        someMethods.DrawBigNumbersOnMiddle(ninja.meters, gameOver, 35, 101);
        
        window.update();
        
        while (true) {
            
            if (mouse.isLeftButtonPressed()) {
                if (mouse.isOverObject(menu)) {
                    buttonSound = new Sound(BUTTON_FILE);
                    buttonSound.play();
                    return false;
                } else if (mouse.isOverObject(restart)) {
                    buttonSound = new Sound(BUTTON_FILE);
                    buttonSound.play();
                    return true;
                }
            }
            if (keyboard.keyDown(Keyboard.ESCAPE_KEY)) {
                return false;
            }
            
            //This sleep is equals for all threads
            try {
                sleep(allThreadSleep);
            } catch (InterruptedException ex) {
                JOptionPane.showMessageDialog(null ,"Maybe the game crash because: " + ex.getMessage());
            }
            
        }
        
    }
    
}

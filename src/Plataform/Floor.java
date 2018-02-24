
package Plataform;

//Jplay imports
import Characters.CoinThread;
import jplay.Animation;
import jplay.GameImage;

//Variables imports
import static Main.DeltaTime.allThreadSleep;
import static Main.DeltaTime.deltaTime;
import static Main.Main.coinThread;
import static Main.Main.enemy;
import static Main.Main.kunaiThead;
import static Main.Main.ninja;
import static Main.Main.playing;
import static Main.Main.someMethods;
import static Main.Main.tiledMapToSlide;
import static Main.Main.window;

//Classes imports
import Characters.Enemy;
import Characters.KunaiThead;
import Characters.TiledMapToSlide;

//Others imports
import javax.swing.JOptionPane;
import java.util.Random;

/**
 *
 * @author Rodrigo Fernando da Silva
 */
public class Floor extends Thread {
    
    //Java variables
    private boolean[] enemyGenerated;
    private double speed;
    public double[] y;
    private int firstFloor;
    private int lastFloor;
    private int line;
    private int[] floorBockNumber;
    /**
     * The first id of each line shows how many object this floor has and the second id of each
     * line show which block type
     */
    private int[][] objectID;
    private final String FILE_WAY = "Images/Playing/Plataform/";
    private Random generator;
    
    //Jplay variables
    public GameImage[] floorLeftDark;
    public GameImage[] floorLeft;
    private GameImage floorRightDark;
    public GameImage floorRight;
    private GameImage object;
    
    @Override
    @SuppressWarnings("SleepWhileInLoop")
    public void run() {
        
        generator = new Random();
        
        floorLeftDark = new Animation[7];
        floorLeft = new Animation[7];
        floorBockNumber = new int[floorLeft.length];
        objectID = new int[floorLeft.length][9];
        y = new double[floorLeft.length];
        enemyGenerated = new boolean[floorLeft.length];
        
        for (line = 0; line < floorLeft.length; line ++) {
            floorLeftDark[line] = new Animation(FILE_WAY + "DarkSky/TiledMapLeft.png");
            floorLeft[line] = new Animation(FILE_WAY + "Sky/TiledMapLeft.png");
            enemyGenerated[line] = false;
        }
        
        floorRightDark = new Animation(FILE_WAY + "DarkSky/TiledMapRight.png");
        floorRight = new Animation(FILE_WAY + "Sky/TiledMapRight.png");
        
        firstFloor = 0;
        lastFloor = floorLeft.length - 1;
        
        InitializeFloors();
        
        playing.floorOk = true;
        
        someMethods.WaitAllThreadsFinish();
        
        while (playing.GetIsPlaying()) {
            
            someMethods.PauseTheGame();
            
            speed = (playing.GetSpeed() * 1.3) * playing.GetSubSpeedMax();
            
            //Move the floor
            for (line = 0; line < floorLeft.length; line ++) {
                floorLeft[line].x -= (speed) * deltaTime;
                floorLeftDark[line].x = floorLeft[line].x;
                //Reposition the floor after it exit the window
                if (someMethods.ExitWindow(floorLeft[line], floorRight.width * 3)) {
                    RepositionFloor(line);
                }
                
                if (!enemyGenerated[line] && floorLeft[line].x < window.getWidth()) {
                    enemyGenerated[line] = true;
                    GenerateSonsPositionX(line);
                }
            }
            
            //Sets who is the first floor
            if ((floorLeft[firstFloor].x + floorLeft[firstFloor].width + (floorRight.width * 2)) <
                 ninja.GetSoul().x + ninja.GetSoul().width) {
                firstFloor ++;
                if (firstFloor > floorLeft.length - 1)
                    firstFloor = 0;
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
    /**
     * Sets the object that each floor is going to have, but have to pass the length of the floor
     * to know how many object this floor can have.
     * 
     * @param index
     * @param width 
     */
    private void SetObjectID(int index, int width) {
        int blockNumber = (width + 64) / 64;
        int randomLength = 5;
        
        if (blockNumber <= 3) {
            objectID[index][0] = 1;
            objectID[index][1] = 1;
        } else if (blockNumber <= 6) {
            objectID[index][0] = 2;
            objectID[index][1] = generator.nextInt(2)+ 1;
        } else if (blockNumber <= 9) {
            objectID[index][0] = 3;
            objectID[index][1] = generator.nextInt(2)+ 1;
        } else if (blockNumber <= 12) {
            objectID[index][0] = 4;
            objectID[index][1] = 3;
        } else if (blockNumber <= 15) {
            objectID[index][0] = 5;
            objectID[index][1] = generator.nextInt(3)+ 1;
        } else if (blockNumber <= 18) {
            objectID[index][0] = 6;
            objectID[index][1] = generator.nextInt(3)+ 1;
        } else if (blockNumber > 18) {
            objectID[index][0] = 7;
            objectID[index][1] = generator.nextInt(3)+ 1;
        }
        
        for (int i = 0; i < objectID[index][0]; i ++) {
            if (generator.nextInt(10) > 3)
                objectID[index][i + 2] = generator.nextInt(randomLength) + 1;
            else
                objectID[index][i + 2] = 0;
        }
    }
    
    /**
     * Initialize all floor position, its random, this methods have to be called before the games
     * start, after the game start have to call the 'RepositionFloor' method
     */
    private void InitializeFloors() {
        for (line = 0; line < floorLeft.length; line ++) {
            
            if (line != 0) {
                floorBockNumber[line] = GenerateFloorBockNumber(line);
                floorLeft[line].x = floorLeft[line - 1].x + floorLeft[line - 1].width + (floorRight.width * 2);
                floorLeft[line].x += (generator.nextInt(5) * floorRight.width); //Distance between the floors
                floorLeft[line].width = (generator.nextInt(20) + 1) * floorRight.width;
            } else {
                floorBockNumber[line] = generator.nextInt(2) + 1;
                floorLeft[line].width = (generator.nextInt(6) + 6) * floorRight.width;
                enemyGenerated[line] = true;
            }
            
            SetObjectID(line, floorLeft[line].width);
            
            y[line] = floorLeft[line].height - 128;
            y[line] -= (floorBockNumber[line] * 64);
            
            floorLeftDark[line].x = floorLeft[line].x;
            floorLeftDark[line].width = floorLeft[line].width;
            
        }
    }
    /**
     * Generates a new raondom position to the floor that exit the windows
     * 
     * @param index 
     */
    private void RepositionFloor(int index) {
        
        floorBockNumber[index] = GenerateFloorBockNumber(index);
        
        floorLeft[index].x = floorLeft[lastFloor].x + floorLeft[lastFloor].width + (floorRight.width * 2);
        floorLeft[line].x += (generator.nextInt(2) * floorRight.width);
        floorLeft[index].width = (generator.nextInt(19) + 1) * floorRight.width;
        
        floorLeft[index].width = (generator.nextInt(15) + 6) * floorRight.width;
        y[index] = floorLeft[index].height - 128;
        y[index] -= (floorBockNumber[index] * 64);
        
        SetObjectID(index, floorLeft[index].width);
        
        lastFloor = index;
        enemyGenerated[index] = false;
    }
    
    /**
     * Generates the distance in 'Y' that the ninja is going to have to jump
     * 
     * @param index
     * @return 
     */
    private int GenerateFloorBockNumber(int index) {
        int value;
        
        if (index == 0) {
            value = generator.nextInt(5) - 2 + floorBockNumber[floorLeft.length - 1];
        } else {
            value = generator.nextInt(5) - 2 + floorBockNumber[index - 1];
        }
        
        for (int i = 0; i < 2; i ++) {
            if (value < 1) {
                value += generator.nextInt(2) + 1;
            } else if (value > 7) {
                value -= generator.nextInt(2) + 1;
            }
        }
        
        return value;
    }
    
    /**
     * Generates the son that is going to be over the floor. They can be the enemy, block to
     * slide, kunai or a coin.
     * 
     * @param index 
     */
    private void GenerateSonsPositionX(int floorIndex) {
        boolean isSlide = false;
        double[] objectX = new double[enemy.length];
        int numberOfBLocks = (int) (Math.round(floorLeft[floorIndex].width / floorRight.width));
        int objectLine = 0;
        
        if (numberOfBLocks > 9 && generator.nextBoolean()) {
            isSlide = true;
            if (numberOfBLocks < 18) {
                objectX[objectLine] = (floorLeft[floorIndex].width / 2) + (generator.nextInt(64));
                
            } else {
                objectX[objectLine] = (floorLeft[floorIndex].width / 2) - (generator.nextInt(128) - 256);
                
                if (generator.nextInt(10) < 7) {
                    objectLine ++;
                    objectX[objectLine] = (floorLeft[floorIndex].width - 256) - (generator.nextInt(64));
                }
                
            }
            
        } else if (numberOfBLocks > 2) {
            if (numberOfBLocks <= 4) {
                objectX[objectLine] = floorLeft[floorIndex].width - 15;
                
            } else if (numberOfBLocks == 5) {
                objectX[objectLine] = floorLeft[floorIndex].width - 15 - (generator.nextInt(100));
                
            } else if (numberOfBLocks <= 7) {
                objectX[objectLine] = 182f + generator.nextInt(floorLeft[floorIndex].width - 182) - 15f;
                if (generator.nextInt(10) <= 4) {
                    objectLine++;
                    if (objectX[objectLine - 1] + 118 + 84 > floorLeft[floorIndex].width + floorRight.width) {
                        objectX[objectLine] = objectX[objectLine - 1] - 118;
                    } else {
                        objectX[objectLine] = objectX[objectLine - 1] + 118;
                    }
                }
                
            } else if (numberOfBLocks <= 12) {
                objectX[objectLine] = 182f + generator.nextInt(floorLeft[floorIndex].width - 182) - 15f;
                if (generator.nextInt(10) <= 6) {
                    objectLine++;
                    if (objectX[objectLine - 1] + 118 + 84 > floorLeft[floorIndex].width + floorRight.width) {
                        objectX[objectLine] = objectX[objectLine - 1] - 118;
                    } else {
                        objectX[objectLine] = objectX[objectLine - 1] + 118;
                    }
                }
                
            } else if (numberOfBLocks <= 17) {
                objectX[objectLine] = 182f + generator.nextInt(floorLeft[floorIndex].width - 182) - 15f;
                if (generator.nextInt(10) <= 8) {
                    objectLine++;
                    if (objectX[objectLine - 1] + 118 + 84 > floorLeft[floorIndex].width + floorRight.width) {
                        objectX[objectLine] = objectX[objectLine - 1] - 118;
                    } else {
                        objectX[objectLine] = objectX[objectLine - 1] + 118;
                    }
                }
                
            } else if (numberOfBLocks > 17) {
                boolean startOrEnd = generator.nextBoolean();
                
                if (startOrEnd) {
                    objectX[objectLine] = 182f + generator.nextInt(182);
                    if (generator.nextBoolean()) { 
                        objectLine ++;
                        objectX[objectLine] = objectX[objectLine - 1] + 118;
                    }
                } else if (!startOrEnd) {
                    objectX[objectLine] = (floorLeft[floorIndex].width - 182) - 15f;
                    objectX[objectLine] -= generator.nextInt(182);
                    if (generator.nextBoolean()) { 
                        objectLine++;
                        objectX[objectLine] = objectX[objectLine - 1] + 118;
                    }
                }
                
                //Enemy on the middle
                if (generator.nextBoolean()) { 
                    objectLine++;
                    objectX[objectLine] = ((floorLeft[floorIndex].width / 2));
                    if (generator.nextBoolean()) { 
                        objectLine ++;
                        objectX[objectLine] = objectX[objectLine - 1] + 118;
                    }
                }
            }
        }
        
        InitializeObjects(floorIndex, objectX, y[floorIndex], (objectLine + 1), isSlide);
    }
    
    public void InitializeObjects(int father, double[] x, double y, int lenght, boolean isSlide) {
        
        
        if (isSlide) {
            for (int i1 = 0; i1 < lenght; i1++) {
                for (int i0 = 0; i0 < tiledMapToSlide.length; i0++) {
                    if (tiledMapToSlide[i0] == null) {
                        tiledMapToSlide[i0] = new TiledMapToSlide(father, x[i1], i0);
                        tiledMapToSlide[i0].start();
                        
                        break;
                    }
                }
            }   
        } else {
            @SuppressWarnings("UnusedAssignment")
            String whatIs = null;

            if (generator.nextInt(10) < 5) {
                whatIs = "Enemy";
            } else if (generator.nextInt(10) < 5) {
                whatIs = "Coin";
            } else {
                whatIs = "Kunai";
            }

            for (int i1 = 0; i1 < lenght; i1++) {
                if ("Enemy".equalsIgnoreCase(whatIs)) {
                    for (int i0 = 0; i0 < enemy.length; i0++) {
                        if (enemy[i0] == null) {
                            enemy[i0] = new Enemy(father, x[i1], y, i0);
                            enemy[i0].start();
                            break;
                        }
                    }
                } else if ("Coin".equalsIgnoreCase(whatIs)) {
                    for (int i0 = 0; i0 < coinThread.length; i0++) {
                        if (coinThread[i0] == null) {
                            coinThread[i0] = new CoinThread(father, x[i1], y, i0);
                            coinThread[i0].start();
                            break;
                        }
                    }
                } else if ("Kunai".equalsIgnoreCase(whatIs)) {
                    for (int i0 = 0; i0 < kunaiThead.length; i0++) {
                        if (kunaiThead[i0] == null) {
                            kunaiThead[i0] = new KunaiThead(father, x[i1], y, i0);
                            kunaiThead[i0].start();
                            break;
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Programing erro, " + whatIs + " does not exist!!!");
                }
            }
        }
    }
    
    public double GetSpeed() {
        return speed;
    }
    
    public int GetFirstFloorId() {
        return firstFloor;
    }
    
    public void DrawLeftFloor() {
        if (playing.day) {
            for (GameImage index : floorLeft) {
                if (someMethods.IsOnScene(index, window)) {
                    index.draw();
                }
            }
        } else {
            for (GameImage index : floorLeftDark) {
                if (someMethods.IsOnScene(index, window)) {
                    index.draw();
                }
            }
        }
    }
    
    public void DrawRightFloor() {
        if (playing.day) {
            for (GameImage index : floorLeft) {
                floorRight.x = index.x + index.width;
                floorRight.y =  index.y;
                if (someMethods.IsOnScene(floorRight, window)) {
                    floorRight.draw();
                }
            }
        } else {
            for (GameImage index : floorLeft) {
                floorRightDark.x = index.x + index.width;
                floorRightDark.y =  index.y;
                if (someMethods.IsOnScene(floorRightDark, window)) {
                    floorRightDark.draw();
                }
            }
        }
    }
    
    public void DrawObjects() {
        String skyFIle = "Sky/Object/";
        
        if (!playing.day) {
            skyFIle = "DarkSky/Object/";
        }
        
        for (int i = 0; i < floorLeft.length; i ++) {
            for (int column = 0; column < objectID[i][0]; column ++) {
                if (objectID[i][column + 2] != 0) {
                    object = new GameImage(FILE_WAY  + skyFIle + String.valueOf(objectID[i][1]) +
                            "Blocks" + String.valueOf(objectID[i][column + 2]) + ".png");
                    if (someMethods.IsOnScene(object, window)) {
                        object.x = someMethods.PutImageOnMiddle(object, floorLeft[i], floorRight.width, objectID[i][0], (column));
                        object.y = floorLeft[i].y - object.height + 2;
                        object.draw();
                    }
                }
            }
        }
    }
    
    public double GetFirstFloorY() {
        return y[firstFloor];
    }
    
    /**
     * Returns if the image collided with one floor, but sees if the image is inside the floor.
     * 
     * @param image
     * @return 
     */
    public boolean Hit(GameImage image) {
        for (GameImage index : floorLeft) {
            if (index.collided(image) && image.x > index.x) {
                return true;
            }
        }
        
        return false;
    }
    
    public void SetNullCoinThread(int index) {
        coinThread[index] = null;
    }
    
    public void SetNullEnemyThread(int index) {
        enemy[index] = null;
    }
    
    public void SetNullKunaiThread(int index) {
        kunaiThead[index] = null;
    }
    
    public void SetNullTiledMapToSlide(int index) {
        tiledMapToSlide[index] = null;
    }
}

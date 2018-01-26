
package Plataform;

//Jplay imports
import jplay.Animation;
import jplay.GameImage;

//Variables imports
import static Main.DeltaTime.allThreadSleep;
import static Main.DeltaTime.deltaTime;
import static Main.Main.ninja;
import static Main.Main.playing;
import static Main.Main.someMethods;

//Others imports
import javax.swing.JOptionPane;
import java.util.Random;

/**
 *
 * @author Rodrigo Fernando da Silva
 */
public class Floor extends Thread {
    
    //Java variables
    private double speed;
    private int firstFloor;
    private int lastFloor;
    private int line;
    private int[] floorBockNumber;
    /**
     * The first id of each line shows how many object this floor has and the second id of each
     * line show which block type
     */
    private int[][] objectID;
    private final String FILE_WAY = "Images/Plataform/";
    private Random generator;
    
    //Jplay variables
    private Animation[] floorLeft;
    private GameImage floorRight;
    private GameImage object;
    
    @Override
    @SuppressWarnings("SleepWhileInLoop")
    public void run() {
        
        generator = new Random();
        
        floorLeft = new Animation[7];
        floorBockNumber = new int[floorLeft.length];
        objectID = new int[floorLeft.length][9];
        
        for (line = 0; line < floorLeft.length; line ++)
            floorLeft[line] = new Animation(FILE_WAY + "TiledMapLeft.png");
        
        floorRight = new Animation(FILE_WAY + "TiledMapRight.png");
        
        firstFloor = 0;
        lastFloor = floorLeft.length - 1;
        
        InitializeFloors();
        
        playing.floorOk = true;
        
        someMethods.WaitAllThreadsFinish();
        
        while (playing.GetIsPlaying()) {
            
            speed = (playing.GetSpeed() * 1.3) * playing.GetSubSpeedMax();
            
            //Move the floor
            for (line = 0; line < floorLeft.length; line ++) {
                floorLeft[line].x -= (speed) * deltaTime;
                //Reposition the floor after it exit the window
                if (someMethods.ExitWindow(floorLeft[line], floorRight.width * 3))
                    RepositionFloor(line);
            }
            
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
                floorLeft[line].x += (generator.nextInt(2) * floorRight.width);
                floorLeft[line].width = (generator.nextInt(20) + 1) * floorRight.width;

            } else {
                floorBockNumber[line] = generator.nextInt(2) + 1;
                floorBockNumber[line] = 0;
                floorLeft[line].width = (generator.nextInt(15) + 6) * floorRight.width;
            }
            
            SetObjectID(line, floorLeft[line].width);
            
            floorLeft[line].y = floorLeft[line].height - 128;
            floorLeft[line].y -= (floorBockNumber[line] * 64);
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
        floorLeft[index].y = floorLeft[index].height - 128;
        floorLeft[index].y -= (floorBockNumber[index] * 64);
        
        SetObjectID(index, floorLeft[index].width);
        
        lastFloor = index;
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
    
    public double GetSpeed() {
        return speed;
    }
    
    public void DrawLeftFloor() {
        for (Animation index : floorLeft)
            index.draw();
    }
    
    public void DrawRightFloor() {
        for (Animation index : floorLeft) {
            floorRight.x = index.x + index.width;
            floorRight.y =  index.y;
            floorRight.draw();
        }
    }
    
    public void DrawObjects() {
        String objectName;
        
        for (int i = 0; i < floorLeft.length; i ++) {
            for (int column = 0; column < objectID[i][0]; column ++) {
                if (objectID[i][column + 2] != 0) {
                    object = new GameImage(FILE_WAY  + "Object/" + String.valueOf(objectID[i][1]) +
                            "Blocks" + String.valueOf(objectID[i][column + 2]) + ".png");
                    object.x = someMethods.PutImageOnMiddle(object, floorLeft[i], floorRight.width, objectID[i][0], (column));
                    object.y = floorLeft[i].y - object.height + 2;
                    object.draw();
                }
            }
        }
    }
    
    public double GetFirstFloorY() {
        return floorLeft[firstFloor].y;
    }
    
}

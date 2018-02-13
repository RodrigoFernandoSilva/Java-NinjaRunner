
package Characters;

//Jplay imports
import jplay.Sprite;
import jplay.Animation;

//Variables imports
import static Main.DeltaTime.allThreadSleep;
import static Main.Main.floor;
import static Main.Main.playing;
import static Main.Main.someMethods;

//Others imports
import javax.swing.JOptionPane;

/**
 *
 * @author Rodrigo Fernando da Silva
 */
public class Ninja extends Characters {
    
    //Java variables
    private boolean isJumping = false;
    public boolean ninjaAnimationOk;
    public boolean ninjaKeyboardOk;
    private boolean onFloor = true;
    private double ninjaFloor;
    /**
     * It is the additional jump force.
     */
    public double jumpForce;
    public double velocityY = 0;
    private final double GRAVITY = 0.098;
    private final double JUMP_FORCE = 5.5;
    private final String FILE_WAY = "Images/Playing/Ninja/";
    private int line;
    public int coin;
    public int enemy;
    public int kunai;
    public int meters;
    
    //Classe variables
    private NinjaAnimation ninjaAnimation;
    private NinjaKeyboard ninjaKeyboard;
    public NinjaKunai[] ninjaKunai;
    
    @Override
    @SuppressWarnings("SuspiciousIndentAfterControlStatement")
    public void run() {
        
        int slideTime = 0;
        final int SLIDE_TIME = 130;
        
        itOver = false;
        ninjaAnimationOk = false;
        ninjaKeyboardOk = false;
        
        y = 0;
        
        coin = 0;
        enemy = 0;
        kunai = 6;
        meters = 0;
        
        soul = new Sprite(FILE_WAY + "soul.png");
        spriteSheet = new Animation[3];
        spriteSheet[0] = new Animation(FILE_WAY + "SpriteSheet1.png", 31);
        spriteSheet[1] = new Animation(FILE_WAY + "SpriteSheet2.png", 31);
        spriteSheet[2] = new Animation(FILE_WAY + "SpriteSheet3.png", 21);
        
        spriteAdjustX = new double[spriteSheet.length][3];
        spriteAdjustY = new double[spriteSheet.length][3];
        
        ninjaKunai = new NinjaKunai[20];
        
        //Start all ninja threads
        ninjaAnimation = new NinjaAnimation();
        ninjaAnimation.start();
        
        InitializeSpriteAdjust();
        JumpForceReset();
        
        soul.x = 100;
        
        //Wait some threads be created for do not have 'NullPointerExeption'
        while (true) {
            if (ninjaAnimationOk && ninjaKeyboardOk) {
                break;
            }

            try { //If do not have this 'sleep' the 'while' maybe never will exit
                sleep(100);
            } catch (InterruptedException ex) {
                JOptionPane.showMessageDialog(null, "Maybe the game crash in loading because: " + ex.getMessage());
            }
        }
        
        playing.ninjaOk = true;
        
        someMethods.WaitAllThreadsFinish();
        
        //Wait the 'PlayingWindow' thread has run one time for the ninja initial position do not
        //bug, because initial position of the ninja is set using how reference the first floor
        while (true) {
            if (playing.GetPlayingWindow().positionY < 0) {
                break;
            }
            
            try { //If do not have this 'sleep' the 'while' maybe never will exit
                sleep(100);
            } catch (InterruptedException ex) {
                JOptionPane.showMessageDialog(null, "Maybe the game crash in loading because: " + ex.getMessage());
            }
        }
        
        y = (floor.GetFirstFloorY());
        
        int temp = 0;
        
        while (playing.GetIsPlaying()) {
            
            someMethods.PauseTheGame();
            
            for (line = 0; line < Main.Main.enemy.length; line ++) {
                if ((Main.Main.enemy[line] != null && Main.Main.enemy[line].isOk) &&
                     Main.Main.enemy[line].spriteSheetEnable == 0 &&
                     soul.collided(Main.Main.enemy[line].spriteSheet[Main.Main.enemy[line].spriteSheetEnable])) {
                    playing.isPlaying = false;
                    break;

                }
            }
                
            
            if (temp > (150 - playing.GetSpeed() * 0.96)) {
                meters++;
                temp = 0;
            }
            temp ++;
            
            //Makes the ninja fall faster or slower in some cases
            if (!ninjaKeyboard.keyUpIsDown && !onFloor && !ninjaKeyboard.isGliding) { //Faster
                jumpForce = 4;
            } else if (ninjaKeyboard.isGliding && ninjaKeyboard.keySpaceIsDown) { //Slower
                jumpForce = 0.4;
            } else if (ninjaKeyboard.isGliding && !ninjaKeyboard.keySpaceIsDown) { //Faster
                jumpForce = 4;
            }
            
            //Set the floor to sprite and makes it jump
            ninjaFloor = floor.GetFirstFloorY();
            jumpApplyForce(jumpForce);
            soul.y = y - soul.height;
            
            if (onFloor && y < ninjaFloor + playing.GetPlayingWindow().camera.y) {
                velocityY = 0;
                onFloor = false;
            }
            
            //Put the sprite sheet over the soul
            if (!ninjaKeyboard.changeAnimation) {
                spriteSheet[spriteSheetEnable].x = soul.x + spriteAdjustX[spriteSheetEnable][spriteSheet[spriteSheetEnable].getInitialFrame() / 10];
                spriteSheet[spriteSheetEnable].y = soul.y + spriteAdjustY[spriteSheetEnable][spriteSheet[spriteSheetEnable].getInitialFrame() / 10];
            }
            
            //This is how long the ninja can slide
            if (ninjaKeyboard.isSliding) {
                if (slideTime > SLIDE_TIME){
                    ninjaKeyboard.isSliding = false;
                    ninjaKeyboard.isRunning = true;
                    slideTime = 0;
                    ninjaKeyboard.changeAnimation = true;
                } else {
                    slideTime ++;
                }
   
            }
            
            //This sleep is equals for all threads
            try {
                sleep(allThreadSleep);
            } catch (InterruptedException ex) {
                JOptionPane.showMessageDialog(null ,"Maybe the game crash because: " + ex.getMessage());
            }
            
        }
        
        itOver = true;
        
        someMethods.WaitSomeThreadOver();
        
    }
    
    /*----  Classe methods  ----*/
    /**
     * Makes the ninja jump, but when it passes from the middle of the windows, the scene needs
     * move down.
     * 
     * @param force 
     */
    private void jumpApplyForce(double force) {
        if (isJumping || (!isJumping && !onFloor)) {
            velocityY += (GRAVITY * force);
            
            if (y + (soul.height / 4) - playing.GetPlayingWindow().camera.y > playing.GetPlayingWindow().camera.height / 2) {
                playing.GetPlayingWindow().camera.y = 0;
                y += velocityY;
            } else {
                y = (playing.GetPlayingWindow().camera.height / 2) - (soul.height / 4);
                playing.GetPlayingWindow().ApplyForceOnCamera(velocityY);
            }
            
            if (velocityY > 0 && y > ninjaFloor + playing.GetPlayingWindow().camera.y) {
                onFloor = true;
                isJumping = false;
                y = ninjaFloor + playing.GetPlayingWindow().camera.y;
            }
        }
    }
    
    public void SetIsJumping(boolean value) {
        if (isJumping != value) {
            isJumping = value;
            onFloor = false;
            velocityY = -JUMP_FORCE;
        }
    }
    
    public void JumpForceReset() {
        jumpForce = 1;
    }
    
    private void InitializeSpriteAdjust() {
        //Running
        spriteAdjustX[0][0] = -45; //x
        spriteAdjustY[0][0] = -30; //y
        //Running tattack
        spriteAdjustX[0][1] = -45;
        spriteAdjustY[0][1] = -40;
        //Running hrow
        spriteAdjustX[0][2] = -20;
        spriteAdjustY[0][2] = -30;
        //Jumping
        spriteAdjustX[1][0] = -45;
        spriteAdjustY[1][0] = -30;
        //Jumping attack
        spriteAdjustX[1][1] = -25;
        spriteAdjustY[1][1] = -23;
        //Jumping throw
        spriteAdjustX[1][2] = -47;
        spriteAdjustY[1][2] = -35;
        //Slide
        spriteAdjustX[2][0] = -45;
        spriteAdjustY[2][0] = -13;
        //Glide
        spriteAdjustX[2][1] = -40;
        spriteAdjustY[2][1] = -30;
    }
    
    public void StartNinjaKeyboard() {
        ninjaKeyboard = new NinjaKeyboard();
        ninjaKeyboard.start();
    }
    
    public void SetNinjaAnimationOk(boolean value) {
        ninjaAnimationOk = value;
    }
    
    public void SetNinjaKeyboardOk(boolean value) {
        ninjaKeyboardOk = value;
    }
    
    public boolean GetIsJumping() {
        return isJumping;
    }
    
    public boolean GetOnFloor() {
        return onFloor;
    }
    
    public NinjaAnimation GetNinjaAnimation() {
        return ninjaAnimation;
    }
    
    public NinjaKeyboard GetNinjaKeyboard() {
        return ninjaKeyboard;
    }
    
    /**
     * Returns all vector.
     * 
     * @return 
     */
    public NinjaKunai[] GetNinjaKunai() {
        return ninjaKunai;
    }
    
    /**
     * Returns one position of the vector.
     * 
     * @param index
     * @return 
     */
    public NinjaKunai GetNinjaKunai(int index) {
        return ninjaKunai[index];
    }
    
    public void ThrowKunai() {
        for (int i = 0; i < ninjaKunai.length; i ++) {
            if (ninjaKunai[i] == null || ninjaKunai[i].getState() == State.TERMINATED) {
                ninjaKunai[i] = new NinjaKunai();
                ninjaKunai[i].start();
                ninjaKunai[i].SetInitialPosition(soul.x + 30, soul.y + 53 - playing.GetPlayingWindow().camera.y);
                break;
            }
        }
    }
    
    public void DrawKunai() {
        for (NinjaKunai index : ninjaKunai) {
            if (index != null && index.getState() == State.TIMED_WAITING) {
                index.DrawKunai();
            }
        }
    }
    
}

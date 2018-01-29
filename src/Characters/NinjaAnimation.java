
package Characters;

//Variables imports
import static Main.DeltaTime.allThreadSleep;
import static Main.Main.ninja;
import static Main.Main.playing;

//Others imports
import static java.lang.Thread.sleep;
import javax.swing.JOptionPane;

/**
 *
 * @author Rodrigo Fernando da Silva
 */
public class NinjaAnimation extends Thread {
    
    public boolean isFalling;
    /**
     *This variable is used to know if the ninja was jumping, because after finish the animation
     *'jumpAttack' or 'jumpThrow' need set the sprite frame to last frame.
     */
    public boolean wasJumping;
    
    @Override
    @SuppressWarnings("SleepWhileInLoop")
    public void run() {
        
        isFalling = false;
        wasJumping = false;
        
        ninja.StartNinjaKeyboard();
        
        ninja.spriteSheetEnable = -1;
        SetCorrectAnimation();
        
        ninja.SetNinjaAnimationOk(true);
        
        while (playing.GetIsPlaying()) {
            
            if (ninja.GetNinjaKeyboard().changeAnimation) {
                SetCorrectAnimation();
            }
            
            //Set the animation speed to sprite sheet that is enable
            ninja.animationSpeed = (int) (4000 - ((playing.GetSpeed() * 40) * 0.55));
            
            ninja.spriteSheet[ninja.spriteSheetEnable].setTotalDuration(ninja.animationSpeed);
            
            //This 'ifs' is used to set the animation to 'running' after the sprite collision with the floor
            if (!isFalling & ninja.velocityY > 0) {
                isFalling = true;
            } else if(isFalling & !ninja.GetIsJumping()) {
                //Set the animation to attack if the ninja collision with the floor during your attack animation
                if (ninja.GetNinjaKeyboard().isJumpAttack | ninja.GetNinjaKeyboard().isJumpThrow) {
                    SetCorrectAnimationTransition();
                    isFalling = false;
                } else if (ninja.GetNinjaKeyboard().isJumping) {
                    ninja.GetNinjaKeyboard().isJumping = false;
                    ninja.GetNinjaKeyboard().isRunning = true;
                    isFalling = false;
                    SetCorrectAnimation();
                } else if (ninja.GetNinjaKeyboard().isGliding) {
                    ninja.GetNinjaKeyboard().isGliding = false;
                    ninja.GetNinjaKeyboard().isRunning = true;
                    isFalling = false;
                    SetCorrectAnimation();
                }
            } else if (isFalling & ninja.soul.getVelocityY() == 0) {
                isFalling = false;
            }
            
            //Sets animation to running if the ninja collided with the floor and to do not stops gliding
            if (ninja.GetNinjaKeyboard().isGliding & !ninja.GetIsJumping()) {
                ninja.GetNinjaKeyboard().isGliding = false;
                ninja.GetNinjaKeyboard().isRunning = true;
                SetCorrectAnimation();
            }
            
            //Set the animation to 'running' after the finish the 'attack' or 'throw' animation
            if ((ninja.GetNinjaKeyboard().isJumpAttack  | ninja.GetNinjaKeyboard().isJumpThrow |
                 ninja.GetNinjaKeyboard().isRunAttack   | ninja.GetNinjaKeyboard().isRunThrow)) {
                if (!ninja.GetNinjaAnimation().CanUpdateSprite()) {
                    ninja.GetNinjaKeyboard().isAnyAttack = false;
                    ninja.GetNinjaKeyboard().SetFalseBoolean();
                    if (ninja.GetIsJumping()) {
                        ninja.GetNinjaKeyboard().isJumping = true;
                        wasJumping = true;
                    } else if (!ninja.GetIsJumping()) {
                        ninja.GetNinjaKeyboard().isRunning = true;
                    }
                    SetCorrectAnimation();
                }
            }
            
            if (ninja.GetNinjaKeyboard().animationTransition) {
                SetCorrectAnimationTransition();
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
     * Make the attack transition animation, if the ninja is jump and attacking, if he
     * collision with the floor, the running animation attack have to be call, but have to call
     * it and set the same frame that it was.
     */
    private void SetCorrectAnimationTransition() {
        int initialFrame = 0;
        int finalFrame = 10;
        
        if (ninja.GetNinjaKeyboard().isRunAttack) {
            initialFrame = ninja.spriteSheet[ninja.spriteSheetEnable].getCurrFrame() - 10;
            finalFrame = 20;
            ninja.spriteSheetEnable = 1;
            ninja.GetNinjaKeyboard().isRunAttack = false;
            ninja.GetNinjaKeyboard().isJumpAttack = true;
        } else if (ninja.GetNinjaKeyboard().isRunThrow) {
            initialFrame = ninja.spriteSheet[ninja.spriteSheetEnable].getCurrFrame() + 10;
            finalFrame = 30;
            ninja.spriteSheetEnable = 1;
            ninja.GetNinjaKeyboard().isRunThrow = false;
            ninja.GetNinjaKeyboard().isJumpThrow = true;
        }  else if (ninja.GetNinjaKeyboard().isJumpAttack) {
            initialFrame = ninja.spriteSheet[ninja.spriteSheetEnable].getCurrFrame() + 10;
            finalFrame = 30;
            ninja.spriteSheetEnable = 0;
            ninja.GetNinjaKeyboard().isJumpAttack = false;
            ninja.GetNinjaKeyboard().isRunAttack = true;
        }   else if (ninja.GetNinjaKeyboard().isJumpThrow) {
            initialFrame = ninja.spriteSheet[ninja.spriteSheetEnable].getCurrFrame() - 10;
            finalFrame = 20;
            ninja.spriteSheetEnable = 0;
            ninja.GetNinjaKeyboard().isJumpThrow = false;
            ninja.GetNinjaKeyboard().isRunThrow = true;
        } 
        
        RepositionSpriteSheet(initialFrame, finalFrame);
        
        ninja.GetNinjaKeyboard().animationTransition = false;
        ninja.GetNinjaKeyboard().changeAnimation = false;
        
    }
    
    /**
     * Changes the animation that the ninja is playing using how reference
     * the 'NinjaKeyboard' variables.
     */
    private void SetCorrectAnimation() {
        int initialFrame = 0;
        int finalFrame = 10;
        
        if (ninja.GetNinjaKeyboard().isRunning) {
            ninja.spriteSheetEnable = 0;
            ninja.JumpForceReset();
        } else if (ninja.GetNinjaKeyboard().isRunThrow) {
            ninja.spriteSheetEnable = 0;
            initialFrame = 10;
            finalFrame = 20;
        }  else if (ninja.GetNinjaKeyboard().isRunAttack) {
            ninja.spriteSheetEnable = 0;
            initialFrame = 20;
            finalFrame = 30;
        } else if (ninja.GetNinjaKeyboard().isJumping) {
            ninja.spriteSheetEnable = 1;
        } else if (ninja.GetNinjaKeyboard().isJumpThrow) {
            ninja.spriteSheetEnable = 1;
            initialFrame = 20;
            finalFrame = 30;
        } else if (ninja.GetNinjaKeyboard().isJumpAttack) {
            ninja.spriteSheetEnable = 1;
            initialFrame = 10;
            finalFrame = 20;
        } else if (ninja.GetNinjaKeyboard().isSliding) {
            ninja.spriteSheetEnable = 2;
        } else if (ninja.GetNinjaKeyboard().isGliding) {
            ninja.spriteSheetEnable = 2;
            initialFrame = 10;
            finalFrame = 20;
        }
        
        RepositionSpriteSheet(initialFrame, finalFrame);
        
        ninja.GetNinjaKeyboard().changeAnimation = false;
        
    }
    
    private void RepositionSpriteSheet(int initialFrame, int finalFrame) {
        ninja.spriteSheet[ninja.spriteSheetEnable].setSequence(initialFrame, finalFrame, true);
        
        //After the change the sprite animation, its position is zeroed, so is necessary
        //reposition it
        ninja.spriteSheet[ninja.spriteSheetEnable].x = ninja.soul.x + ninja.spriteAdjustX[ninja.spriteSheetEnable][ninja.spriteSheet[ninja.spriteSheetEnable].getInitialFrame() / 10];
        ninja.spriteSheet[ninja.spriteSheetEnable].y = ninja.soul.y + ninja.spriteAdjustY[ninja.spriteSheetEnable][ninja.spriteSheet[ninja.spriteSheetEnable].getInitialFrame() / 10];
    }
    
    public boolean CanUpdateSprite() {
        if (wasJumping) {
            ninja.spriteSheet[ninja.spriteSheetEnable].setCurrFrame(ninja.spriteSheet[ninja.spriteSheetEnable].getFinalFrame() - 1);
            wasJumping = false;
            return false;
        } else if (!ninja.GetNinjaKeyboard().isRunning & !ninja.GetNinjaKeyboard().isGliding & !ninja.GetNinjaKeyboard().isSliding) {
            if (ninja.spriteSheet[ninja.spriteSheetEnable].getCurrFrame() >= ninja.spriteSheet[ninja.spriteSheetEnable].getFinalFrame() - 1) {
                return false;
            }
        }
        return true;
    }
    
}

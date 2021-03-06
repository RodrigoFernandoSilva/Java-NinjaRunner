
package Main;

//Jplay imports
import jplay.Animation;
import jplay.GameImage;
import jplay.Window;

//Classes imports
import Characters.CoinThread;
import Characters.Enemy;
import Characters.NinjaKunai;
import Characters.KunaiThead;
import Characters.TiledMapToSlide;

//Variables impors
import static Main.Main.coinThread;
import static Main.Main.enemy;
import static Main.Main.kunaiThead;
import static Main.Main.ninja;
import static Main.Main.playing;
import static Main.Main.tiledMapToSlide;

//Others imports
import static java.lang.Thread.sleep;
import javax.swing.JOptionPane;

/**
 *
 * @author Rodrigo Fernando da Silva
 */
public class SomeMethods {
    
    private final Animation NUMBERS = new Animation("Images/Playing/HUD/Numbers/Numbers.png",11);
    private final Animation NUMBERS_BIG = new Animation("Images/Playing/HUD/Numbers/NumbersBig.png",11);
    
    public void DrawNumbersOnMiddle(int value, GameImage imageBase, double adjustX, double adjustY) {
        String valueStr = String.valueOf(value);
        
        NUMBERS.x = imageBase.x + (imageBase.width / 2);
        NUMBERS.x -= ((16 * valueStr.length()) / 2);
        NUMBERS.y = imageBase.y + (imageBase.height / 2);
        NUMBERS.y -= NUMBERS.height /2;
        
        NUMBERS.x += adjustX;
        NUMBERS.y += adjustY;
        
        for (int i = 0; i < valueStr.length(); i ++) {
            NUMBERS.setCurrFrame((((int) valueStr.charAt(i)) - 48));
            NUMBERS.draw();
            NUMBERS.x += 16;
        }
    }
    
    public void DrawBigNumbersOnMiddle(int value, GameImage imageBase, double adjustX, double adjustY) {
        String valueStr = String.valueOf(value);
        
        NUMBERS_BIG.x = imageBase.x + (imageBase.width / 2);
        NUMBERS_BIG.x -= ((31 * valueStr.length()) / 2);
        NUMBERS_BIG.y = imageBase.y + (imageBase.height / 2);
        NUMBERS_BIG.y -= NUMBERS_BIG.height /2;
        
        NUMBERS_BIG.x += adjustX;
        NUMBERS_BIG.y += adjustY;
        
        for (int i = 0; i < valueStr.length(); i ++) {
            NUMBERS_BIG.setCurrFrame((((int) valueStr.charAt(i)) - 48));
            NUMBERS_BIG.draw();
            NUMBERS_BIG.x += 31;
        }
    }
    
    public boolean ExitWindow(Animation image, double addition) {
        return (image.x + image.width + addition) < 0;
    }
    
    public boolean ExitWindow(GameImage image, double addition) {
        return (image.x + image.width + addition) < 0;
    }
    
    /**
     * Return the if the image that was passed is on the scene, or inside the camera.
     * 
     * @param imagem
     * @param window
     * @return 
     */
    public boolean IsOnScene(Animation imagem, Window window) {
        return (imagem.x < window.getWidth() && (imagem.x + imagem.width) > 0);
    }
    
    /**
     * Return the if the image that was passed is on the scene, or inside the camera.
     * 
     * @param imagem
     * @param window
     * @return 
     */
    public boolean IsOnScene(GameImage imagem, Window window) {
        return (imagem.x < window.getWidth() && (imagem.x + imagem.width) > 0);
    }
    
    public double SetPositionBelowWindow(Animation image, Window window) {
        return window.getHeight() - image.height;
    }
    
    public double SetPositionBelowWindow(GameImage image, Window window) {
        return window.getHeight() - image.height;
    }
    
    /**
     * Distributes the 'GameImages' on the middle of 'Animation'. 
     * 
     * @param imageToPut
     * @param imageBase
     * @param additionLenght
     * @param numberObject
     * @param part
     * @return double
     */
    public double PutImageOnMiddle (GameImage imageToPut, GameImage imageBase, int additionLenght, int numberObject, int part) {
        double value;
        double lenght = ((imageBase.width + additionLenght) / numberObject);
        
        value = imageBase.x + (lenght / 2);
        value += lenght * part;
        value -= (imageToPut.width / 2);
        
        return value;
    }
    
    public void WaitAllThreadsFinish() {
        //Wait some threads be created for do not have 'NullPointerExeption'
        while (true) {
            if (playing.allThreadsOk) {
                break;
            }
            
            try { //If do not have this 'sleep' the 'while' maybe never will exit
                sleep(100);
            } catch (InterruptedException ex) {
                JOptionPane.showMessageDialog(null, "Maybe the game crash in loading because: " + ex.getMessage());
            }
        }
    }
    
    /**
     * Pause the game.
     */
    public void PauseTheGame() {
        while (playing.isPaused) {
            try {
                sleep(1);
            } catch (InterruptedException ex) {
                JOptionPane.showMessageDialog(null, "Maybe the game crash because: " + ex.getMessage());
            }
        }
    }
    
    /**
     * After the game over, need wait some threads over to do not have 'NullPointerExeption'.
     */
    public void WaitSomeThreadOver() {
        boolean allEnemyOver;
        boolean allCoinThreadOver;
        boolean allKunaiThreadOver;
        boolean allKunai;
        boolean allTiledMapToSlideOver;
        
        while (true) {
            
            allEnemyOver = true;
            for (Enemy index : enemy) {
                if (index != null && !index.itOver) {
                    allEnemyOver = false;
                    break;
                }
            }
            
            allCoinThreadOver = true;
            for (CoinThread index : coinThread) {
                if (index != null && !index.itOver) {
                    allCoinThreadOver = false;
                    break;
                }
            }
            
            allKunaiThreadOver = true;
            for (KunaiThead index : kunaiThead) {
                if (index != null && !index.itOver) {
                    allKunaiThreadOver = false;
                    break;
                }
            }
            
            allKunai = true;
            for (NinjaKunai index : ninja.ninjaKunai) {
                if (index != null && !index.itOver) {
                    allKunai = false;
                    break;
                }
            }
            
            allTiledMapToSlideOver = true;
            for (TiledMapToSlide index : tiledMapToSlide) {
                if (index != null && !index.itOver) {
                    allTiledMapToSlideOver = false;
                    break;
                }
            }
            
            if ((allEnemyOver && allCoinThreadOver && allKunaiThreadOver) && allTiledMapToSlideOver &&
                 playing.GetPlayingDraw().itOver && playing.GetPlayingUpdate().itOver &&
                 playing.GetPlayingWindow().itOver && ninja.itOver) {
                break;
            }
            
            try {
                sleep(1);
            } catch (InterruptedException ex) {
                JOptionPane.showMessageDialog(null, "Maybe the game crash because: " + ex.getMessage());
            }
        }
    }
}

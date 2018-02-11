
package Menus;

//Jplay imports
import jplay.GameImage;
import jplay.Keyboard;

//Variables imports
import static Main.Main.keyboard;
import static Main.Main.mouse;
import static Main.Main.window;

/**
 *
 * @author Rodrigo Fernando da Silva
 */
public class Menu {
    
    //Java variables
    private int line;
    private final String FILE_WAY = "Images/Menu/";
    
    //Jplay variables
    private final GameImage menu = new GameImage(FILE_WAY + "/Menu.png");
    private final GameImage jogar = new GameImage(FILE_WAY + "Buttons/Jogar.png");
    private final GameImage tutorial = new GameImage(FILE_WAY + "Buttons/Tutorial.png");
    private final GameImage sair = new GameImage(FILE_WAY + "Buttons/Sair.png");
    private final GameImage ninja = new GameImage(FILE_WAY + "/Ninja.png");
    private final GameImage enemy = new GameImage(FILE_WAY + "/enemy.png");
    private final GameImage background = new GameImage(FILE_WAY + "/Background.png");
    
    private final GameImage[] tutorialImages = new GameImage[7];
    private final GameImage tutorialBc = new GameImage(FILE_WAY + "Tutorial/Tutorial_BC.png");
    private final GameImage anterios = new GameImage(FILE_WAY + "Buttons/Anterios.png");
    private final GameImage proximo = new GameImage(FILE_WAY + "Buttons/Proximo.png");
    private final GameImage finalizar = new GameImage(FILE_WAY + "Buttons/Finalizar.png");
    
    public void Menu() {
        
        boolean isMenu = true;
        int distance = 20;
        
        for (line = 0; line < tutorialImages.length; line ++) {
            tutorialImages[line] = new GameImage(FILE_WAY + "Tutorial/Tutorial_" + String.valueOf(line + 1) + ".png");
            tutorialImages[line].width = window.getWidth();
            tutorialImages[line].height = (tutorialImages[line].width * 896) / 1732;
            tutorialImages[line].y = (window.getHeight() - tutorialImages[line].height) / 2;
            
        }
        tutorialBc.width = window.getWidth();
        tutorialBc.height = window.getHeight();
        
        menu.x = ((window.getWidth() - menu.width) / 2);
        menu.y = ((window.getHeight()- menu.height) / 2);
        enemy.x = menu.x + menu.width - 140;
        enemy.y = menu.y + menu.height - enemy.height + 30;
        ninja.x = menu.x - ninja.width + 80;
        ninja.y = menu.y + menu.height - ninja.height - 5;
        
        jogar.x = menu.x + ((menu.width - jogar.width) / 2);
        jogar.y = menu.y + distance + 30;
        tutorial.x = jogar.x;
        tutorial.y = jogar.y + jogar.height + distance;
        sair.x = jogar.x;
        sair.y = tutorial.y + tutorial.height + distance;
        
        anterios.x = 10;
        anterios.y = window.getHeight() - proximo.height - 10;
        proximo.x = window.getWidth()- proximo.width - 10;
        proximo.y = window.getHeight() - proximo.height - 10;
        finalizar.x = window.getWidth()- proximo.width - 10;
        finalizar.y = window.getHeight() - proximo.height - 10;
        
        while (isMenu) {
            
            background.draw();
            
            menu.draw();
            ninja.draw();
            enemy.draw();
            
            jogar.draw();
            tutorial.draw();
            sair.draw();
            
            window.update();
            
            if (mouse.isLeftButtonPressed()) {
                if (mouse.isOverObject(jogar)) {
                    isMenu = false;
                } else if (mouse.isOverObject(tutorial)) {
                    Tutorial();
                } else if(mouse.isOverObject(sair)) {
                    window.exit();
                }
            }
            
            if (keyboard.keyDown(Keyboard.ESCAPE_KEY)) {
                window.exit();
            }
            
        }
        
    }
    
    private void Tutorial() {
        int index = 0;
        
        while (index < 7) {
            
            tutorialBc.draw();
            tutorialImages[index].draw();
            
            anterios.draw();
            if (index == 6) {
                finalizar.draw();
            } else {
                proximo.draw();
            }
            
            if (keyboard.keyDown(Keyboard.ESCAPE_KEY)) {
                break;
            }
            
            if (mouse.isLeftButtonPressed()) {
                if (mouse.isOverObject(anterios) && index > 0) {
                    index--;
                } else if (mouse.isOverObject(proximo) && index < 7) {
                    index ++;
                }
            }
            
            window.update();
            
        }
        
    }
}

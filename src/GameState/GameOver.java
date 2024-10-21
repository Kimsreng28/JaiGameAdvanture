package GameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;

import Audio.AudioPlayer;

public class GameOver extends GameState {

    private String[] options = {"Retry", "Main Menu"};
    private int currentChoice = 0;
    private Image backgroundImage;
    

    public GameOver(GameStateManager gsm) {
        this.gsm = gsm;
        backgroundImage = new ImageIcon(getClass().getResource("/Backgrounds/go.gif")).getImage();
        AudioPlayer.stop("background_2");
        AudioPlayer.load("/Audio/lose.wav", "lose");
        AudioPlayer.loop ("lose",600,AudioPlayer.getFrames("lose")-2200);
    }

    public void init() {
        // Initialization logic if needed
    }

    public void update() {
        
    }

    public void draw(Graphics2D g) {
        g.drawImage(backgroundImage, 0, 0, null);
        // Drawing logic for the game over screen
        // Display game over message and options
        // g.setColor(Color.RED);
        // g.setFont(new Font("Monospaced", Font.BOLD, 20));
        // g.drawString("Game Over", 100, 100);

        // Display options
        for (int i = 0; i < options.length; i++) {
            if (i == currentChoice) {
                g.setColor(Color.BLACK);
            } else {
                g.setColor(Color.WHITE);
            }
            g.setFont(new Font("Monospaced", Font.PLAIN, 20));
            g.drawString(options[i], 230, 179 + i * 60);
        }
    }

    public void keyPressed(int k) {
        if (k == KeyEvent.VK_ENTER) {
            selectOption();
        }
        if (k == KeyEvent.VK_UP) {
            AudioPlayer.play("clickbutton",0);
            currentChoice--;
            if (currentChoice < 0) {
                currentChoice = options.length - 1;
            }
        }
        if (k == KeyEvent.VK_DOWN) {
            AudioPlayer.play("clickbutton",0);
            currentChoice++;
            if (currentChoice >= options.length) {
                currentChoice = 0;
            }
        }
    }

    private void selectOption() {
        if (currentChoice == 0) {
            gsm.setState(GameStateManager.LEVEL2STATE); // Restart the game
        } else if (currentChoice == 1) {
            gsm.setState(GameStateManager.MENUSTATE); // Go back to the main menu
        }
    }

    public void keyReleased(int k) {
        // Release logic if needed
    }
}
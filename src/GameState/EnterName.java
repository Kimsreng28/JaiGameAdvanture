package GameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
//import TileMap.Background; 
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;

import Audio.AudioPlayer;

public class EnterName extends GameState {
    //private Background bg;
    private Image backgroundImage;

    private String playerName = "";
    private Font font;
    private boolean showBox; // Flag to show/hide input box
	private boolean shiftPressed;
    

    public EnterName(GameStateManager gsm) {
        this.gsm = gsm;
        try {
            backgroundImage = new ImageIcon(getClass().getResource("/Backgrounds/bg5.gif")).getImage();
            font = new Font("Monospaced", Font.PLAIN, 16);
            showBox = true;
			shiftPressed = false;
        } catch (Exception e) {
            e.printStackTrace();
        }

        
    }

    @Override
    public void init() {}

    public void update() {
        //bg.update();
    }

    public void draw(Graphics2D g) {
        g.drawImage(backgroundImage, 0, 0, null);
        g.setColor(Color.WHITE);
        g.setFont(font);
        g.drawString("Welcome!!", 100, 50);
        g.drawString("StartSoon!!", 100, 70);
        g.drawString("Enter Your Name:", 100, 100);
        g.drawString(">>>Enter", 430, 100);
        g.drawString("Version 1.1.1", 400, 290);
        g.drawString("Press 'B' to go back to the menu.", 10, 290);

        if (showBox) {
            int textBoxX = 270; // Adjust X position
            int textBoxY = 80; // Adjust Y position
            int textBoxWidth = 150; // Adjust width
            int textBoxHeight = 30; // Adjust height

            g.drawRect(textBoxX, textBoxY, textBoxWidth, textBoxHeight); // Draw input box outline

            int fontSize = calculateFontSize(g, playerName, textBoxWidth);
            if (fontSize < 12) {
                fontSize = 12;
            }
            //
            Font adjustedFont = new Font("Monospaced", Font.PLAIN, fontSize);
            g.setFont(adjustedFont);

            g.drawString(playerName, textBoxX + 10, textBoxY + textBoxHeight - 10); // Display entered text inside the box
        }
    }

    private int calculateFontSize(Graphics g, String text, int boxWidth) {
        int fontSize = 16; // Initial font size
        FontMetrics fontMetrics = g.getFontMetrics(new Font("Monospaced", Font.PLAIN, fontSize));
        while (fontMetrics.stringWidth(text) > boxWidth - 16) {
            fontSize--;
            fontMetrics = g.getFontMetrics(new Font("Monospaced", Font.PLAIN, fontSize));
            if (fontSize < 12) {
                break;
            }
        }
        return fontSize;
    }

    public void keyPressed(int k) {
        
        if (k == KeyEvent.VK_SHIFT) {
            //new neath
            AudioPlayer.play("clickbutton",0);
            shiftPressed = true;
        } else if (Character.isLetterOrDigit(k)) {
            char keyChar = (char) k;
            if (Character.isLetter(keyChar)) {
                if (shiftPressed) {
                    //new neath
                    AudioPlayer.play("clickbutton",0);
                    playerName += Character.toUpperCase(keyChar);
                    shiftPressed = false; // Reset Shift key status
                } else {
                    //new neath
                    AudioPlayer.play("clickbutton",0);
                    playerName += Character.toLowerCase(keyChar);
                }
            } else {
                //new neath
                AudioPlayer.play("clickbutton",0);
                playerName += keyChar;
            }
        } else if (k == KeyEvent.VK_BACK_SPACE && playerName.length() > 0) {
            //neww neath
            AudioPlayer.play("clickbutton",0);
            playerName = playerName.substring(0, playerName.length() - 1);
        } else if (k == KeyEvent.VK_SPACE) {
            //new neath
            AudioPlayer.play("clickbutton",0);
            playerName += " ";
        } 
        if (k == KeyEvent.VK_ENTER && playerName.length() > 0) {
            AudioPlayer.play("clickbutton",0);
            GameStateManager.setPlayerName(playerName);
            gsm.setState(GameStateManager.LEVEL2STATE);
        }
        if (k == KeyEvent.VK_B) {
            gsm.setState(GameStateManager.MENUSTATE); // Go back to the menu state
        }
    }

    public void keyReleased(int k) {
        if (k == KeyEvent.VK_SHIFT) {
            shiftPressed = false;
        }
    }
}
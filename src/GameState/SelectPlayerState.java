package GameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import TileMap.Background;


public class SelectPlayerState extends GameState {

    private Background bg;
    private String[] playerOptions = {
            "Player 1",
            "Player 2"
    };

    private int currentPlayerChoice = 0;
    private Color titleColor;
    private Font titleFont;
    private Font font;
    

    public SelectPlayerState(GameStateManager gsm) {
        this.gsm = gsm;

        try {
            bg = new Background("/Backgrounds/pic1.jpg", 1);
            bg.setVector(-0.1, 0);

            titleColor = new Color(255, 171, 187);
            titleFont = new Font("TimesRoman", Font.PLAIN, 40);

            font = new Font("Monospaced", Font.PLAIN, 20);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void init() {
    }

    public void update() {
        bg.update();
    }

    public void draw(Graphics2D g) {
        // draw background
        bg.draw(g);
        // draw title
        g.setColor(titleColor);
        g.setFont(titleFont);
        g.drawString("Select Player", 80, 70);
        g.drawString("Select Player", 82, 72);

        // draw player options
        g.setFont(font);
        for (int i = 0; i < playerOptions.length; i++) {
            if (i == currentPlayerChoice) {
                g.setColor(Color.PINK);
            } else {
                g.setColor(Color.WHITE);
            }
            g.drawString(playerOptions[i], 150, 140 + i * 30);
        }
    }

    private void selectPlayer() {
        switch (currentPlayerChoice) {
            case 0:
                GameStateManager.setLevelPlayerName("Player 1");
                gsm.setState(GameStateManager.LEVEL1STATE);
                break;
            case 1:
                GameStateManager.setLevelPlayerName("Player 2");
                gsm.setState(GameStateManager.LEVEL2STATE);
                break;
            default:
                break;
        }
    }

    public void keyPressed(int k) {
        if (k == KeyEvent.VK_ENTER) {
            selectPlayer();
        }
        if (k == KeyEvent.VK_UP) {
            currentPlayerChoice--;
            if (currentPlayerChoice == -1) {
                currentPlayerChoice = playerOptions.length - 1;
            }
        }
        if (k == KeyEvent.VK_DOWN) {
            currentPlayerChoice++;
            if (currentPlayerChoice == playerOptions.length) {
                currentPlayerChoice = 0;
            }
        }
    }

    public void keyReleased(int k) {
    }
}
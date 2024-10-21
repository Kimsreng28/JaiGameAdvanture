package GameState;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;

//import TileMap.Background;
import javax.swing.ImageIcon;
public class ShowHelp extends GameState {

    //private Background bg;
    private Image backgroundImage;
    private Font font;

    public ShowHelp(GameStateManager gsm) {
        this.gsm = gsm;
        try {
            backgroundImage = new ImageIcon(getClass().getResource("/Backgrounds/bg3.gif")).getImage();
            font = new Font("Monospaced", Font.PLAIN, 16);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g) {
        // Draw background
        g.drawImage(backgroundImage, 0, 0, null);

        // Draw help text
        g.setColor(Color.BLACK);
        g.setFont(font);
        g.drawString("*Help*", 0, 20); 
        g.drawString("***Notes To Play***", 20, 50);
        g.drawString("Press 'LEFT' :: Go Back and 'RIGHT':: Go Forward ", 20, 70);
        g.drawString("Press 'W' :: Go To Jump and 'F':: Go Ice Fire ", 20, 90); 
        g.drawString("Press 'M' :: Go To Pause and 'ENTER':: Go To Start ", 20, 110);
        g.drawString("Thank You and ::: ENJOY :::", 100, 240); // Modify this line for your help text
        g.drawString("Press 'B' to go back to the menu.", 100, 260); // Prompt to go back to the menu
    }

    @Override
    public void init() {
        
    }

    @Override
    public void update() {
        //bg.update();
    }

    @Override
    public void keyPressed(int k) {
        if (k == KeyEvent.VK_B) {
            gsm.setState(GameStateManager.MENUSTATE); // Go back to the menu state
        }
    }

    @Override
    public void keyReleased(int k) {
       
    }
}

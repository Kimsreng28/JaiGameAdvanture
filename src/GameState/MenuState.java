package GameState;

// import TileMap.Background;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;

import Audio.AudioPlayer;

public class MenuState extends GameState {
	
	// private Background bg;
	private Image backgroundImage;
	//
	
	private int currentChoice = 0;
	private String[] options = {
		"Start",
		"Help",
		"Quit"
	};
	
	private Color titleColor;
	private Font titleFont;
	
	private Font font;
	
	public MenuState(GameStateManager gsm) {
		
		this.gsm = gsm;
		

		try {
			
			backgroundImage = new ImageIcon(getClass().getResource("/Backgrounds/bg2.gif")).getImage();
			
			titleColor = new Color(255, 171, 187);
			titleFont = new Font(
					"TimesRoman",
					Font.PLAIN,
					40);
			
			font = new Font("Monospaced", Font.PLAIN, 20);
        	
			//new audio menu
			AudioPlayer.load("/Audio/select.wav","select");
			AudioPlayer.load("/Audio/clickbutton.wav","clickbutton");
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		AudioPlayer.stop("background_2");
		AudioPlayer.stop("Level_1");
		AudioPlayer.stop("lose");
		AudioPlayer.load("/Audio/Menu.wav", "Menu");
        AudioPlayer.loop ("Menu",600,AudioPlayer.getFrames("Menu")-2200);
	}
	
	public void init() {}
	
	public void update() {
		// bg.update();
		
	}
	
	public void draw(Graphics2D g) {
		
		// draw bg
		g.drawImage(backgroundImage, 0, 0, null);
		
		// draw title
		g.setColor(titleColor);
		g.setFont(titleFont);
		g.drawString("Jai Adventure", 80, 70);
		g.drawString("Jai Adventure", 82, 72);
		
		// draw menu options
		g.setFont(font);
		for(int i = 0; i < options.length; i++) {
			if(i == currentChoice) {
				g.setColor(Color.PINK);
			}
			else {
				g.setColor(Color.WHITE);
			}
			g.drawString(options[i], 150, 140 + i * 30);
		}
		
		
	}
	
	private void select() {
		if(currentChoice == 0) {
			//new neath
			AudioPlayer.play("select");
			gsm.setState(GameStateManager.ENTERNAME);
			
		}
		if(currentChoice == 1) {
			//new neath
			AudioPlayer.play("select");
			// help
			gsm.setState(GameStateManager.HELPSTATE);
		}
		if(currentChoice == 2) {
			System.exit(0);
		}
	}
	
	public void keyPressed(int k) {
		if(k == KeyEvent.VK_ENTER){
			select();
		}
		if(k == KeyEvent.VK_UP) {
			AudioPlayer.play("clickbutton",0);
			currentChoice--;
			if(currentChoice == -1) {
				currentChoice = options.length - 1;
			}
		}	
		if(k == KeyEvent.VK_DOWN) {
			AudioPlayer.play("clickbutton",0);
			currentChoice++;
			if(currentChoice == options.length) {
				currentChoice = 0;
			}
		}
	}
	public void keyReleased(int k) {}
	
}
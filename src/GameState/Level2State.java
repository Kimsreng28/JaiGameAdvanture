package GameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import Audio.AudioPlayer;
import Enemies.Snail;
import Enemies.Superslay;
import Entity.Enemy;
import Entity.Explosion;
import Entity.HUD;
import Entity.Player;
import Main.GamePanel;
import TileMap.Background;
import TileMap.TileMap;

public class Level2State extends GameState {
	
	private TileMap tileMap;
	private Background  bgM ;
	private Image backgroundImage, bgW;
	private Player player;
	// new nita
	private HUD hud;
	// Name 
	// adjustedFont 
	private String playerName =  "";

	//Enemy 
	private ArrayList<Enemy> enemies;
	private ArrayList<Enemy> enemies1;
	private ArrayList<Explosion> explosions;

	//Show Menu
	private boolean showMenu; // Flag to display the menu
    private String[] options = { "> Pause", "> Retry","> Back Menu" };
    private int currentChoice = 0;

	//GameWin
	private boolean gameWon = false;
	private String[] options1 = { "> NextLevel" };
    private int currentChoice1 = 0;
	private int winX = 3130;

	public Level2State(GameStateManager gsm) {
		this.gsm = gsm;
		playerName = GameStateManager.getPlayerName();
		init(); //additional initialization tasks.
	}
	public void init() { 
		
		tileMap = new TileMap(30);
		tileMap.loadTiles("/Tilesets/grasstileset3.gif");
		tileMap.loadMap("/Maps/level1-2.map");
		tileMap.setPosition(0, 0);
		
		backgroundImage = new ImageIcon(getClass().getResource("/Backgrounds/G2.gif")).getImage();
    
		//background menu
		bgM = new Background("/Backgrounds/bg5.gif", 1);
		//backgrounnd win game
		bgW = new ImageIcon(getClass().getResource("/Backgrounds/gw.gif")).getImage();

		player = new Player(tileMap);
		player.setPosition(100, 110);
        hud = new HUD(player);
		//new nita
		explosions = new ArrayList<Explosion>();
		// new bupheng
		enemiesCreator();
		enemiesCreator1();
		//neath
		AudioPlayer.stop("Menu");
		AudioPlayer.stop("lose");
		AudioPlayer.load("/Audio/background_2.wav", "background_2");
		AudioPlayer.loop ("background_2",600,AudioPlayer.getFrames("background_2")-2200);
		AudioPlayer.load("/Audio/completion level.wav", "completion level");
	}
	//new 
	private void enemiesCreator(){
			enemies = new ArrayList<Enemy> ();
			Snail sn;
			Point[] points = new Point[] {
				new Point(270,100),
				new Point(420,200),
				new Point(720,10),
				new Point(810,10),
				new Point(840,10),
				new Point(990,100),
				new Point (1020,200),
				new Point(1050,200),
				new Point(1530,200),
				new Point(1500,200),
				new Point(1535,200),
				new Point (3000,100),
    		};
			for(int i=0; i<points.length; i++){
			sn = new Snail(tileMap,player);
			sn.setPosition(points[i].x, points[i].y);
			enemies.add(sn);
			}  
  	}

	private void enemiesCreator1(){
		enemies1 = new ArrayList<Enemy> ();
		Superslay ssn;
		Point[] points1 = new Point[] {
		  new Point(230,200),
		  new Point(840,100),
		  new Point(400,50),
		  new Point(1080,50),
		  new Point(1230,100),
		  new Point(1530,200),
		  new Point(1710,150),
		  new Point(1830,50),
		  new Point(2130,100),
		  new Point(2490,100),
		  new Point(2460,50),
		  new Point(2670,50),
		  new Point(3030,200),
		  new Point(3060,200),
		  new Point(3090,200)
	
		};
		for(int i=0; i<points1.length; i++){
		  ssn = new Superslay(tileMap,player);
		  ssn.setPosition(points1[i].x, points1[i].y);
		  enemies.add(ssn);
		}  
	}
	
	public void update() {
		// update player
		player.update();

		//bg.setPosition(tileMap.getx(),  tileMap.gety());
		
		tileMap.setPosition(
			GamePanel.WIDTH / 2 - player.getx(),
			GamePanel.HEIGHT / 2 - player.gety()
		);

		if (player.getHealth() == 0 || player.notOnScreen()) {
			gsm.setGameOverState();
		}

		//new 
		player.checkAttack(enemies);
		//update enemies bunpheng
		for(int i = 0; i < enemies.size(); i++) {
			Enemy e = enemies.get(i);
			e.update();
			if(e.isDead()) {
			  enemies.remove(i);
			  i--;
			  explosions.add(new Explosion(e.getx(), e.gety()));
			}
		  }
		  //update explosion nita
		  for(int i = 0;i<explosions.size();i++){
		  explosions.get(i).update();
		  if(explosions.get(i).shouldRemove()){
			explosions.remove(i);
			i--;
		  }
		}

		// Check win condition
		if (!gameWon && player.getx() > winX) {
			gameWon = true;
			AudioPlayer.stop("background_2");
            AudioPlayer.play("completion level");
		}
	
	}

	public void draw(Graphics2D g) {
		
		// draw bg
		g.drawImage(backgroundImage, 0, 0, null);
		
		// draw tilemap
		tileMap.draw(g);
		
		// draw player
		player.draw(g);

        hud.draw(g);
		
		g.setColor(Color.BLUE);
        g.setFont(new Font("Monospaced", Font.PLAIN, 12));
		g.drawString("Player Name: " + playerName, 5, 65); // Display player name
        
		//draw enemy pheng
		for(int i = 0; i<enemies.size(); i++){
			enemies.get(i).draw(g);
		}
		// explosion nita
		  for(int i = 0; i< explosions.size(); i++){
			explosions.get(i).setMapPosition((int)tileMap.getx(), (int) tileMap.gety() );
			explosions.get(i).draw(g);
		}

		if (showMenu) {
			drawOverlay(g);
            drawMenu(g); // Draw the menu if showMenu is true
        } else if (gameWon) {
            drawWinMessage(g);
        }
	}

	private void drawWinMessage(Graphics2D g) {
		g.drawImage(bgW, 0, 0, null);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Monospaced", Font.BOLD, 30));
        g.setFont(new Font("Monospaced", Font.PLAIN, 12));
    }

	private void drawOverlay(Graphics2D g) {
		bgM.draw(g);
        g.setColor(new Color(0, 0, 0, 150)); // Semi-transparent black color
        g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT); // Cover entire screen
		
    }

	//Draw Menu
	private void drawMenu(Graphics2D g) {
        int menuX = 220; // Adjust X position
        int menuY = 110; // Adjust Y position
        int spacing = 30; // Spacing between menu options

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 20));

        for (int i = 0; i < options.length; i++) {
            if (i == currentChoice) {
                g.setColor(Color.PINK);
            } else {
                g.setColor(Color.WHITE);
            }
            g.drawString(options[i], menuX, menuY + i * spacing);
        }
    }

	public void keyPressed(int k) {
		if(k == KeyEvent.VK_LEFT) player.setLeft(true);
		if(k == KeyEvent.VK_RIGHT) player.setRight(true);
		if(k == KeyEvent.VK_UP) player.setUp(true);
		if(k == KeyEvent.VK_DOWN) player.setDown(true);
		if(k == KeyEvent.VK_W) player.setJumping(true);
		if(k == KeyEvent.VK_F) player.setFiring();

		//show menu
		if (k == KeyEvent.VK_M) {
			
            showMenu = !showMenu; // Toggle menu visibility when 'M' is pressed
        }

		if (showMenu) {
            if (k == KeyEvent.VK_ENTER) {
                selectMenuOption();
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

		if(gameWon){
			if(k == KeyEvent.VK_ENTER){
				selectWin();
			}
		}
	}
	private void selectWin(){
		if (currentChoice1 == 0){
			gsm.setState(GameStateManager.LEVEL1STATE);
		}
	}

	
	private void selectMenuOption() {
        if (currentChoice == 0) {
            // Perform action for "Push"
			AudioPlayer.play("select");
			
        } else if (currentChoice == 1) {
			AudioPlayer.play("select");
            // Perform action for "Retry"
            gsm.setState(GameStateManager.LEVEL2STATE); // Restart the level or retry
         // Next to other level
        } else if (currentChoice == 2) {
			AudioPlayer.play("select");
            // Perform action for "Quit"
            gsm.setState(GameStateManager.MENUSTATE);
        }
        showMenu = false; // Hide menu after selecting an option
    }
	
	public void keyReleased(int k) {
		if(k == KeyEvent.VK_LEFT) player.setLeft(false);
		if(k == KeyEvent.VK_RIGHT) player.setRight(false);
		if(k == KeyEvent.VK_UP) player.setUp(false);
		if(k == KeyEvent.VK_DOWN) player.setDown(false);
		if(k == KeyEvent.VK_W) player.setJumping(false);

	}
}
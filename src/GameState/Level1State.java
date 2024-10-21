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
import Enemies.Snow;
import Enemies.Snowman;
import Enemies.Supersnow;
import Entity.Enemy;
import Entity.Explosion1;
import Entity.HUD;
import Entity.Player;
import Main.GamePanel;
import TileMap.Background;
import TileMap.TileMap;

public class Level1State extends GameState {
	
	private TileMap tileMap;
	private Background  bgM ;
	private Player player;
	// new nita
	private HUD hud;
	//player Name
	private String playerName =  "";
	//Enemy 
	private ArrayList<Enemy> enemies;
	private ArrayList<Enemy> enemies1;
	private ArrayList<Enemy> enemies2;
	private ArrayList<Explosion1> explosions1;
	
	private Image backgroundImage ,bgW;

	//Show Menu
	private boolean showMenu; // Flag to display the menu
    private String[] options = { "> Push", "> Retry","> Back Menu" };
    private int currentChoice = 0;

	private boolean gameWon = false;
	private String[] options1 = { "> NextLevel" };
    private int currentChoice1 = 0;
	private int winX = 3120;

	public Level1State(GameStateManager gsm) {
		this.gsm = gsm;
		playerName = GameStateManager.getPlayerName();
		init();
	}
	
	public void init() {
		
		tileMap = new TileMap(30);
		tileMap.loadTiles("/Tilesets/grasstileset2.gif");
		tileMap.loadMap("/Maps/level1-1.map");
		tileMap.setPosition(0, 0);
		tileMap.setTween(1);
		
		backgroundImage = new ImageIcon(getClass().getResource("/Backgrounds/snow.gif")).getImage();

		bgM = new Background("/Backgrounds/pic1.jpg", 1);
		
		bgW = new ImageIcon(getClass().getResource("/Backgrounds/giphy2.gif")).getImage();

		player = new Player(tileMap);
		player.setPosition(100, 100);
		//new update nita
		hud = new HUD(player);
		//new nita
		explosions1 = new ArrayList<Explosion1>();
		// new bupheng
		enemiesCreator();
		enemiesCreator1();
		enemiesBoss();
		//new monyneath
		AudioPlayer.stop("background_2");
		AudioPlayer.stop("completion level");
		AudioPlayer.load("/Audio/Level_1.wav", "Level_1");
		AudioPlayer.loop ("Level_1",600,AudioPlayer.getFrames("Level_1")-2200);
		
	}

	private void enemiesBoss(){
    enemies2 = new ArrayList<Enemy> ();
    Snowman sm;
    Point[] points2 = new Point[] {
      new Point(3120,50)
    };

      for(int i=0; i<points2.length; i++){
        sm = new Snowman(tileMap,player);
        sm.setPosition(points2[i].x, points2[i].y);
        enemies.add(sm);
      }  
    }
	
	//new 
	private void enemiesCreator(){
			enemies = new ArrayList<Enemy> ();
			Snow sn;
			Point[] points = new Point[] {
				new Point(120,200),
				new Point(330,200),
				new Point(450,10),
				new Point(810,10),
				new Point(840,10),
				new Point(2130,100),
				new Point (1020,200),
				new Point(1050,200),
				new Point(2310,200),
				new Point(2220,200),
				new Point(1650,200),
				new Point(1530,200),
				new Point(2010,200),
				new Point(3030,200),
				new Point(3090,200),
				new Point (3000,100),
    		};
			for(int i=0; i<points.length; i++){
			sn = new Snow(tileMap,player);
			sn.setPosition(points[i].x, points[i].y);
			enemies.add(sn);
			}  
  	}

	private void enemiesCreator1(){
		enemies1 = new ArrayList<Enemy> ();
		Supersnow ssn;
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
		  ssn = new Supersnow(tileMap,player);
		  ssn.setPosition(points1[i].x, points1[i].y);
		  enemies.add(ssn);
		}  
	  }

	public void update() {
		
		// update player
		player.update();
		
        // Adjust tileMap position based on player position (existing code)
        tileMap.setPosition(
            GamePanel.WIDTH / 2 - player.getx(),
            GamePanel.HEIGHT / 2 - player.gety()
        );

		
		if (player.getHealth() == 0 || player.notOnScreen()) {
			gsm.setState(GameStateManager.LEVEL1STATE);
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
			  explosions1.add(new Explosion1(e.getx(), e.gety()));
			}
		  }
		  //update explosion nita
		  for(int i = 0;i<explosions1.size();i++){
		  explosions1.get(i).update();
		  if(explosions1.get(i).shouldRemove()){
			explosions1.remove(i);
			i--;
		  }
		}
		// Check win condition
		if (!gameWon && player.getx() > winX) {
			gameWon = true;
			
		}
    }

	public void draw(Graphics2D g) {
		
		// draw bg
		g.drawImage(backgroundImage, 0, 0, null);
		
		// draw tilemap
		tileMap.draw(g);
		
		// draw player
		player.draw(g);

		// draw hud nita
		hud.draw(g);

		g.setColor(Color.BLUE);
        g.setFont(new Font("Monospaced", Font.PLAIN, 12));
		g.drawString("Player Name: " + playerName, 5, 65); // Display player name
		
		//draw enemy pheng
		for(int i = 0; i<enemies.size(); i++){
			enemies.get(i).draw(g);
		}
		// explosion nita
		  for(int i = 0; i< explosions1.size(); i++){
			explosions1.get(i).setMapPosition(tileMap.getx(), tileMap.gety() );
			explosions1.get(i).draw(g);
		}

		if (showMenu) {
			drawOverlay(g);
            drawMenu(g); // Draw the menu if showMenu is true
        }  else if (gameWon) {
            drawWinMessage(g);
        }
	}

	private void drawWinMessage(Graphics2D g) {
		g.drawImage(bgW, 0, 0, null);
		g.setColor(new Color(0, 0, 0, 150)); // Semi-transparent black color
        

        g.setColor(Color.YELLOW);
        g.setFont(new Font("Monospaced", Font.BOLD, 30));
        String winMessage = "You Win!";
        int messageWidth = g.getFontMetrics().stringWidth(winMessage);
        g.drawString(winMessage, (GamePanel.WIDTH - messageWidth) / 2, GamePanel.HEIGHT / 2 - 20);

        g.setFont(new Font("Monospaced", Font.PLAIN, 20));
        String continueMessage = "Thank You For Play this Game!";
        int continueWidth = g.getFontMetrics().stringWidth(continueMessage);
        g.drawString(continueMessage, (GamePanel.WIDTH - continueWidth) / 2, GamePanel.HEIGHT / 2 + 20);
    }

	private void drawOverlay(Graphics2D g) {
		bgM.draw(g);
        g.setColor(new Color(0, 0, 0, 150)); // Semi-transparent black color
        g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT); // Cover entire screen
    }

	//Draw Menu
	private void drawMenu(Graphics2D g) {
        int menuX = 245; // Adjust X position
        int menuY = 130; // Adjust Y position
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
			gsm.setState(GameStateManager.MENUSTATE);
		}
	}

	private void selectMenuOption() {
        if (currentChoice == 0) {
            // Perform action for "Push"
			
        } else if (currentChoice == 1) {
            // Perform action for "Retry"
            gsm.setState(GameStateManager.LEVEL1STATE); // Restart the level or retry
         // Next to other level
        } else if (currentChoice == 2) {
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
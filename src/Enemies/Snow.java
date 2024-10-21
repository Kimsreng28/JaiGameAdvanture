package Enemies;

import Entity.*;
import Main.GamePanel;
import TileMap.TileMap;

import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import javax.imageio.ImageIO;

public class Snow extends Enemy {
    private BufferedImage[] sprites;
    private Player player;
    private boolean active;

    public Snow (TileMap tm, Player p){
        super(tm);
        player = p;

        moveSpeed = 1.3;
        maxSpeed = 1.3;
        fallSpeed = 0.2;
        maxFallSpeed = 10.0;
        jumpStart = -5;

        width = 30;
        height = 30;
        cwidth  = 20;
        cheight = 20;

        health = maxheal = 4;
        damage = 1;

        //load pic
        try{
            BufferedImage spritesheet = ImageIO.read(
                getClass().getResourceAsStream("/Sprites/Enemies/Snoww.gif"));
            sprites = new BufferedImage[3];
            for(int i = 0; i< sprites.length; i++){
                sprites[i] = spritesheet.getSubimage(i*width,0,width,height);

            }
        }catch(Exception e){
            e.printStackTrace();
        }
    
        animation = new Animation();
        animation.setFrames(sprites);
        animation.setDelay(300);

        left = true;
        facingRight = true;
    
    }

    private void getNextPosition(){
          if(left) dx = -moveSpeed;
          else if(right) dx = moveSpeed;
          else dx = 0;
          if(falling) {
            dy += fallSpeed;
            if(dy > maxFallSpeed) dy = maxFallSpeed;
          }
          if(jumping && !falling) {
            dy = jumpStart;
          }
      }
    public void update(){
        if(!active) {
          if(Math.abs(player.getx() - x) < GamePanel.WIDTH) active = true;
          return;
        }
        //update position
        getNextPosition();
        checkTileMapCollision();
        setPosition(xtemp, ytemp);

        //check flinching 
        if (flinching){
            long elapsed =( System.nanoTime() - flinchingTimer)/1000000;
            if(elapsed > 400){
                flinching = false;
            }
        }
        calculateCorners(x, ydest + 1);
        if(!bottomLeft) {
          left = false;
          right = facingRight = true;
        }
        if(!bottomRight) {
          left = true;
          right = facingRight = false;
        }
        setPosition(xtemp, ytemp);
    
        if(dx == 0) {
          left = !left;
          right = !right;
          facingRight = !facingRight;
        }
        animation.update();
    }

    public void draw(Graphics2D g){
        //if(notOnScreen()) return;
        setMapPosition();
        super.draw(g);
    }
}
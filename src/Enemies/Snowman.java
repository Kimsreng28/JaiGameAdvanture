package Enemies;
import java.awt.*;
import Entity.*;
import Main.GamePanel;
import TileMap.TileMap;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class Snowman extends Enemy {
    private BufferedImage[] openedSprites;
    private Player player;
    private boolean active;

    private int fallingTime = 0;

    public Snowman(TileMap tm, Player p) {
        super(tm);
        player = p;

        isSnowman = true;
        
        moveSpeed = 1.8;
        maxSpeed = 3.0;
        fallSpeed = 3.3;
        maxFallSpeed = 4.5;
        jumpStart = -5;
        
        width = 81;
        height = 112;
        cwidth = 30;
        cheight = 40;

        health = maxheal = 40;
        damage = 5;
        
        y = -200;
        // load pic
        try {
            BufferedImage spritesheet = ImageIO.read(
                    getClass().getResourceAsStream("/Sprites/Enemies/Snowman.gif"));
                    openedSprites = new BufferedImage[3];
                    for(int i = 0; i < openedSprites.length; i++) {
                        openedSprites[i] = spritesheet.getSubimage(
                            i * width, 0, width, height
                        );
                    }
    

        } catch (Exception e) {
            e.printStackTrace();
        }

        animation = new Animation();
        animation.setFrames(openedSprites);
        animation.setDelay(900);

        left = true;
        facingRight = true;

    }

    private void getNextPosition() {
        // System.out.print(dy + " ");
        // System.out.println(maxFallSpeed);'
        
        if (falling) {
            fallingTime = 0;
            dy += fallSpeed;
            if (dy > maxFallSpeed) {
                dy = maxFallSpeed;
                //new for boss
                dy = 1.4;
            }


            if (jumping && !falling) {
                dy = jumpStart;
            }
        } else {
            fallSpeed++;
        }

        if (fallSpeed > 5) {
            if (left)
                dx = -moveSpeed;
            else if (right)
                dx = moveSpeed;
            else
                dx = 0;
        }

    }

    public void update() {
        if (!active) {
            if (Math.abs(player.getx() - x) < GamePanel.WIDTH)
                active = true;
            return;
        }
        // update position
        getNextPosition();
        checkTileMapCollision();
        // setPosition(xtemp, ytemp);

        // check flinching
        if (flinching) {
            long elapsed = (System.nanoTime() - flinchingTimer) / 1000000;
            if (elapsed > 400) {
                flinching = false;
            }
        }
        calculateCorners(x, ydest + 1);
        if (!bottomLeft) {
            left = false;
            right = facingRight = true;
        }
        if (!bottomRight) {
            left = true;
            right = facingRight = false;
        }
        setPosition(xtemp, ytemp);

        if (dx == 0) {
            left = !left;
            right = !right;
            facingRight = true;
        }
        animation.update();
    }

    public void draw(Graphics2D g) {
        // if(notOnScreen()) return;
        setMapPosition();
        super.draw(g);
    }
}


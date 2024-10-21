package Enemies;

import Entity.*;
import Main.GamePanel;
import TileMap.TileMap;

import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import javax.imageio.ImageIO;

public class Superslay extends Enemy {
    private BufferedImage[] sprites;
    private Player player;
    private boolean active;

    private int fallingTime = 0;

    public Superslay(TileMap tm, Player p) {
        super(tm);
        player = p;

        moveSpeed = 0.8;
        maxSpeed = 3.0;
        fallSpeed = 1.3;
        maxFallSpeed = 1.5;
        jumpStart = -5;

        width = 39;
        height = 20;
        cwidth = 20;
        cheight = 10;

        health = maxheal = 15;
        damage = 3;

        // load pic
        try {
            BufferedImage spritesheet = ImageIO.read(
                    getClass().getResourceAsStream("/Sprites/Enemies/superslay.gif"));
            sprites = new BufferedImage[6];
            for (int i = 0; i < sprites.length; i++) {
                sprites[i] = spritesheet.getSubimage(i * width, 0, width, height);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        animation = new Animation();
        animation.setFrames(sprites);
        animation.setDelay(400);

        left = true;
        facingRight = true;

    }

    private void getNextPosition() {
        if (falling) {
            fallingTime = 0;
            dy += fallSpeed;
            if (dy > maxFallSpeed) {
                dy = maxFallSpeed;
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
        setPosition(xtemp, ytemp);

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


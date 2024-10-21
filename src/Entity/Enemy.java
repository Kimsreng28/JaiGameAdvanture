package Entity;

import Audio.AudioPlayer;
import TileMap.TileMap;

public class Enemy extends MapObject {

    protected int health;
    protected int maxheal;
    protected boolean dead;  
    protected int damage;

    protected boolean flinching;
    protected long flinchingTimer;
    
    public Enemy(TileMap tm){
        super (tm);
        AudioPlayer.load("/Audio/die.wav", "die");
    }

    public boolean isDead(){
        return dead;
    }
    public int getDamage(){
        return damage;
    }
    public void hit(int damage){
        if(dead || flinching) 
        return;
        health -= damage;
        if(health<0) health = 0;
        if(health == 0) dead = true;
        flinching = true;
        flinchingTimer = System.nanoTime();
        AudioPlayer.play("die");
    }
    public void update(){
        
    }

public void draw(java.awt.Graphics2D g) {    
    if(facingRight) {
      g.drawImage(
        animation.getImage(),
        (int)(x + xmap - width / 2),
        (int)(y + ymap - height / 2),
        null
      );
    }
    else {
      g.drawImage(
        animation.getImage(),
        (int)(x + xmap - width / 2 + width),
        (int)(y + ymap - height / 2),
        -width,
        height,
        null
      );
    }
  }
}

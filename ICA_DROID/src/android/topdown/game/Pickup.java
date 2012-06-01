package android.topdown.game;

import android.gameengine.icadroids.engine.GameEngine;
import android.gameengine.icadroids.objects.GameObject;

public abstract class Pickup extends GameObject {
	
	private boolean pickedUp;
	private int respawnrate;
	
	/**
	 * constructor voegt zichzelf toe aan de wereld
	 * @param x
	 * @param y
	 * @param sprite
	 */
	public Pickup(int x, int y, String sprite, int respawnrate){
		super();
		super.setX(x);
		super.setY(y);
		super.setSprite(sprite);
		this.respawnrate = respawnrate;
		GameEngine.items.add(this);
		pickedUp = false;
	}
	/**
	 * methode die afhandelt wat er moet gebeuren als de speler dit obj oppakt
	 * @param player de huidige speler
	 */
	public void pickupEvent(Player player){
		this.setX(-Level.TILE_SIZE*2);
		this.setY(-Level.TILE_SIZE*2);
		pickedUp=true;
	}
	
	public boolean isPickedUp(){
		return pickedUp;
	}
	
	public int getRespawnRate(){
		return respawnrate;
	}
}

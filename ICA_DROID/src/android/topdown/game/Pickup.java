package android.topdown.game;

import android.gameengine.icadroids.engine.GameEngine;
import android.gameengine.icadroids.objects.GameObject;
import android.gameengine.icadroids.objects.graphics.Sprite;

public abstract class Pickup extends GameObject {
	
	/**
	 * constructor voegt zichzelf toe aan de wereld
	 * @param x
	 * @param y
	 * @param sprite
	 */
	public Pickup(int x, int y, String sprite){
		super();
		super.setX(x);
		super.setY(y);
		super.setSprite(sprite);
		GameEngine.items.add(this);
	}
	/**
	 * methode die afhandelt wat er moet gebeuren als de speler dit obj oppakt
	 * @param player de huidige speler
	 */
	public abstract void pickupEvent(Player player);
}

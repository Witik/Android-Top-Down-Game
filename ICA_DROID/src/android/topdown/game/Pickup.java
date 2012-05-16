package android.topdown.game;

import android.gameengine.icadroids.objects.GameObject;
import android.gameengine.icadroids.objects.graphics.Sprite;

public abstract class Pickup extends GameObject {
	
	public Pickup(int x, int y, String sprite){
		super();
		super.setX(x);
		super.setY(y);
		super.setSprite(sprite);
	}
	public abstract void pickupEvent(Player player);
}

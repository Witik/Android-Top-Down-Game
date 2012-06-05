package android.topdown.game;

import android.gameengine.icadroids.alarms.Alarm;
import android.gameengine.icadroids.alarms.IAlarm;
import android.gameengine.icadroids.engine.GameEngine;
import android.gameengine.icadroids.objects.GameObject;
import android.gameengine.icadroids.renderer.Viewport;
import android.util.Log;

public abstract class Pickup extends GameObject implements IAlarm {

	private boolean pickedUp;
	private int respawnrate;
	private Viewport port;

	/**
	 * constructor voegt zichzelf toe aan de wereld
	 * 
	 * @param x
	 * @param y
	 * @param sprite
	 */
	public Pickup(int x, int y, String sprite, int respawnrate) {
		super();
		super.setSprite(sprite);
		super.setX((Level.TILE_SIZE-getSprite().getFrameWidth())/2+x);
		super.setY((Level.TILE_SIZE-getSprite().getFrameHeight())/2+y);
		this.respawnrate = respawnrate;
		GameEngine.items.add(this);
		pickedUp = false;
		port = Viewport.getInstance();
	}

	/**
	 * methode die afhandelt wat er moet gebeuren als de speler dit obj oppakt
	 * 
	 * @param player
	 *            de huidige speler
	 */
	public void pickupEvent(Player player) {
		isVisible = false;
		pickedUp = true;
		new Alarm(0, respawnrate, this);
	}

	public boolean isPickedUp() {
		return pickedUp;
	}

	public void triggerAlarm(int alarmID) {
		if (0 == alarmID) {
			if (!port.isInViewport(this)) {
				isVisible = true;
				pickedUp = false;
				Log.d("pickup", "respawn " + getClass().getSimpleName() + ", rate " + respawnrate);
			}
		}
	}
}

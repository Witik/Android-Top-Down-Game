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
	 * @param x 
	 * @param y
	 * @param sprite the chosen sprite
	 * @param respawnrate the time it wil take to respawn
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
	 * Handle the occurrence of the pickup
	 * @param player the player that pick this up
	 */
	public void pickupEvent(Player player) {
		isVisible = false;
		pickedUp = true;
		new Alarm(0, respawnrate, this);
	}

	/**
	 * @return has the object been picked up
	 */
	public boolean isPickedUp() {
		return pickedUp;
	}

	/* (non-Javadoc)
	 * @see android.gameengine.icadroids.alarms.IAlarm#triggerAlarm(int)
	 * *CALLBACK METHODE FOR IAlarm*
	 */
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

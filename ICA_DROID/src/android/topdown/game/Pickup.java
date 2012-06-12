package android.topdown.game;

import android.gameengine.icadroids.alarms.Alarm;
import android.gameengine.icadroids.alarms.IAlarm;
import android.gameengine.icadroids.engine.GameEngine;
import android.gameengine.icadroids.objects.GameObject;
import android.gameengine.icadroids.renderer.Viewport;
import android.util.Log;

public abstract class Pickup extends GameObject implements IAlarm {

	private boolean pickedUp;
	private int respawnrate, type, amount;
	private Viewport port;

	/**
	 * Creates a new pickup, sets it to the right location and adds it to the
	 * item list.
	 * 
	 * @param x
	 *            the x position of the Pickup
	 * @param y
	 *            the y position of the Pickup
	 * @param sprite
	 *            the chosen sprite
	 * @param respawnrate
	 *            the time it wil take to respawn
	 * @param type
	 *            the type of the pickup, defined by the sub-class
	 */
	public Pickup(int x, int y, String sprite, int respawnrate, int type) {
		super();
		super.setSprite(sprite);
		super.setX((Level.TILE_SIZE - getSprite().getFrameWidth()) / 2 + x);
		super.setY((Level.TILE_SIZE - getSprite().getFrameHeight()) / 2 + y);
		this.respawnrate = respawnrate;
		this.type = type;
		init();
	}

	/**
	 * Creates a new pickup, sets it to the right location and adds it to the
	 * item list.
	 * 
	 * @param x
	 *            the x position of the Pickup
	 * @param y
	 *            the y position of the Pickup
	 * @param sprite
	 *            the chosen sprite
	 * @param respawnrate
	 *            the time it wil take to respawn
	 * @param type
	 *            the type of the pickup, defined by the sub-class
	 * @param amount
	 *            the amount of the pickup, defined by the sub-class
	 */
	public Pickup(int x, int y, String sprite, int respawnrate, int type, int amount) {
		super();
		super.setSprite(sprite);
		super.setX((Level.TILE_SIZE - getSprite().getFrameWidth()) / 2 + x);
		super.setY((Level.TILE_SIZE - getSprite().getFrameHeight()) / 2 + y);
		this.respawnrate = respawnrate;
		this.type = type;
		this.amount = amount;
		init();
	}

	// add to game engine, initialise variables
	private void init() {
		GameEngine.items.add(this);
		pickedUp = false;
		port = Viewport.getInstance();
		setLayerPosition(1f);
	}

	/**
	 * Returns the type of the pickup.
	 * 
	 * @return the type of the pickup
	 */
	protected int getType() {
		return type;
	}

	/**
	 * Returns the amount of the pickup.
	 * 
	 * @return the amount of the pickup
	 */
	protected int getAmount() {
		return amount;
	}

	/**
	 * Handle the occurrence of the pickup.
	 * 
	 * @param player
	 *            the player that pick this up
	 */
	public void pickupEvent(Player player) {
		isVisible = false;
		pickedUp = true;
		new Alarm(0, respawnrate, this);
	}

	/**
	 * Returns whether or not the item has been picked up.
	 * 
	 * @return has the object been picked up
	 */
	public boolean isPickedUp() {
		return pickedUp;
	}

	/*
	 * (non-Javadoc)
	 * 
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

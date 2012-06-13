package android.topdown.game;

import java.util.List;

import android.gameengine.icadroids.alarms.Alarm;
import android.gameengine.icadroids.alarms.IAlarm;
import android.gameengine.icadroids.input.OnScreenButtons;
import android.gameengine.icadroids.objects.GameObject;
import android.gameengine.icadroids.tiles.Tile;
import android.util.Log;

public class Player extends LivingEntity implements IAlarm {

	public static final int HP = 100;
	private static final int SPEED = 7;
	private static final String SHOTGUNSPRITE = "ferdishotgun";
	private static final String PISTOLSPRITE = "ferdipistol";
	private Pistol pistol;
	private Shotgun shotgun;
	private Gun currentGun;
	private static int[] blockedTiles = { Level.ID_WALL };
	private boolean hasShotgun, swappable;
	private boolean godmode;

	/**
	 * Creates the player, gives him a pistol with half of the max ammo and initializes some fields.
	 */
	public Player() {
		super(blockedTiles, HP, SPEED);
		pistol = new Pistol(Pistol.MAX_AMMO / 2);
		hasShotgun = false;
		godmode = false;
		swappable = true;
		currentGun = pistol;
		setSprite(PISTOLSPRITE);
	}
	
	/**
	 * God Mode
	 * @param b on or off
	 */
	public void god(boolean b){
		godmode = b;
	}

	/**
	 * Gives the player a gun, if he already has the gun, he'll take the ammo of the given gun.
	 * @param gun the gun that is given.
	 */
	public void giveGun(Gun gun) {
		if (gun instanceof Shotgun) {
			if (hasShotgun)
				shotgun.addAmmo(gun.getAmmo());
			else {
				shotgun = (Shotgun) gun;
				hasShotgun = true;
				currentGun = shotgun;
				swapSprite();
			}
			switch ((int) (Math.random() * 4)) {
			case 0:
				SoundLib.play(SoundLib.FERDI_BITCHPLEASE);
				break;
			case 1:
				SoundLib.play(SoundLib.FERDI_SHOTGUNFERDI);
				break;
			case 2:
				SoundLib.play(SoundLib.FERDI_COMEGETSOME);
				break;
			case 3:
				SoundLib.play(SoundLib.FERDI_MOTHERFUCKINGSHOTGUN);
				break;
			case 4:
				SoundLib.play(SoundLib.FERDI_FULLOFCRAP);
				break;
			}
		} else if (gun instanceof Pistol) {
			pistol.addAmmo(gun.getAmmo());
		}
	}

	/**
	 * Returns the player's shotgun.
	 * @return the player's shotgun
	 */
	public Shotgun getShotgun() {
		return shotgun;
	}
	
	/**
	 * Returns the player's pistol.
	 * @return the player's pistol
	 */
	public Pistol getPistol() {
		return pistol;
	}

	/**
	 * Gives the player the given amount of ammo for the given gun type.
	 * returns false if the player has full ammo or doesn't have the gun the ammo is for. 
	 * @param type
	 *            type of ammo
	 * @param amount
	 *            amount of ammo
	 * @return if the player could use the ammo
	 */
	public boolean giveAmmo(int type, int amount) {
		if (type == Ammo.TYPE_PISTOL) {
			pistol.addAmmo(amount);
			return true;
		} else if (type == Ammo.TYPE_SHOTGUN && hasShotgun) {
			shotgun.addAmmo(amount);
			return true;
		}
		return false;
	}

	//shoots the current gun
	private void shoot() {
		float rot = getRotation();

		double xx = Math.sin(Math.toRadians(rot)) * 42;
		double yy = Math.cos(Math.toRadians(rot)) * 42;

		int x = (int) getCenterX() + (int) Math.round(xx);
		int y = (int) getCenterY() - (int) Math.round(yy);

		currentGun.shoot(x, y, (int) rot);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.topdown.game.LivingEntity#update()
	 */
	public void update() {
		super.update();

		movement();
		if (getHp() > getmaxHp()) {// als we op adren zitten dan langzaam laten
									// dalen
			Game.addPoints(1);
			setHp(getHp() - 1);
		}
	}

	//handles the players movement
	private void movement() {
		if (OnScreenButtons.dPadUp)
			moveForward();
		else if (OnScreenButtons.dPadDown)
			moveBackward();
		else if (OnScreenButtons.dPadLeft)
			moveLeft();
		else if (OnScreenButtons.dPadRight)
			moveRight();
		if (OnScreenButtons.button1)
			swap();
		else if (OnScreenButtons.button2)
			shoot();
		if (OnScreenButtons.button3)
			rotate(-7.5f);
		else if (OnScreenButtons.button4)
			rotate(7.5f);
	}
	
	@SuppressWarnings("unused")
	@Deprecated
	//other type of control. 
	private void isaacmovement() {
		int speed = (int) getSpeeds();
		if (OnScreenButtons.dPadUp)
			movePlayer(0, -speed);
		else if (OnScreenButtons.dPadDown)
			movePlayer(0, speed);
		else if (OnScreenButtons.dPadLeft)
			movePlayer(-speed, 0);
		else if (OnScreenButtons.dPadRight)
			movePlayer(speed, 0);
		if (OnScreenButtons.button1) {
			setRotation(180f);
			shoot();
		} else if (OnScreenButtons.button2) {
			setRotation(90f);
			shoot();
		} else if (OnScreenButtons.button3) {
			setRotation(270f);
			shoot();
		} else if (OnScreenButtons.button4) {
			setRotation(0f);
			shoot();
		}
		if (OnScreenButtons.shoulderR) {
			swap();
		}
	}

	//swaps the gun of the player has a shotgun
	private void swap() {
		if (swappable) {
			if (hasShotgun) {
				if (currentGun.equals(shotgun))
					currentGun = pistol;
				else
					currentGun = shotgun;
				swapSprite();
			}
			new Alarm(1, 10, this);
			swappable = false;
		}
	}

	//swaps the sprite
	private void swapSprite() {
		if (currentGun.equals(shotgun))
			setSprite(SHOTGUNSPRITE);
		else
			setSprite(PISTOLSPRITE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.topdown.game.LivingEntity#collisionOccurred(java.util.List)
	 */
	public void collisionOccurred(List<Tile> collidedTiles) {
		super.collisionOccurred(collidedTiles);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.gameengine.icadroids.alarms.IAlarm#triggerAlarm(int)
	 */
	public void triggerAlarm(int alarmID) {
		if (alarmID == 1)
			swappable = true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.topdown.game.LivingEntity#hurt(int)
	 */
	public void hurt(int damage) {
		if (!godmode) {
			super.hurt(damage);
			if ((int) (Math.random() * 20) == 0)
				SoundLib.play(SoundLib.FERDI_HURTSCREAM);
			else if ((int) (Math.random() * 20) == 0 && getHp() < getmaxHp() / 4)
				SoundLib.play(SoundLib.FERDI_NEEDMOREFOOD);
			else {
				switch ((int) (Math.random() * 3 + 1)) {
				case 1:
					SoundLib.play(SoundLib.FERDI_HURT1);
					break;
				case 2:
					SoundLib.play(SoundLib.FERDI_HURT2);
					break;
				case 3:
					SoundLib.play(SoundLib.FERDI_HURT3);
					break;
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.topdown.game.LivingEntity#objectCollision(android.gameengine.
	 * icadroids.objects.GameObject)
	 */
	@Override
	protected void objectCollision(GameObject g) {
		if (g instanceof Pickup) {
			if (!((Pickup) g).isPickedUp()) {
				((Pickup) g).pickupEvent(this);
				Log.i("collision", "pickup");
			}
		} else if (g instanceof Zombie) {
			hurt(((Zombie) g).getDamage());
			Log.i("collision", "zombie");
		}
	}

	/* (non-Javadoc)
	 * @see android.topdown.game.LivingEntity#die()
	 */
	@Override
	public void die() {
		super.die();
		Log.i("Player", "died");
	}

	/**
	 * returns true if the player has a shotgun
	 * 
	 * @return true if the player has a shotgun
	 */
	public boolean hasShotgun() {
		return hasShotgun;
	}

	/**
	 * returns the gun the player is currently holding
	 * @return the gun the player is currently holding
	 */
	public Gun getCurrentGun() {
		return currentGun;
	}
}

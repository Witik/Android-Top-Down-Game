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

	public Player() {
		super(blockedTiles, HP, SPEED);
		pistol = new Pistol(Pistol.MAX_AMMO / 2);
		hasShotgun = false;
		swappable = true;
		currentGun = pistol;
		setSprite(PISTOLSPRITE);
	}

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

	public Shotgun getShotgun() {
		return shotgun;
	}

	public Pistol getPistol() {
		return pistol;
	}

	/**
	 * @param type type of ammo
	 * @param amount amount of ammo
	 * @return did the amount of ammo fit
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

	private void shoot() {
		float rot = getRotation();

		double xx = Math.sin(Math.toRadians(rot)) * 42;
		double yy = Math.cos(Math.toRadians(rot)) * 42;

		int x = (int) getCenterX() + (int) Math.round(xx);
		int y = (int) getCenterY() - (int) Math.round(yy);

		currentGun.shoot(x, y, (int) rot);
	}

	/* (non-Javadoc)
	 * @see android.topdown.game.LivingEntity#update()
	 */
	public void update() {
		super.update();

		makeSound();

		movement();
		if (getHp() > getmaxHp()) {// als we op adren zitten dan langzaam laten
									// dalen
			setHp(getHp() - 1);
		}
	}

	private void makeSound() {
		if ((int) (Math.random() * 200) == 0)
			if (false)
				;
	}

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

	private void swapSprite() {
		if (currentGun.equals(shotgun))
			setSprite(SHOTGUNSPRITE);
		else
			setSprite(PISTOLSPRITE);
	}

	/* (non-Javadoc)
	 * @see android.topdown.game.LivingEntity#collisionOccurred(java.util.List)
	 */
	public void collisionOccurred(List<Tile> collidedTiles) {
		super.collisionOccurred(collidedTiles);
	}

	/* (non-Javadoc)
	 * @see android.gameengine.icadroids.alarms.IAlarm#triggerAlarm(int)
	 */
	public void triggerAlarm(int alarmID) {
		if (alarmID == 1)
			swappable = true;
	}

	/* (non-Javadoc)
	 * @see android.topdown.game.LivingEntity#hurt(int)
	 */
	public void hurt(int damage) {
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

	/* (non-Javadoc)
	 * @see android.topdown.game.LivingEntity#objectCollision(android.gameengine.icadroids.objects.GameObject)
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

	@Override
	public void die() {
		super.die();
		Log.i("Player", "died");
	}

	/**
	 * does the player have a shotgun
	 * @return yes or no
	 */
	public boolean hasShotgun() {
		return hasShotgun;
	}

	/**
	 * @return show me your current gun
	 */
	public Gun getCurrentGun() {
		return currentGun;
	}
}

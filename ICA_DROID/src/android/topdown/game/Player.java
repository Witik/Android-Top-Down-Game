package android.topdown.game;

import java.util.List;

import android.gameengine.icadroids.alarms.Alarm;
import android.gameengine.icadroids.alarms.IAlarm;
import android.gameengine.icadroids.input.OnScreenButtons;
import android.gameengine.icadroids.objects.GameObject;
import android.gameengine.icadroids.tiles.Tile;
import android.util.Log;

public class Player extends LivingEntity implements IAlarm {

	private static final int HP = 100;
	private static final int SPEED = 7;
	private static final String SPRITE = "ferdi";
	private Pistol pistol;
	private Shotgun shotgun;
	private Gun currentGun;
	private static int[] blockedTiles = { Level.ID_WALL };
	private boolean hasShotgun, swappable;

	public Player() {
		super(blockedTiles, HP, SPEED);
		pistol = new Pistol(Pistol.MAX_AMMO);
		shotgun = new Shotgun(5);
		hasShotgun = true;
		swappable = true;
		currentGun = shotgun;
		setSprite(SPRITE);
	}

	public void giveGun(Gun gun) {
		if (gun instanceof Shotgun) {
			if (hasShotgun)
				shotgun.addAmmo(gun.getAmmo());
			else {
				shotgun = (Shotgun) gun;
				hasShotgun = true;
				currentGun = shotgun;
				if ((int) (Math.random() * 2) == 1)
					SoundLib.play(SoundLib.FERDI_BITCHPLEASE2);
				else
					SoundLib.play(SoundLib.FERDI_SHOTGUNFERDI);
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
	 * @param type
	 * @param amount
	 * @return
	 */
	public boolean giveAmmo(int type, int amount) {
		if (type == Ammo.TYPE_PISTOL) {
			// TODO add pistol ammo
			return true;
		} else if (type == Ammo.TYPE_SHOTGUN && hasShotgun) {
			// TODO add shotgun ammo
			return true;
		}
		return false;
	}

	public void shoot() {
		float rot = getRotation();

		double xx = Math.sin(Math.toRadians(rot)) * 42;
		double yy = Math.cos(Math.toRadians(rot)) * 42;

		int x = (int) getCenterX() + (int) Math.round(xx);
		int y = (int) getCenterY() - (int) Math.round(yy);

		currentGun.shoot(x, y, (int) rot);
	}

	public void update() {
		super.update();

		if (OnScreenButtons.dPadUp)
			moveUp();
		else if (OnScreenButtons.dPadDown)
			moveDown();
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
		if (getHp() > getmaxHp()) {// als we op adren zitten dan langzaam laten
									// dalen
			setHp(getHp() - 1);
		}
	}

	private void swap() {
		if (swappable) {
			if (hasShotgun) {
				if (currentGun.equals(shotgun))
					currentGun = pistol;
				else
					currentGun = shotgun;
			}
			new Alarm(1, 10, this);
			swappable = false;
		}
	}

	public void collisionOccurred(List<Tile> collidedTiles) {
		super.collisionOccurred(collidedTiles);
	}

	public void triggerAlarm(int alarmID) {
		if (alarmID == 1)
			swappable = true;
	}

	public void hurt(int damage) {
		super.hurt(damage);
		if ((int) (Math.random() * 20) == 0)
			SoundLib.play(SoundLib.FERDI_HURTSCREAM);
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
}

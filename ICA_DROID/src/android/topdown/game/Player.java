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
	private static final String SHOTGUNSPRITE = "ferdishotgun";
	private static final String PISTOLSPRITE = "ferdipistol";
	private Pistol pistol;
	private Shotgun shotgun;
	private Gun currentGun;
	private static int[] blockedTiles = { Level.ID_WALL };
	private boolean hasShotgun, swappable;

	public Player() {
		super(blockedTiles, HP, SPEED);
		pistol = new Pistol(Pistol.MAX_AMMO/2);
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
				if ((int) (Math.random() * 2) == 1)
					SoundLib.play(SoundLib.FERDI_BITCHPLEASE);
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

		movement();
		if (getHp() > getmaxHp()) {// als we op adren zitten dan langzaam laten
									// dalen
			setHp(getHp() - 1);
		}
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
		int speed = (int)getSpeeds();
		if (OnScreenButtons.dPadUp)
			movePlayer(0,-speed);
		else if (OnScreenButtons.dPadDown)
			movePlayer(0,speed);
		else if (OnScreenButtons.dPadLeft)
			movePlayer(-speed,0);
		else if (OnScreenButtons.dPadRight)
			movePlayer(speed,0);
		if (OnScreenButtons.button1){
			setRotation(180f);
			shoot();
		} else if (OnScreenButtons.button2){
			setRotation(90f);
			shoot();
		} else if (OnScreenButtons.button3){
			setRotation(270f);
			shoot();
		} else if (OnScreenButtons.button4){
			setRotation(0f);
			shoot();
		}
		if (OnScreenButtons.shoulderR){
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
	
	private void swapSprite(){
		if(currentGun.equals(shotgun))
			setSprite(SHOTGUNSPRITE);
		else
			setSprite(PISTOLSPRITE);
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
	
	public boolean hasShotgun(){
		return hasShotgun;
	}
	public Gun getCurrentGun(){
		return currentGun;
	}
}

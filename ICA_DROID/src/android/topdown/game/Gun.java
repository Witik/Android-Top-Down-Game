package android.topdown.game;

import android.gameengine.icadroids.alarms.Alarm;
import android.gameengine.icadroids.alarms.IAlarm;

public abstract class Gun implements IAlarm {
	private int ammoMax, damage, rate, ammo;
	private boolean canShoot;

	public Gun(String sprite, int ammo, int ammoMax, int damage, int rate) {
		this.ammo = ammo;
		this.ammoMax = ammoMax;
		this.damage = damage;
		this.rate = rate;
		canShoot = true;
	}

	/**
	 * @return the amount of ammo this gun currently has
	 */
	public int getAmmo() {
		return ammo;
	}

	/**
	 * @return the maximum amount of ammo this gun can haz
	 */
	public int getAmmoMax() {
		return ammoMax;
	}

	/**
	 * @return the amount of damage this gun does per bullet
	 */
	public int getDamage() {
		return damage;
	}

	/**
	 * @param ammo the ammount of ammo you want added to this gun
	 */
	public void addAmmo(int ammo) {
		this.ammo += ammo;
		if (this.ammo > ammoMax)
			this.ammo = ammoMax;
	}

	/**
	 * @return if true the gun is empty else the gun still has ammo
	 */
	public boolean isEmpty(){
		if(ammo<=0){
			SoundLib.play(SoundLib.SFX_GUNCLICK);
			new Alarm(1, 10, this);
			canShoot=false;
			return true;
		}
		return false;
	}
	
	/**
	 * @return can the gun currently shoot or not?
	 */
	public boolean canShoot(){
		return canShoot;
	}
	
	/**
	 * methode called after shooting which sets that the gun cant fire for a set amount of time
	 */
	public void shot(){
		ammo--;
		canShoot = false;
		new Alarm(1, rate, this);
	}

	public boolean alarmsActiveForThisObject() {
		return !canShoot;
	}

	/* (non-Javadoc)
	 * @see android.gameengine.icadroids.alarms.IAlarm#triggerAlarm(int)
	 * *Callback Methode for IAlarm*
	 */
	public void triggerAlarm(int alarmID) {
		canShoot = true;
	}

	/**
	 * Fire the gun
	 * @param x x-point from which will be shot
	 * @param y y-point from which will be shot
	 * @param rotation the angle at which we will shoot
	 */
	public abstract void shoot(double x, double y, int rotation);
}

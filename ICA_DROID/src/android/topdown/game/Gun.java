package android.topdown.game;

public abstract class Gun {
	private int ammoMax, damage;
	protected int ammo;

	public Gun(String sprite, int ammo, int ammoMax, int damage) {
		this.ammo = ammo;
		this.ammoMax = ammoMax;
		this.damage = damage;
	}

	public int getAmmo() {
		return ammo;
	}

	public int getAmmoMax() {
		return ammoMax;
	}

	public int getDamage() {
		return damage;
	}

	public void addAmmo(int ammo) {
		this.ammo += ammo;
		if (ammo > ammoMax)
			ammo = ammoMax;
	}

	public abstract void shoot(double x, double y, int rotation);
}

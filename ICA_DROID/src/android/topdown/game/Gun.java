package android.topdown.game;

public abstract class Gun extends Pickup {
	private int ammo,ammoMax,damage;
	
	public Gun(int x, int y, String sprite, int ammo, int ammoMax, int damage) {
		super(x, y, sprite);
		this.ammo = ammo;
		this.ammoMax = ammoMax;
		this.damage = damage;
	}

	public Gun(String sprite, int ammo, int ammoMax, int damage) {
		super(0, 0, sprite);
		this.ammo = ammo;
		this.ammoMax = ammoMax;
		this.damage = damage;
	}

	@Override
	public void pickupEvent(Player player) {
		//TODO methode afmaken als de player zijn constructors af zijn
	}
	public abstract void shoot(double x, double y, int rotation);
}

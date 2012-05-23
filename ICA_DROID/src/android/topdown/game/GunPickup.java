package android.topdown.game;

public class GunPickup extends Pickup {

	public static final int TYPE_PISTOL = 0;
	public static final int TYPE_SHOTGUN = 1;
	private static final String PISTOL_SPRITE = "shotgunpickup";
	private static final String SHOTGUN_SPRITE = "pistolpickup";
	
	public GunPickup(int x, int y, int type) {
		super(x, y, gunSprite(type));
		// TODO Auto-generated constructor stub
	}

	private static String gunSprite(int type) {
		if(type==TYPE_PISTOL)
			return PISTOL_SPRITE;
		else if (type==TYPE_SHOTGUN)
			return SHOTGUN_SPRITE;
		return "missingimage";
	}

	@Override
	public void pickupEvent(Player player) {
		player.giveGun(new Shotgun(Shotgun.MAX_AMMO));
	}

}

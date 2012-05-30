package android.topdown.game;

public class GunPickup extends Pickup {

	public static final int TYPE_PISTOL = 0;
	public static final int TYPE_SHOTGUN = 1;
	private static final String PISTOL_SPRITE = "shotgunpickup";
	private static final String SHOTGUN_SPRITE = "pistolpickup";
	private int type;
	private int ammo;
	
	public GunPickup(int x, int y, int type,int ammo) {
		super(x, y, gunSprite(type));
		this.type = type;
		this.ammo = ammo;
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
		player.giveGun(giveGun());
	}

	/**
	 * resolve de gun die we gaan geven
	 * ALS DE TYPE NIET BESTAAT RETUNEERD LEGE GUN
	 * @return de gun die gegeven moet worden
	 */
	private Gun giveGun() {
		if(type==TYPE_PISTOL){
			return new Pistol(ammo);
		}
		else if(type==TYPE_SHOTGUN){
			return new Shotgun(ammo);
		}
		else{// als de type niet bestaat geef dan een lege pistol om problemen te voorkomen
			return new Pistol(0);
		}
	}

}

package android.topdown.game;

public class Ammo extends Pickup {
	public static final int TYPE_PISTOL = 1;
	public static final int TYPE_SHOTGUN = 2;
	private int type,amount;
	/**
	 * @param x 
	 * @param y 
	 * @param type type of gun
	 * @param amount the amount of ammo
	 * @param respawnrate how long it takes for it to respawn
	 */
	public Ammo(int x, int y, int type, int amount, int respawnrate) {
		super(x, y, getSprite(type), respawnrate);
		this.type = type;
		this.amount = amount;
	}

	private static String getSprite(int type) {
		if(type==Ammo.TYPE_PISTOL){
			return "pistolammo";
		}
		else if (type==TYPE_SHOTGUN){
			return "shotgunammo";
		}
		else{
			return "spritemissing";
		}
	}

	@Override
	public void pickupEvent(Player player) {
		if(player.giveAmmo(type,amount)){
			if ((int) (Math.random() * 4) == 0)
				SoundLib.play(SoundLib.FERDI_RELOADINGTHEMBITCHES);
			super.pickupEvent(player);
		}
	}

}

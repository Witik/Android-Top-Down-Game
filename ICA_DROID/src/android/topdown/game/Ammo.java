package android.topdown.game;

public class Ammo extends Pickup {
	public static final int TYPE_PISTOL = 1;
	public static final int TYPE_SHOTGUN = 2;

	/**
	 * @param x
	 * @param y
	 * @param type
	 *            type of gun
	 * @param amount
	 *            the amount of ammo
	 * @param respawnrate
	 *            how long it takes for it to respawn
	 */
	public Ammo(int x, int y, int type, int amount, int respawnrate) {
		super(x, y, ammoSprite(type), respawnrate,type,amount);
	}

	//give the ammo the correct sprite according to its type
	private static String ammoSprite(int type) {
		if (type == Ammo.TYPE_PISTOL) {
			return "pistolammo";
		} else if (type == TYPE_SHOTGUN) {
			return "shotgunammo";
		} else {
			return "spritemissing";
		}
	}

	/* (non-Javadoc)
	 * @see android.topdown.game.Pickup#pickupEvent(android.topdown.game.Player)
	 */
	@Override
	public void pickupEvent(Player player) {
		if (player.giveAmmo(getType(), getAmount())) {
			if ((int) (Math.random() * 4) == 0)
				SoundLib.play(SoundLib.FERDI_RELOADINGTHEMBITCHES);
			super.pickupEvent(player);
		}
	}

}

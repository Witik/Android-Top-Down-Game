package android.topdown.game;

public class Ammo extends Pickup {
	public static final int TYPE_PISTOL = 1;
	public static final int TYPE_SHOTGUN = 2;
	private int type,amount;
	public Ammo(int x, int y, int type, int amount) {
		super(x, y, getSprite(type));
		this.type = type;
		this.amount = amount;
	}

	private static String getSprite(int type) {
		if(type==Ammo.TYPE_PISTOL){
			return "pistolammo";
		}
		else{
			return "shotgunammo";
		}
	}

	@Override
	public void pickupEvent(Player player) {
		//TODO methode afmaken als de player zijn constructors af zijn
	}

}

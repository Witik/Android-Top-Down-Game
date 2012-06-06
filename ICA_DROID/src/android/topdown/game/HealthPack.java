package android.topdown.game;

public class HealthPack extends Pickup {

	static final int TYPE_MEDKIT = 0;
	static final int TYPE_PILLS = 1;
	static final int HEAL_MEDKIT = Player.HP*(3/4);
	static final int HEAL_PILLS = Player.HP*(1/4);

	private int type;

	public HealthPack(int x, int y, int type, int respawnrate) {
		super(x, y, getSprite(type), respawnrate);
		this.type = type;
	}

	private static String getSprite(int type) {
		if(type==TYPE_MEDKIT){
			return "health";
		}
		else if (type==TYPE_PILLS){
			return "health";
		}
		else{
			return "spritemissing";
		}
	}

	@Override
	public void pickupEvent(Player player) {
		if (type == TYPE_MEDKIT) {
			player.heal(HEAL_MEDKIT);
			SoundLib.play(SoundLib.FERDI_MOTHERFUCKINGFOOD);
		}
		if (type == TYPE_PILLS) {
			player.heal(HEAL_PILLS);
			if ((int) (Math.random() * 2) == 0)
				SoundLib.play(SoundLib.FERDI_PILLS);
			else
				SoundLib.play(SoundLib.FERDI_GOTMOTHERFUCKINGPILLS);
		}
		super.pickupEvent(player);
	}

}

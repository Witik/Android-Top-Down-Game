package android.topdown.game;

public class HealthPack extends Pickup {

	private int amount;
	public HealthPack(int x, int y, int amount) {
		super(x, y, getSprite(amount));
		this.amount = amount;
	}

	private static String getSprite(int amount) {
		if(amount>50){
			return "healthbig";
		}
		else{
			return "health";
		}
	}

	@Override
	public void pickupEvent(Player player) {
		player.heal(amount);
		this.deleteThisGameObject();
	}

}

package android.topdown.game;

public class HealthPack extends Pickup {

	private int amount;
	public HealthPack(int x, int y, int amount, int respawnrate) {
		super(x, y, "health", respawnrate);
		this.amount = amount;
	}

	@Override
	public void pickupEvent(Player player) {
		player.heal(amount);
		super.pickupEvent(player);
	}

}

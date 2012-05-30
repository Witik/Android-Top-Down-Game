package android.topdown.game;

public class HealthPack extends Pickup {

	private int amount;
	public HealthPack(int x, int y, int amount) {
		super(x, y, "health");
		this.amount = amount;
	}

	@Override
	public void pickupEvent(Player player) {
		player.heal(amount);
		this.deleteThisGameObject();
	}

}

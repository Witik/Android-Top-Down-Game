package android.topdown.game;

public class HealthPack extends Pickup {

	private int amount;
	public HealthPack(int x, int y, int amount) {
		super(x, y, "healthpack");
		this.amount = amount;
	}

	@Override
	public void pickupEvent(Player player) {
		//TODO methode afmaken als de player zijn constructors af zijn
	}

}

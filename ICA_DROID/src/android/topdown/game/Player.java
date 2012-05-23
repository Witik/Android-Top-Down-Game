package android.topdown.game;

import java.util.List;

import android.gameengine.icadroids.input.OnScreenButtons;
import android.gameengine.icadroids.tiles.Tile;

public class Player extends LivingEntity {

	private static Tile[] blockedTiles = {};
	private int currentGun;
	private boolean hasShotgun;

	public Player() {
		super(blockedTiles, 100, 7);
	}
	
	public void giveGun(Gun gun){
		
	}
	
	/**
	 * @param type
	 * @param amount
	 * @return
	 */
	public boolean giveAmmo(int type, int amount){
		if(type==Ammo.TYPE_PISTOL){
			//TODO add pistol ammo
			return true;
		}else if(type==Ammo.TYPE_SHOTGUN&&hasShotgun){
			//TODO add shotgun ammo
			return true;
		}
		return false;
	}

	public void update() {
		super.update();

		if (OnScreenButtons.dPadUp) {
			moveUp();
		}
		if (OnScreenButtons.dPadDown) {
			moveDown();
		}
		if (OnScreenButtons.dPadLeft) {
			moveLeft();
		}
		if (OnScreenButtons.dPadRight) {
			moveRight();
		}
		// if (OnScreenButtons.button1)
		//
		// if (OnScreenButtons.button2)

		if (OnScreenButtons.button3)
			rotate(-10);
		if (OnScreenButtons.button4)
			rotate(10);
	}

	public void collisionOccurred(List<Tile> collidedTiles) {
		super.collisionOccurred(collidedTiles);
	}
}

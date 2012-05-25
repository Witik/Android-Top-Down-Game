package android.topdown.game;

import java.util.List;

import android.gameengine.icadroids.engine.GameEngine;
import android.gameengine.icadroids.input.OnScreenButtons;
import android.gameengine.icadroids.tiles.Tile;

public class Player extends LivingEntity {

	private static final int HP = 100;
	private static final int SPEED = 7;
	private static final String SPRITE = "ferdi";
	private Pistol pistol;
	private Shotgun shotgun;
	private Gun currentGun;
	private static int[] blockedTiles = { Level.ID_WALL };
	private boolean hasShotgun;

	public Player() {
		super(blockedTiles, HP, SPEED);
		pistol = new Pistol(Pistol.MAX_AMMO);
		shotgun = new Shotgun(Shotgun.MAX_AMMO);
		hasShotgun = true;
		currentGun = shotgun;
		setSprite(SPRITE);
	}

	public void giveGun(Gun gun) {
		if(gun instanceof Shotgun){
			if(hasShotgun)
				shotgun.addAmmo(gun.getAmmo());
			else {
				shotgun = (Shotgun) gun;
				hasShotgun = true;
				currentGun = shotgun;
			}
		} else if (gun instanceof Pistol){
			pistol.addAmmo(gun.getAmmo());
		}
	}
	
	public Shotgun getShotgun(){
		return shotgun;
	}
	
	public Pistol getPistol(){
		return pistol;
	}

	/**
	 * @param type
	 * @param amount
	 * @return
	 */
	public boolean giveAmmo(int type, int amount) {
		if (type == Ammo.TYPE_PISTOL) {
			// TODO add pistol ammo
			return true;
		} else if (type == Ammo.TYPE_SHOTGUN && hasShotgun) {
			// TODO add shotgun ammo
			return true;
		}
		return false;
	}

	public void shoot() {
		int x = getX()+36;
		int y = getY();
		currentGun.shoot(x, y, (int) getRotation());
	}

	public void update() {
		super.update();

		if (OnScreenButtons.dPadUp)
			moveUp();
		if (OnScreenButtons.dPadDown)
			moveDown();
		if (OnScreenButtons.dPadLeft)
			moveLeft();
		if (OnScreenButtons.dPadRight)
			moveRight();
		if (OnScreenButtons.button1)
			swap();
		if (OnScreenButtons.button2)
			shoot();
		if (OnScreenButtons.button3)
			rotate(-7.5f);
		if (OnScreenButtons.button4)
			rotate(7.5f);
	}

	private void swap() {
		if (hasShotgun) {
			if (currentGun.equals(shotgun))
				currentGun = pistol;
			else
				currentGun = shotgun;
		}
	}

	public void collisionOccurred(List<Tile> collidedTiles) {
		super.collisionOccurred(collidedTiles);
	}
}

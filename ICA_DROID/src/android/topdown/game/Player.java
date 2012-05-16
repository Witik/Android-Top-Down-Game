package android.topdown.game;

import java.util.List;

import android.gameengine.icadroids.input.OnScreenButtons;
import android.gameengine.icadroids.tiles.Tile;

public class Player extends LivingEntity {

	static Tile[] blockedTiles = {};

	public Player() {
		super(blockedTiles, 100, 7);
	}

	public void update() {
		super.update();
		double xspeed = getxSpeed();
		double yspeed = getySpeed();

		if (xspeed > 0)
			xspeed--;
		if (xspeed < 0)
			xspeed++;
		if (yspeed > 0)
			yspeed--;
		if (yspeed < 0)
			yspeed++;

		if (OnScreenButtons.dPadUp) {
			double dx = Math.sin(Math.toRadians(getRotation()))*getSpeeds();
			double dy = Math.cos(Math.toRadians(getRotation()))*getSpeeds();
			movePlayer((int)Math.ceil(dx),(int)Math.ceil(dy));
		}
		if (OnScreenButtons.dPadDown) {
			double dx = Math.sin(Math.toRadians(getRotation()))*getSpeeds();
			double dy = Math.cos(Math.toRadians(getRotation()))*getSpeeds();
			xspeed = dx;
			yspeed = dy;
		}
		if (OnScreenButtons.dPadLeft) {
			xspeed = -getSpeeds();
			// setFacing(LEFT);
		}
		if (OnScreenButtons.dPadRight) {
			xspeed = getSpeeds();
			// setFacing(RIGHT);
		}
		// if (OnScreenButtons.button1)
		//
		// if (OnScreenButtons.button2)

		if (OnScreenButtons.button3)
			rotate(10);
		if (OnScreenButtons.button4)
			rotate(-10);

		setxSpeed(xspeed);
		setySpeed(yspeed);
	}

	public void collisionOccurred(List<Tile> collidedTiles) {
		super.collisionOccurred(collidedTiles);
	}
}

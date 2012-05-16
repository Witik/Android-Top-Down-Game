package android.topdown.game;

import java.util.List;

import android.gameengine.icadroids.input.OnScreenButtons;
import android.gameengine.icadroids.objects.MoveableGameObject;
import android.gameengine.icadroids.tiles.Tile;

public class Player extends MoveableGameObject {
	double speed = 10;

	public void update(){
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

		if (OnScreenButtons.dPadUp)
			yspeed = -speed;
		if (OnScreenButtons.dPadDown)
			yspeed = speed;
		if (OnScreenButtons.dPadLeft)
			xspeed = -speed;
		if (OnScreenButtons.dPadRight)
			xspeed = speed;

		setxSpeed(xspeed);
		setySpeed(yspeed);
	}
	
	public void collisionOccurred(List<Tile> collidedTiles) {
	}
}

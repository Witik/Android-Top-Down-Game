package android.topdown.game;

import java.util.List;

import android.gameengine.icadroids.objects.MoveableGameObject;
import android.gameengine.icadroids.tiles.Tile;
import android.graphics.Canvas;

public class LivingEntity extends MoveableGameObject {
	public static final int UP = 0;
	public static final int RIGHT = 1;
	public static final int DOWN = 2;
	public static final int LEFT = 3;

	private int hp;
	private float rotation;
	private double speed;
	private Tile[] blockedTiles;

	public LivingEntity(Tile[] blockedTiles, int hp, double speed) {
		this.hp = hp;
		this.speed = speed;
		this.blockedTiles = blockedTiles;
	}
	
	public void rotate(float rotation){
		this.rotation += rotation;
		if(rotation>360)
			rotation-= 360;
		else if(rotation<0)
			rotation+=360;
	}
	
	public float getRotation(){
		return rotation;
	}

	public void drawGameObject(Canvas canvas) {
		if (getSprite().getSprite() != null && isVisible) {
			canvas.save();
			canvas.rotate(rotation, getX() + (getSprite().getFrameWidth() / 2), getY() + (getSprite().getFrameHeight() / 2));
			canvas.drawBitmap(getSprite().getSprite(), getSprite().getCurrentFrameRectangle(), position, null);
			canvas.restore();
		}
	}

	public double getSpeeds() {
		return speed;
	}

	/**
	 * De beweegsnelheid, wordt altijd positief.
	 * 
	 * @param speed
	 *            de snelheid waarmee hij beweegt, wordt versteld naar positief
	 */
	public void setSpeeds(double speed) {
		this.speed = Math.abs(speed);
	}

	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}

	public void collisionOccurred(List<Tile> collidedTiles) {
	}
	
}

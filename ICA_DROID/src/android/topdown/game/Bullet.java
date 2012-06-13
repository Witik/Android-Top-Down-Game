package android.topdown.game;

import java.util.List;

import android.gameengine.icadroids.objects.MoveableGameObject;
import android.gameengine.icadroids.objects.graphics.AnimatedSprite;
import android.gameengine.icadroids.tiles.Tile;
import android.graphics.Canvas;

public class Bullet extends MoveableGameObject {

	private static final int BULLET_SPEED = 20;
	private int decay;
	private int damage, rotation;

	private static int[] blockedTiles = { Level.ID_WALL };

	/**
	 * Creates a new bullet, sets the position and speed, and rotates it to the
	 * given angle
	 * 
	 * @param x
	 *            spawn x
	 * @param y
	 *            spawn y
	 * @param rotation
	 *            the angle in degrees in which the bullet will travel
	 * @param decay
	 *            the distance that might be travel
	 * @param damage
	 *            the damage which the bullet will do
	 */
	public Bullet(double x, double y, int rotation, int decay, int damage) {
		super();
		this.rotation = rotation;
		this.setSprite("bullet");
		this.decay = decay;
		setX(x - getFrameWidth() / 2);
		setY(y);
		setDirectionSpeed(rotation, BULLET_SPEED);
		this.damage = damage;
		Game.addPoints(1);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.gameengine.icadroids.objects.MoveableGameObject#collisionOccurred
	 * (java.util.List)
	 */
	public void collisionOccurred(List<Tile> collidedTiles) {

		boolean collision = false;

		for (int i = 0; i < collidedTiles.size(); i++)
			for (int y = 0; y < blockedTiles.length; y++)
				if (collidedTiles.get(i).getTileType() == blockedTiles[y])
					collision = true;

		if (collision)
			this.deleteThisGameObject();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.gameengine.icadroids.objects.MoveableGameObject#update()
	 */
	public void update() {
		super.update();
		decay--;
		if (decay < 0)
			this.deleteThisGameObject();
	}

	/**
	 * returns the damage the bullet does
	 * @return the damage the bullet does
	 */
	public int getDamage() {
		return damage;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.gameengine.icadroids.objects.GameObject#drawGameObject(android
	 * .graphics.Canvas)
	 */
	public void drawGameObject(Canvas canvas) {
		AnimatedSprite sprite = getSprite();
		if (sprite.getSprite() != null && isVisible) {
			canvas.save();
			int cenx = position.centerX();
			int ceny = position.centerY();
			canvas.rotate(rotation, cenx, ceny);
			canvas.drawBitmap(sprite.getSprite(), sprite.getCurrentFrameRectangle(), position, null);
			canvas.restore();
		}
	}
}

package android.topdown.game;

import java.util.List;

import android.gameengine.icadroids.objects.MoveableGameObject;
import android.gameengine.icadroids.objects.graphics.AnimatedSprite;
import android.gameengine.icadroids.tiles.Tile;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class Bullet extends MoveableGameObject {
	/**
	 * bij houden om punt van origin te weten
	 */
	private int startX, startY;
	/**
	 * afstand die gereisd mag worden
	 */
	private int decay, damage;
	private int rotation, speed;

	private static int[] blockedTiles = { Level.ID_WALL };

	public Bullet(double x, double y, int rotation, int speed, int decay, int damage) {
		super();
		this.rotation = rotation;
		this.setSprite("bullet");
		this.decay = decay;
		setX(x);
		setY(y);
		setDirectionSpeed(rotation, speed);
		this.damage = damage;
	}

	public void collisionOccurred(List<Tile> collidedTiles) {

		boolean collision = false;

		for (int i = 0; i < collidedTiles.size(); i++)
			for (int y = 0; y < blockedTiles.length; y++)
				if (collidedTiles.get(i).getTileType() == blockedTiles[y])
					collision = true;

		if (collision)
			this.deleteThisGameObject();
	}

	public void update() {
		super.update();

		// TODO decay, buiten veld, muur KAPOT
	}

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

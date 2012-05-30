package android.topdown.game;

import java.util.List;

import android.gameengine.icadroids.objects.MoveableGameObject;
import android.gameengine.icadroids.objects.graphics.AnimatedSprite;
import android.gameengine.icadroids.tiles.Tile;
import android.graphics.Canvas;

public class Bullet extends MoveableGameObject {
	/**
	 * afstand die gereisd mag worden
	 */
	private int decay, damage, rotation;

	private static int[] blockedTiles = { Level.ID_WALL };

	public Bullet(double x, double y, int rotation, int decay, int damage) {
		super();
		this.rotation = rotation;
		this.setSprite("bullet");
		this.decay = decay;
		setX(x);
		setY(y);
		setDirectionSpeed(rotation, 20);
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
		decay--;
		if(decay<0)
			this.deleteThisGameObject();
	}
	
	public int getDamage(){
		return damage;
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

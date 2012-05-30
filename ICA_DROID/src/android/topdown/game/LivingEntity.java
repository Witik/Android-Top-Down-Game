package android.topdown.game;

import java.util.List;

import android.gameengine.icadroids.objects.MoveableGameObject;
import android.gameengine.icadroids.objects.graphics.AnimatedSprite;
import android.gameengine.icadroids.tiles.Tile;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class LivingEntity extends MoveableGameObject {
	public static final int UP = 0;
	public static final int RIGHT = 1;
	public static final int DOWN = 2;
	public static final int LEFT = 3;

	private int hp, maxHp;
	private float rotation;
	private double speed;
	private int[] blockedTiles;

	public LivingEntity(int[] blockedTiles, int hp, double speed) {
		this.hp = hp;
		this.maxHp = hp;
		this.speed = speed;
		this.blockedTiles = blockedTiles;
		rotation = 180;
	}

	public void rotate(float rotation) {
		this.rotation += rotation;
		if (rotation > 360)
			rotation -= 360;
		else if (rotation < 0)
			rotation += 360;
	}

	public void setRotation(float rot) {
		this.rotation = rot % 360;
	}

	public float getRotation() {
		return rotation;
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

	public void heal(int hp) {
		if (hp + this.hp > maxHp)
			this.hp += hp;

	}

	public void hurt(int hp) {
		this.hp -= hp;
	}

	public int getHp() {
		return hp;
	}

	public int getmaxHp() {
		return maxHp;
	}

	public void collisionOccurred(List<Tile> collidedTiles) {

		boolean collision = false;

		for (int i = 0; i < collidedTiles.size(); i++)
			for (int y = 0; y < blockedTiles.length; y++)
				if (collidedTiles.get(i).getTileType() == blockedTiles[y])
					collision = true;

		if (collision)
			moveUpToTileSide(collidedTiles.get(0));
	}

	public void update() {
		super.update();
	}

	public void moveUp() {
		double dx = Math.sin(Math.toRadians(getRotation())) * getSpeeds();
		double dy = Math.cos(Math.toRadians(getRotation())) * getSpeeds();
		movePlayer((int) Math.round(dx), (int) Math.round(-dy));
	}

	public void moveDown() {
		double dx = Math.sin(Math.toRadians(getRotation())) * getSpeeds();
		double dy = Math.cos(Math.toRadians(getRotation())) * getSpeeds();
		movePlayer((int) Math.round(-dx), (int) Math.round(dy));
	}

	public void moveLeft() {
		double dx = Math.sin(Math.toRadians(getRotation())) * getSpeeds();
		double dy = Math.cos(Math.toRadians(getRotation())) * getSpeeds();
		movePlayer((int) Math.round(-dy), (int) Math.round(-dx));
	}

	public void moveRight() {
		double dx = Math.sin(Math.toRadians(getRotation())) * getSpeeds();
		double dy = Math.cos(Math.toRadians(getRotation())) * getSpeeds();
		movePlayer((int) Math.round(dy), (int) Math.round(dx));
	}

}

package android.topdown.game;

import java.util.List;

import android.gameengine.icadroids.engine.GameEngine;
import android.gameengine.icadroids.objects.GameObject;
import android.gameengine.icadroids.objects.MoveableGameObject;
import android.gameengine.icadroids.objects.graphics.AnimatedSprite;
import android.gameengine.icadroids.tiles.Tile;
import android.graphics.Canvas;

public abstract class LivingEntity extends MoveableGameObject {
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
		if (this.rotation + rotation < 0) {
			this.rotation += rotation + 360;
		} else if (this.rotation + rotation > 360) {
			this.rotation += rotation - 360;
		} else {
			this.rotation += rotation;
		}
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
		this.hp += hp;

	}

	public void hurt(int hp) {
		this.hp -= hp;
		if (this.hp <= 0) {
			die();
		}
	}

	public void die() {
		this.deleteThisGameObject();
	}

	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
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
		gameObjectCollision();
	}

	private void gameObjectCollision() {
		for (GameObject g : GameEngine.items) {
			if (g.position.intersect(position)) {// collison
				objectCollision(g);
			}
		}
	}

	protected abstract void objectCollision(GameObject g);

	public void moveForward() {
		double dx = Math.sin(Math.toRadians(getRotation())) * getSpeeds();
		double dy = Math.cos(Math.toRadians(getRotation())) * getSpeeds();
		movePlayer((int) Math.round(dx), (int) Math.round(-dy));
	}

	public void moveBackward() {
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

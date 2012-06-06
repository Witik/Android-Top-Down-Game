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

	/**
	 * @param blockedTiles array of tiles which the entity cannot move on to
	 * @param hp the amount of healthpoint the entity has
	 * @param speed the movement speed for this entity
	 */
	public LivingEntity(int[] blockedTiles, int hp, double speed) {
		this.hp = hp;
		this.maxHp = hp;
		this.speed = speed;
		this.blockedTiles = blockedTiles;
		rotation = 180;
	}

	/**
	 * rotate the entity
	 * @param rotation the amount of rotation you want
	 */
	public void rotate(float rotation) {
		if (this.rotation + rotation < 0) {
			this.rotation += rotation + 360;
		} else if (this.rotation + rotation > 360) {
			this.rotation += rotation - 360;
		} else {
			this.rotation += rotation;
		}
	}

	/**
	 * @param rot set the rotation to this amount
	 */
	public void setRotation(float rot) {
		this.rotation = rot % 360;
	}

	/**
	 * @return current rotation of the entity
	 */
	public float getRotation() {
		return rotation;
	}

	/* (non-Javadoc)
	 * @see android.gameengine.icadroids.objects.GameObject#drawGameObject(android.graphics.Canvas)
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

	/**
	 * @return het speed of this entity
	 */
	public double getSpeeds() {
		return speed;
	}

	/**
	 * Movement speed always a positive number
	 * @param speed the desired speed
	 */
	public void setSpeeds(double speed) {
		this.speed = Math.abs(speed);
	}

	/**
	 * heal the entity
	 * @param hp the amount the entity will be healed
	 */
	public void heal(int hp) {
		this.hp += hp;
	}

	/**
	 * hurt the entity
	 * @param hp the amount the entity will be healed
	 */
	public void hurt(int hp) {
		this.hp -= hp;
		if (this.hp <= 0) {
			die();
		}
	}

	/**
	 * The passing away of this entity
	 */
	public void die() {
		this.deleteThisGameObject();
	}

	/**
	 * @return the entity's current amount of health points
	 */
	public int getHp() {
		return hp;
	}

	/**
	 * Change the entity's amount of health points
	 * @param hp the amount you want
	 */
	public void setHp(int hp) {
		this.hp = hp;
	}

	/**
	 * @return the maximum amount of health
	 */
	public int getmaxHp() {
		return maxHp;
	}

	/* (non-Javadoc)
	 * @see android.gameengine.icadroids.objects.MoveableGameObject#collisionOccurred(java.util.List)
	 */
	public void collisionOccurred(List<Tile> collidedTiles) {

		boolean collision = false;

		for (int i = 0; i < collidedTiles.size(); i++)
			for (int y = 0; y < blockedTiles.length; y++)
				if (collidedTiles.get(i).getTileType() == blockedTiles[y])
					collision = true;

		if (collision)
			moveUpToTileSide(collidedTiles.get(0));
	}

	/* (non-Javadoc)
	 * @see android.gameengine.icadroids.objects.MoveableGameObject#update()
	 */
	public void update() {
		super.update();
		gameObjectCollision();
	}

	/**
	 * Similar to but then to check the collision between game object
	 * @see android.gameengine.icadroids.objects.MoveableGameObject#collisionOccurred(java.util.List)
	 */
	private void gameObjectCollision() {
		for (GameObject g : GameEngine.items) {
			if (g.position.intersect(position)) {// collison
				objectCollision(g);
			}
		}
	}

	/**
	 * methode called upon collision with an other game object
	 * @param g the game object we collided with
	 */
	protected abstract void objectCollision(GameObject g);

	/**
	 * move the entity forward relative to his facing
	 */
	public void moveForward() {
		double dx = Math.sin(Math.toRadians(getRotation())) * getSpeeds();
		double dy = Math.cos(Math.toRadians(getRotation())) * getSpeeds();
		movePlayer((int) Math.round(dx), (int) Math.round(-dy));
	}

	/**
	 * move the entity backward relative to his facing
	 */
	public void moveBackward() {
		double dx = Math.sin(Math.toRadians(getRotation())) * getSpeeds();
		double dy = Math.cos(Math.toRadians(getRotation())) * getSpeeds();
		movePlayer((int) Math.round(-dx), (int) Math.round(dy));
	}

	/**
	 * move the entity left relative to his facing
	 */
	public void moveLeft() {
		double dx = Math.sin(Math.toRadians(getRotation())) * getSpeeds();
		double dy = Math.cos(Math.toRadians(getRotation())) * getSpeeds();
		movePlayer((int) Math.round(-dy), (int) Math.round(-dx));
	}

	/**
	 * move the entity right relative to his facing
	 */
	public void moveRight() {
		double dx = Math.sin(Math.toRadians(getRotation())) * getSpeeds();
		double dy = Math.cos(Math.toRadians(getRotation())) * getSpeeds();
		movePlayer((int) Math.round(dy), (int) Math.round(dx));
	}

}

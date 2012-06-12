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
	 * @param blockedTiles
	 *            array of tiles which the entity cannot move on to
	 * @param hp
	 *            the amount of healthpoint the entity has
	 * @param speed
	 *            the movement speed for this entity
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
	 * 
	 * @param rotation
	 *            the amount of rotation you want
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
	 * set the rotation to the given amount of degrees
	 * 
	 * @param rot
	 *            the amount of degrees the LivingEntity will be rotated
	 */
	public void setRotation(float rot) {
		this.rotation = rot % 360;
	}

	/**
	 * Returns the current rotation of the LivingEntity in degrees
	 * 
	 * @return The current rotation of the LivingEntity
	 */
	public float getRotation() {
		return rotation;
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

	/**
	 * returns the speed of the LivingEntity.
	 * 
	 * @return the speed of the LivingEntity
	 */
	public double getSpeeds() {
		return speed;
	}

	/**
	 * Sets the movement speed to the absolute value of the given amount.
	 * 
	 * @param speed
	 *            The new speed of the living entity
	 */
	public void setSpeeds(double speed) {
		this.speed = Math.abs(speed);
	}

	/**
	 * Heals the entity by the given amount.
	 * 
	 * @param hp
	 *            the amount the entity will be healed
	 */
	public void heal(int hp) {
		this.hp += hp;
	}

	/**
	 * Hurts the entity by the given amount.
	 * 
	 * @param hp
	 *            the amount the entity will be hurt
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
	 * Returns the entity's current amount of hp
	 * @return the entity's current amount of health points
	 */
	public int getHp() {
		return hp;
	}

	/**
	 * Change the entity's amount of health points
	 * 
	 * @param hp
	 *            the amount the hp will be changed to
	 */
	public void setHp(int hp) {
		this.hp = hp;
	}

	/**
	 * returns the maximum amount of health of the entity
	 * @return the maximum amount of health
	 */
	public int getmaxHp() {
		return maxHp;
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
			moveUpToTileSide(collidedTiles.get(0));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.gameengine.icadroids.objects.MoveableGameObject#update()
	 */
	public void update() {
		super.update();
		gameObjectCollision();
	}

	/**
	 * Checks the collision between game objects
	 * 
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
	 * method called upon collision with an other game object
	 * 
	 * @param g
	 *            the game object this object collided with
	 */
	protected abstract void objectCollision(GameObject g);

	/**
	 * move the entity forward relative to its facing
	 */
	public void moveForward() {
		double dx = Math.sin(Math.toRadians(getRotation())) * getSpeeds();
		double dy = Math.cos(Math.toRadians(getRotation())) * getSpeeds();
		movePlayer((int) Math.round(dx), (int) Math.round(-dy));
	}

	/**
	 * move the entity backward relative to its facing
	 */
	public void moveBackward() {
		double dx = Math.sin(Math.toRadians(getRotation())) * getSpeeds();
		double dy = Math.cos(Math.toRadians(getRotation())) * getSpeeds();
		movePlayer((int) Math.round(-dx), (int) Math.round(dy));
	}

	/**
	 * move the entity left relative to its facing
	 */
	public void moveLeft() {
		double dx = Math.sin(Math.toRadians(getRotation())) * getSpeeds();
		double dy = Math.cos(Math.toRadians(getRotation())) * getSpeeds();
		movePlayer((int) Math.round(-dy), (int) Math.round(-dx));
	}

	/**
	 * move the entity right relative to its facing
	 */
	public void moveRight() {
		double dx = Math.sin(Math.toRadians(getRotation())) * getSpeeds();
		double dy = Math.cos(Math.toRadians(getRotation())) * getSpeeds();
		movePlayer((int) Math.round(dy), (int) Math.round(dx));
	}

}

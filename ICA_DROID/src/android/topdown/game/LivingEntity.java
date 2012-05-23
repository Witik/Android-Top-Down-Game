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

	private int hp, maxHp;
	private float rotation;
	private double speed;
	private int[] blockedTiles;

	public LivingEntity(int[] blockedTiles, int hp, double speed) {
		this.hp = hp;
		this.maxHp = hp;
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
	
	public void setRotation(float rot){
		this.rotation = rot%360;
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
	
	public void heal(int hp){
		if(hp+this.hp>maxHp)
		this.hp += hp;
		
	}
	
	public void hurt(int hp){
		this.hp -= hp;
	}

	public int getHp() {
		return hp;
	}

	public int getmaxHp() {
		return maxHp;
	}

	public void collisionOccurred(List<Tile> collidedTiles) {
		
		boolean collisionHorizontal = false;
		boolean collisionVertical = false;
		boolean collision = false;
		
		for (int i = 0; i < collidedTiles.size(); i++) {
			for (int y = 0; y < blockedTiles.length; y++)
				if(collidedTiles.get(i).getTileType()==blockedTiles[y])
					collision = true;
			
			
//			int collisionSide = getCollisionSide(collidedTiles.get(i));		
//			collidedTiles.get(i).setTileType(1);
//			if (collisionSide == 0 || collisionSide == 2) {
//				collisionVertical = true;
//			} else {
//				collisionHorizontal = true;
//			}
		}
		
		if (collision)
			moveUpToTileSide(collidedTiles.get(0));
		
		if (collisionHorizontal)
			moveUpToTileSide(collidedTiles.get(0));
		
		if (collisionVertical) 
			moveUpToTileSide(collidedTiles.get(0));
	}
	
	public void update(){
		super.update();
	}
	
	public void moveUp(){
		double dx = Math.sin(Math.toRadians(getRotation()))*getSpeeds();
		double dy = Math.cos(Math.toRadians(getRotation()))*getSpeeds();
		movePlayer((int)Math.round(dx),(int)Math.round(-dy));
	}
	
	public void moveDown(){
		double dx = Math.sin(Math.toRadians(getRotation()))*getSpeeds();
		double dy = Math.cos(Math.toRadians(getRotation()))*getSpeeds();
		movePlayer((int)Math.round(-dx),(int)Math.round(dy));
	}
	
	public void moveLeft(){
		double dx = Math.sin(Math.toRadians(getRotation()))*getSpeeds();
		double dy = Math.cos(Math.toRadians(getRotation()))*getSpeeds();
		movePlayer((int)Math.round(-dy),(int)Math.round(-dx));
	}
	
	public void moveRight(){
		double dx = Math.sin(Math.toRadians(getRotation()))*getSpeeds();
		double dy = Math.cos(Math.toRadians(getRotation()))*getSpeeds();
		movePlayer((int)Math.round(dy),(int)Math.round(dx));
	}
	
}

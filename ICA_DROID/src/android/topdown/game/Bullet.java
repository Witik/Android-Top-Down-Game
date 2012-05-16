package android.topdown.game;

import android.gameengine.icadroids.objects.MoveableGameObject;

public class Bullet extends MoveableGameObject {
	/**
	 * bij houden om punt van origin te weten
	 */
	private int startX,startY;
	/**
	 * afstand die gereisd mag wordne
	 */
	private int decay,damage;
	private int rotation, speed;
	public Bullet(double x, double y, int rotation,int speed, int decay,int damage){
		super();
		this.decay = decay;
		setX(x);
		setY(y);
		setDirectionSpeed(rotation,speed);
		this.damage = damage;
	}
	public void update(){
		super.update();
	}
}

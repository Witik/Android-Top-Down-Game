package android.topdown.game;

import android.gameengine.icadroids.engine.GameEngine;

public class Pistol extends Gun {

	public static final int MAX_AMMO = 255;
	public static final int DAMAGE = 10;
	
	public Pistol(int ammo){
		super("pistol", ammo, MAX_AMMO, DAMAGE, 10);
	}

	@Override
	public void shoot(double x, double y, int rotation) {
		if(canShoot()){
			GameEngine.items.add(new Bullet(x,y,(int) (rotation+Math.round((Math.random()*6)-3)), 10, 30, DAMAGE));
			shot();
		}
	}

}

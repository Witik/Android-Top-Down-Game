package android.topdown.game;

import android.gameengine.icadroids.engine.GameEngine;

public class Shotgun extends Gun {
	public static final int MAX_AMMO = 70;
	public static final int DAMAGE = 40;
	
	public Shotgun(int ammo){
		super("shotgun", ammo, MAX_AMMO, DAMAGE);
	}

	@Override
	public void shoot(double x, double y, int rotation) {
		GameEngine.items.add(new Bullet(x,y,(int) (rotation+Math.round((Math.random()*30)-15)), 10, 30,DAMAGE));
		GameEngine.items.add(new Bullet(x,y,(int) (rotation+Math.round((Math.random()*30)+15)), 10, 30,DAMAGE));
		GameEngine.items.add(new Bullet(x,y,(int) (rotation+Math.round((Math.random()*30)-30)), 10, 30,DAMAGE));
	}

}

package android.topdown.game;

import java.util.ArrayList;


public class Pistol extends Gun {

	ArrayList<Gun> guns;
	public static final int MAX_AMMO = 255;
	public static final int DAMAGE = 10;
	
	public Pistol(int ammo){
		super("pistol", ammo, MAX_AMMO, DAMAGE);
	}

	@Override
	public void shoot(double x, double y, int rotation) {
		new Bullet(x,y,(int) (rotation+Math.round((Math.random()*6)-3)), 10, 30, DAMAGE);
	}


}

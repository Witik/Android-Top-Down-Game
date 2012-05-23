package android.topdown.game;

public class Pistol extends Gun {
	public static final int MAX_AMMO = 255;
	public static final int DAMAGE = 10;
	
	public Pistol(int x, int y, int ammo) {
		super(x, y, "pistol", ammo, MAX_AMMO, DAMAGE);
	}
	
	public Pistol(int ammo){
		super("pistol", ammo, MAX_AMMO, DAMAGE);
	}

	@Override
	public void shoot(double x, double y, int rotation) {
		new Bullet(x,y,(int) (rotation+Math.round((Math.random()*6)-3)), 10, 30, DAMAGE);
	}


}

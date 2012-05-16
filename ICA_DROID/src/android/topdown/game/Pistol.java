package android.topdown.game;

public class Pistol extends Gun {

	public Pistol(int x, int y, int ammo) {
		super(x, y, "pistol", ammo, 255, 10);
	}
	
	public Pistol(int ammo){
		super("pistol", ammo, 255, 10);
	}

	@Override
	public void shoot(double x, double y, int rotation) {
		new Bullet(x,y,(int) (rotation+Math.round((Math.random()*6)-3)), 10, 30, 30);
	}


}

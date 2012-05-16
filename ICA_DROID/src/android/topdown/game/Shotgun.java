package android.topdown.game;

public class Shotgun extends Gun {

	public Shotgun(int x, int y, int ammo) {
		super(x, y, "shotgun", ammo, 70, 40);
	}
	
	public Shotgun(int ammo){
		super("shotgun", ammo, 70, 40);
	}

	@Override
	public void shoot(double x, double y, int rotation) {
		new Bullet(x,y,(int) (rotation+Math.round((Math.random()*30)-15)), 10, 30,50);
		new Bullet(x,y,(int) (rotation+Math.round((Math.random()*30)+15)), 10, 30,50);
		new Bullet(x,y,(int) (rotation+Math.round((Math.random()*30)-30)), 10, 30,50);
	}

}

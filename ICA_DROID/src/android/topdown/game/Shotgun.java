package android.topdown.game;

import android.gameengine.icadroids.engine.GameEngine;

public class Shotgun extends Gun {
	public static final int MAX_AMMO = 42;
	public static final int DAMAGE = 30;
	public static final int RATE = 35;

	public Shotgun(int ammo) {
		super("shotgun", ammo, MAX_AMMO, DAMAGE, RATE);
	}

	@Override
	public void shoot(double x, double y, int rotation) {
		if (canShoot()) {
			if (!isEmpty()) {
				SoundLib.play(SoundLib.SFX_SHOTGUN);
				GameEngine.items.add(new Bullet(x, y, (int) (rotation + Math.round((Math.random() * 30) - 15)), 15, DAMAGE));
				GameEngine.items.add(new Bullet(x, y, (int) (rotation + Math.round((Math.random() * 30) + 15)), 15, DAMAGE));
				GameEngine.items.add(new Bullet(x, y, (int) (rotation + Math.round((Math.random() * 30) + 15)), 15, DAMAGE));
				GameEngine.items.add(new Bullet(x, y, (int) (rotation + Math.round((Math.random() * 30) - 30)), 15, DAMAGE));
				GameEngine.items.add(new Bullet(x, y, (int) (rotation + Math.round((Math.random() * 30) - 30)), 15, DAMAGE));
				shot();
			}
		}
	}

}

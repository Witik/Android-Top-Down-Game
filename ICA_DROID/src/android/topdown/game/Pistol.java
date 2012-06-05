package android.topdown.game;

import android.gameengine.icadroids.engine.GameEngine;

public class Pistol extends Gun {

	public static final int MAX_AMMO = 255;
	public static final int DAMAGE = 25;
	public static final int RATE = 10;

	public Pistol(int ammo) {
		super("pistol", ammo, MAX_AMMO, DAMAGE, RATE);
	}

	@Override
	public void shoot(double x, double y, int rotation) {
		if (canShoot()) {
			if (!isEmpty()) {
				SoundLib.play(SoundLib.SFX_PISTOL);
				GameEngine.items.add(new Bullet(x, y, (int) (rotation + Math.round((Math.random() * 6) - 3)), 30, DAMAGE));
				shot();
			}
		}
	}

}

package android.topdown.game;

import android.gameengine.icadroids.engine.GameEngine;
import android.gameengine.icadroids.sound.GameSound;

public class Shotgun extends Gun {
	public static final int MAX_AMMO = 70;
	public static final int DAMAGE = 40;
	public static final int RATE = 35;

	public Shotgun(int ammo) {
		super("shotgun", ammo, MAX_AMMO, DAMAGE, RATE);
	}

	@Override
	public void shoot(double x, double y, int rotation) {
		if (canShoot()) {
			GameSound.playSound(Game.SHOTGUNSOUND, 0);
			GameEngine.items.add(new Bullet(x, y, (int) (rotation + Math.round((Math.random() * 30) - 15)), 15, DAMAGE));
			GameEngine.items.add(new Bullet(x, y, (int) (rotation + Math.round((Math.random() * 30) + 15)), 15, DAMAGE));
			GameEngine.items.add(new Bullet(x, y, (int) (rotation + Math.round((Math.random() * 30) - 30)), 15, DAMAGE));
			shot();
		}
	}

}

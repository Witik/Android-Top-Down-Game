package android.topdown.game;

import android.gameengine.icadroids.objects.GameObject;
import android.gameengine.icadroids.objects.graphics.Sprite;
import android.util.Log;

public class Zombie extends LivingEntity {
	private int damage;
	private static int[] blockedTiles = { Level.ID_WALL };
	/**
	 * pointer naar de player die kan gedeelt zijn tussen alle zombies geen
	 * issues met asycn memory acces dus dat gaat allemaal prima lukken
	 */
	private static Player player;
	private int numMoves, move;// voor de random movement te reguleren

	public Zombie(int hp, double speed, int damage, Player player) {
		super(blockedTiles, hp, speed);
		this.damage = damage;
		this.player = player;
		setSprite(getSpriteName());
		numMoves = move = 0;
	}

	private String getSpriteName() {
		switch ((int) (Math.random() * 3 + 1)) {
		case 1:
			return "zombie1";
		case 2:
			return "zombie2";
		case 3:
			return "zombie3";
		default:
			return "zombie4";
		}
	}

	public void update() {
		super.update();
		zombieNavigation();
		// rotate(0.1f);
	}

	private void zombieNavigation() {
		if (seesPlayer() || nearPlayer()) {
			clearRandom();
			if (turnTowardPlayer()) {
				moveUp();
			}
		} else {
			randomMove();
		}
	}

	private void clearRandom() {
		numMoves = move = 0;
	}

	private void randomMove() {
		if (numMoves == 0) {// shit verzinnen
			numMoves = (int) (Math.random() * 20);
			move = (int) (Math.random() * 19 + 1);
		} else {// we moeten de huidige move nog een keer doen
			numMoves--;
			if (move == 1) {
				moveUp();
			} else if (move == 2) {
				moveLeft();
			} else if (move == 3) {
				moveRight();
			} else if (move == 4) {
				moveDown();
			} else if (move == 5) {
				rotate(4f);
			} else if (move == 6) {
				rotate(-4f);
			}
		}
	}

	/**
	 * turn toward player
	 * 
	 * @return true if close enough
	 */
	private boolean turnTowardPlayer() {
		int angle = getAngleToPlayer();
		if (!(angle < 10 || angle > 350)) {
			if (angle <= 180) {// speler is rechts van ons
				rotate(7.5f);
			} else if (angle > 180) {// speler is links van ons
				rotate(-7.5f);
			}
		}
		if (angle < 20 || angle > 340) {// speler is dicht bij genoeg
			return true;
		}
		return false;
	}

	/**
	 * can i see the player?
	 * 
	 * @return true = yes
	 */
	private boolean seesPlayer() {
		if (getAngleToPlayer() > 320 || getAngleToPlayer() < 40) {
			return true;
		}
		return false;
	}

	private boolean nearPlayer() {
		float zx = getCenterX();
		float zy = getCenterY();
		float px = player.getCenterX();
		float py = player.getCenterY();

		int dy = Math.round(Math.abs(zy - py));
		int dx = Math.round(Math.abs(zx - px));

		if (Math.sqrt(dx * dx + dy * dy) < Level.TILE_SIZE * 4)
			return true;

		return false;
	}

	/**
	 * @return the angle of this to the player
	 */
	private int getAngleToPlayer() {
		float zx = getCenterX();
		float zy = getCenterY();
		float px = player.getCenterX();
		float py = player.getCenterY();

		double rot = getRotation();

		double angle = Math.toDegrees(Math.atan2(zy - py, zx - px));

		if (angle + (270 - rot) < 0)
			angle += (270 - rot) + 360;
		else if (angle + (270 - rot) > 360)
			angle += (270 - rot) - 360;
		else
			angle += (270 - rot);

		return (int) Math.round(angle);
	}

	@Override
	protected void objectCollision(GameObject g) {
		if (g instanceof Bullet) {
			hurt(((Bullet) g).getDamage());
			g.deleteThisGameObject();
			switch ((int) (Math.random() * 3)) {
			case 1:
				SoundLib.play(SoundLib.ZOMBIE_ZOMSHORT);
				break;
			case 2:
				SoundLib.play(SoundLib.ZOMBIE_ZOMSHORT2);
				break;
			default:
				SoundLib.play(SoundLib.ZOMBIE_ZOMSHORT3);
			}
			Log.i("collision", "zombievsbullet");
		} else if (g instanceof Zombie && !g.equals(this)) {

			rotate((float) (Math.random() * 7.5f - 3.75));
		}
	}

	public int getDamage() {
		return damage;
	}

	@Override
	public void die() {
		super.die();
		// TODO add custom die maybe met extra corpse class idk
	}
}

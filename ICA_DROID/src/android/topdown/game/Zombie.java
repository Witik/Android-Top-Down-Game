package android.topdown.game;

import android.gameengine.icadroids.objects.GameObject;
import android.util.Log;

public class Zombie extends LivingEntity {
	private int damage;
	private static int[] blockedTiles = { Level.ID_WALL };
	//static pointer shared by all zombies
	private static Player player;
	private int numMoves, move;
	private boolean noticedPlayer;

	/**
	 * @param hp amount of health point this zombie will have
	 * @param speed the speed this zombie will have
	 * @param damage the damage this zombie will be able to give per attack
	 * @param player a pointer to the current player so the zombie can eat his brains
	 */
	public Zombie(int hp, double speed, int damage, Player player) {
		super(blockedTiles, hp, speed);
		this.damage = damage;
		Zombie.player = player;
		setSprite(getSpriteName());
		numMoves = move = 0;
		noticedPlayer = false;
	}

	//chooses a random zombie sprite
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

	/* (non-Javadoc)
	 * @see android.topdown.game.LivingEntity#update()
	 */
	public void update() {
		super.update();
		zombieNavigation();
		if (playerWithin(12))
			makeSound();
	}

	//make a random sound
	private void makeSound() {
		if ((int) (Math.random() * 200) == 0)
			switch ((int) (Math.random() * 20)) {
			case 0:
				SoundLib.play(SoundLib.ZOMBIE_ZOMGRUNT);
				break;
			case 1:
				SoundLib.play(SoundLib.ZOMBIE_ZOMGRUNT2);
				break;
			case 2:
				SoundLib.play(SoundLib.ZOMBIE_ZOMGRUNT3);
				break;
			case 3:
				SoundLib.play(SoundLib.ZOMBIE_ZOMMOAN1);
				break;
			case 4:
				SoundLib.play(SoundLib.ZOMBIE_ZOMMOAN2);
				break;
			case 5:
				SoundLib.play(SoundLib.ZOMBIE_ZOMMOAN3);
				break;
			case 6:
				SoundLib.play(SoundLib.ZOMBIE_ZOMMOAN4);
				break;
			}
	}

	//decide how to move
	private void zombieNavigation() {
		if ((seesPlayer() && playerWithin(8)) || playerWithin(3) || noticedPlayer) {
			clearRandom();
			if (turnTowardPlayer()) {
				moveForward();
			}
			if (!playerWithin(12))
				noticedPlayer = false;
		} else {
			randomMove();
		}
	}

	//clear the random move counter
	private void clearRandom() {
		numMoves = move = 0;
	}

	//move somewhere at random
	private void randomMove() {
		if (numMoves == 0) {// shit verzinnen
			numMoves = (int) (Math.random() * 20) + 20;
			move = (int) (Math.random() * 12);
		} else {// we moeten de huidige move nog een keer doen
			numMoves--;
			switch (move) {
			case 0:
				moveForward();
				break;
			case 1:
				moveBackward();
				break;
			case 2:
				rotate(4f);
				break;
			case 3:
				rotate(-4f);
				break;
			}
		}
	}

	//turn towards the player
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

	//returns true if the player is in front of the zombie
	private boolean seesPlayer() {
		if (getAngleToPlayer() > 320 || getAngleToPlayer() < 40) {
			return true;
		}
		return false;
	}

	//returns true if the player is within a certain amount of tiles
	private boolean playerWithin(int tiles) {
		float zx = getCenterX();
		float zy = getCenterY();
		float px = player.getCenterX();
		float py = player.getCenterY();

		int dy = Math.round(Math.abs(zy - py));
		int dx = Math.round(Math.abs(zx - px));

		if (Math.sqrt(dx * dx + dy * dy) < Level.TILE_SIZE * tiles)
			return true;

		return false;
	}

	//returns the angle to the player
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

	/* (non-Javadoc)
	 * @see android.topdown.game.LivingEntity#objectCollision(android.gameengine.icadroids.objects.GameObject)
	 */
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
			if (!noticedPlayer)
				noticedPlayer = !noticedPlayer;
			Log.i("collision", "zombievsbullet");
		} else if (g instanceof Zombie && !g.equals(this)) {

			rotate((float) (Math.random() * 7.5f - 3.75));
		}
	}

	/**
	 * @return the amount of damage this zombie can do per attack
	 */
	public int getDamage() {
		return damage;
	}

	/* (non-Javadoc)
	 * @see android.topdown.game.LivingEntity#die()
	 */
	@Override
	public void die() {
		super.die();
		
		Game.ZombieDeath();

		switch ((int) (Math.random() * 40)) {
		case 2:
			SoundLib.play(SoundLib.FERDI_JIJGAATDOOD);
			break;
		case 3:
			SoundLib.play(SoundLib.FERDI_PUNCHINBALLS);
			break;
		case 4:
			SoundLib.play(SoundLib.FERDI_TAKEITLIKEABOSS);
			break;
		case 5:
			SoundLib.play(SoundLib.FERDI_THATSUREWASEASY);
			break;
		case 6:
			SoundLib.play(SoundLib.FERDI_TASTYBACONSTRIPS);
			break;
		case 7:
			SoundLib.play(SoundLib.FERDI_BANAAN);
			break;
		case 8:
			SoundLib.play(SoundLib.FERDI_BANAAN2);
			break;
		case 9:
			SoundLib.play(SoundLib.FERDI_BANAAN3);
			break;
		case 10:
			SoundLib.play(SoundLib.FERDI_BANAAN4);
			break;
		case 11:
			SoundLib.play(SoundLib.FERDI_BITCHPLEASE2);
			break;
		case 12:
			SoundLib.play(SoundLib.FERDI_ZONDERSOUND);
			break;
		default:
			if ((int) (Math.random() * 2) == 0)
				SoundLib.play(SoundLib.FERDI_HA);
			else
				SoundLib.play(SoundLib.FERDI_HMM);
		}
		Game.addPoints(25);
	}
}

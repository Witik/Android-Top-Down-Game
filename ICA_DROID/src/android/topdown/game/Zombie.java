package android.topdown.game;

import android.gameengine.icadroids.objects.GameObject;
import android.util.Log;

public class Zombie extends LivingEntity {
	private int damage;
	private static int[] blockedTiles = { Level.ID_WALL };
	/**
	 * pointer naar de player die kan gedeelt zijn tussen alle zombies
	 * geen issues met asycn memory acces dus dat gaat allemaal prima lukken
	 */
	private static Player player;
	public Zombie(int hp, double speed, int damage, Player player) {
		super(blockedTiles, hp, speed);
		this.damage = damage;
		this.player = player;
		setSprite("spritemissing");
	}

	public void update(){
		super.update();
		zombieNavigation();
	//	rotate(0.1f);
	}
	
	private void zombieNavigation() {
//		setDirectionSpeed(getAngleToPlayer(), 5);
//		if(seesPlayer()){
//			if(true){
//				turnTowardPlayer();
		System.out.println(getAngleToPlayer());
		if(nearPlayer())
			System.out.println("nearplayer");
//			}
//		}
		/*else{
			randomMove();
		}*/
	}

	private void randomMove() {
		int hold = (int) (Math.random()*30);
		switch(hold){
		case 0:
			moveUp();
			break;
		case 1:
			moveLeft();
			break;
		case 2:
			moveRight();
			break;
		case 3:
			moveDown();
			break;
		default:
			if(hold>3||hold<9){
				rotate((float) (Math.random()*7.5f-3.75));
			}
			break;
		}
	}

	/**
	 * turn toward player
	 * @return true if done
	 */
	private boolean turnTowardPlayer() {
		int target = getAngleToPlayer()-180;
		int cur = (int)getRotation();
		if(getAngleToPlayer()<320&&getAngleToPlayer()>40){
			rotate(7.5f);
		}
		return false;
	}

	/**
	 * can i see the player?
	 * @return true = yes
	 */
	private boolean seesPlayer() {
		if(getAngleToPlayer()>320||getAngleToPlayer()<40){
			return true;
		}
		return false;
	}
	
	private boolean nearPlayer() {
		float zx = getCenterX();
		float zy = getCenterY();
		float px = player.getCenterX();
		float py = player.getCenterY();
		
		int dy = Math.round(Math.abs(zy-py));
		int dx = Math.round(Math.abs(zx-px));
		
		if (Math.sqrt(dx*dx+dy*dy)<256)
			return true;
		
		return false;
	}
	/**
	 * @return the angle of this to the player
	 */
	private int getAngleToPlayer(){
		float zx = getCenterX();
		float zy = getCenterY();
		float px = player.getCenterX();
		float py = player.getCenterY();

		double rot = getRotation();
		
		double angle = Math.toDegrees(Math.atan2(zy - py, zx - px));
		
		if(angle+(270-rot)<0)
			angle += (270-rot)+360;
		else if(angle+(270-rot)>360)
			angle += (270-rot)-360;
		else
			angle += (270-rot);
			
		return (int) Math.round(angle);
	}

	@Override
	protected void objectCollision(GameObject g) {
		if(g instanceof Bullet){// oh nooz im hit
			hurt(((Bullet) g).getDamage());
			g.deleteThisGameObject();
			Log.i("collision","zombievsbullet");
		}
		else if (g instanceof Zombie&&!g.equals(this)){// lets try to leave each other
			rotate((float) (Math.random()*7.5f-3.75));
		}
	}
	public int getDamage(){
		return damage;
	}

	@Override
	public void die() {
		super.die();
		//TODO add custom die maybe met extra corpse class idk
	}
}

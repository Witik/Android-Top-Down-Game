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
	}
	
	private void zombieNavigation() {
		if(seesPlayer()){
			if(true){
				moveUp();
			}
		}
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
		if(this.getAngle(player)>350||this.getAngle(player)<10){// we staan recht voor de speler
			return true;
		}
		if(this.getAngle(player)<40){
			rotate(7.5f);
		}
		else if(this.getAngle(player)>320){
			rotate(-7.5f);
		}
		return false;
	}

	/**
	 * can i see the player?
	 * @return true = yes
	 */
	private boolean seesPlayer() {
		if(getAngleToPlayer()<40||getAngleToPlayer()>320){
			return true;
		}
		return false;
	}
	/**
	 * @return the angle of this to the player
	 */
	private int getAngleToPlayer(){
		float x = this.getCenterX() - player.getCenterX();
		float y = this.getCenterY() - player.getCenterY();
		double Return = Math.toDegrees(Math.atan(y/x));
		Return += this.getRotation();
		if(Return>=360){
			Return -= 360;
		}
		else if (Return<0){
			Return += 360;
		}
		return (int) Math.round(Return);
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

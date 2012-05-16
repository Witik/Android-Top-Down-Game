package faito.dajo;

import android.gameengine.icadroids.objects.GameObject;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Infobar extends GameObject {
	
	private Paint pt = new Paint();
	private double speed,xspeed,yspeed,xpos,ypos; 
	private float zoomFactor;
	private int viewportX, viewportY;

	public Infobar(){
		pt.setColor(Color.BLACK);
		pt.setTextSize(18);
	}
	
	@Override
	public void update() {
		super.update();
	}

	@Override
	public void drawGameObject(Canvas canvas) {
		super.drawGameObject(canvas);
		canvas.drawText("Speed: "+speed+" current speed: "+xspeed+", "+yspeed+" Location: ("+xpos+")("+ypos+")", viewportX+160, viewportY+80, pt);
		canvas.drawText("Zoomfactor: "+zoomFactor+" Location: ("+viewportX+")("+viewportY+")", viewportX+160, viewportY+100, pt);
	}
	
	public void setSpeed(double speed,double xspeed,double yspeed, float xpos, float ypos){
		this.speed = speed;
		this.xspeed = xspeed;
		this.yspeed = yspeed;
		this.xpos = xpos;
		this.ypos = ypos;
	}

	public void setPort(float zoomFactor, int viewportX, int viewportY) {
		this.zoomFactor = zoomFactor;
		this.viewportX = viewportX;
		this.viewportY = viewportY;
	}
}

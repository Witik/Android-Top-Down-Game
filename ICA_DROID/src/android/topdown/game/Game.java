package android.topdown.game;

import android.content.Intent;
import android.gameengine.icadroids.engine.GameEngine;
import android.gameengine.icadroids.input.OnScreenButtons;
import android.gameengine.icadroids.renderer.GameView;
import android.gameengine.icadroids.renderer.Viewport;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

public class Game extends GameEngine {
	
	private Player player;
	private Level level;
	Infobar info;
	Viewport port;

	public Game() {
		super();
		level = new Level("map");
		player = new Player();
		info = new Infobar();
		addPlayer(player, 0, 0);
		addGameObject(info);
		Viewport.useViewport = true;
	}
	@Override
	public void initialize() {
		super.initialize();

		info.isVisible = true;
		
		player.setSprite("player");
		player.setPosition(super.getScreenWidth() / 2 - player.getSprite().getFrameWidth() / 2, super.getScreenHeight() / 2 - player.getSprite().getFrameHeight() / 2);
		player.isVisible = true;
		
		OnScreenButtons.use = true;
		OnScreenButtons.feedback = true;
		OnScreenButtons.opacity = 100;
		
		GameView.BACKGROUND_COLOR = Color.BLACK;
		
		level.initialize();
		
		setTileMap(level.getGameTiles());
		
		port = Viewport.getInstance();
		port.setBounds(0, 0, 64*64, 64*64);
		port.setPlayer(player);
		port.setPlayerPositionTolerance(0.0, 0.0);
		port.setPlayerPositionOnScreen(Viewport.PLAYER_VCENTER | Viewport.PLAYER_HCENTER);
	}

	@Override
	public void update() {
		super.update();
		
		if (OnScreenButtons.start)
			if (OnScreenButtons.select)
				player.setPosition(super.getScreenWidth() / 2 - player.getSprite().getFrameWidth() / 2, super.getScreenHeight() / 2 - player.getSprite().getFrameHeight() / 2);
		
		info.setSpeed(player.getSpeeds(), player.getySpeed(), player.getxSpeed(), player.getCenterX(), player.getCenterY());
		info.setPort(port.getZoomFactor(), port.getViewportX(), port.getViewportY());
		info.setPosition(port.getViewportX()+200, port.getViewportY()+200);
	}

}
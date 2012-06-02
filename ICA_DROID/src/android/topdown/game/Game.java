package android.topdown.game;

import android.content.Context;
import android.gameengine.icadroids.engine.GameEngine;
import android.gameengine.icadroids.input.OnScreenButtons;
import android.gameengine.icadroids.renderer.GameView;
import android.gameengine.icadroids.renderer.Viewport;
import android.graphics.Color;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

public class Game extends GameEngine {

	private Player player;
	private Level level;
	private Infobar info;
	private Viewport port;

	private int numZom;

	public Game() {
		super();
		level = new Level("map");
		player = new Player();
		info = new Infobar(player);
		addPlayer(player, 0, 0);
		addGameObject(info);
		Viewport.useViewport = true;
	}

	@Override
	public void initialize() {
		super.initialize();

		info.isVisible = true;

		player.setPosition(super.getScreenWidth() / 2 - player.getSprite().getFrameWidth() / 2, super.getScreenHeight() / 2 - player.getSprite().getFrameHeight() / 2);
		player.isVisible = true;

		OnScreenButtons.use = true;
		OnScreenButtons.feedback = true;
		OnScreenButtons.opacity = 100;

		GameView.BACKGROUND_COLOR = Color.BLACK;

		level.initialize();

		setTileMap(level.getGameTiles());

		port = Viewport.getInstance();
		port.setBounds(0, 0, 64 * 64, 64 * 64);
		port.setPlayer(player);
		port.setPlayerPositionTolerance(0.0, 0.0);
		port.setPlayerPositionOnScreen(Viewport.PLAYER_VCENTER | Viewport.PLAYER_HCENTER);
		Display display = ((WindowManager) GameEngine.getAppContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
		info.setScreenSize(display.getHeight(), display.getWidth());

		new SoundLib();
	}

	@Override
	public void update() {
		super.update();

		if ((int) (Math.random() * 20) == 0 && numZom < 40)
			spawnZombie();

		if (OnScreenButtons.start)
			player.setPosition(player.getFullX() + 64, player.getFullY() + 64);
		if (OnScreenButtons.select)
			player.setPosition(player.getFullX() - 64, player.getFullY() - 64);

		info.setPort(port.getZoomFactor(), port.getViewportX(), port.getViewportY());
		info.setPosition(port.getViewportX() + 200, port.getViewportY() + 200);
	}

	private void spawnZombie() {
		int x = port.getRandomX(0);
		int y = port.getRandomY(0);

		if (level.getGameTiles().getTileArray()[x / Level.TILE_SIZE][y / Level.TILE_SIZE].getTileType() != Level.ID_WALL) {
			addGameObject(new Zombie(100, 1, 1, player), x, y);
			numZom++;

			Log.d("ZombieSpawn", "spawned at: (" + x / Level.TILE_SIZE + "," + y / Level.TILE_SIZE + ") on ID: " + level.getGameTiles().getTileArray()[x / Level.TILE_SIZE][y / Level.TILE_SIZE].getTileType());
			Log.d("PlayerLoc", "player at at: (" + (int) player.getFullX() / Level.TILE_SIZE + "," + (int) player.getFullY() / Level.TILE_SIZE + ") on ID: " + level.getGameTiles().getTileArray()[(int) player.getFullX() / Level.TILE_SIZE][(int) player.getFullY() / Level.TILE_SIZE].getTileType());
		}

	}
}
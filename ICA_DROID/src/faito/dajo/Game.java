package faito.dajo;

import android.gameengine.icadroids.engine.GameEngine;
import android.gameengine.icadroids.input.OnScreenButtons;
import android.gameengine.icadroids.objects.MoveableGameObject;
import android.gameengine.icadroids.objects.graphics.Sprite;
import android.gameengine.icadroids.renderer.GameView;
import android.gameengine.icadroids.renderer.Viewport;
import android.gameengine.icadroids.tiles.GameTiles;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;

public class Game extends GameEngine {

	private static final String TAG = "ButtonEnabled";
	private MoveableGameObject neko;
//	private final long[] pattern = { 200, 500, 200, 800, 200, 500, 200 };
	double speed = 10;
	Infobar info;
	Viewport port;

	public Game() {
		super();
		neko = new Player();
		info = new Infobar();
		addPlayer(neko, 0, 0);
		addGameObject(info, 200, 200);
		Viewport.useViewport = true;
		

	}

	@Override
	public void initialize() {
		super.initialize();

		info.isVisible = true;
		
		neko.setSprite("kat_01");
		neko.setPosition(super.getScreenWidth() / 2 - neko.getSprite().getFrameWidth() / 2, super.getScreenHeight() / 2 - neko.getSprite().getFrameHeight() / 2);
		neko.isVisible = true;
		
		OnScreenButtons.use = true;
		OnScreenButtons.feedback = true;
		OnScreenButtons.opacity = 100;
		
		GameView.BACKGROUND_COLOR = Color.BLACK;

		Sprite grass = new Sprite();
		Sprite wall = new Sprite();
		Sprite concrete = new Sprite();
		Sprite mapsprite = new Sprite();
		grass.loadSprite("grass");
		wall.loadSprite("wall");
		concrete.loadSprite("concrete");
		mapsprite.loadSprite("map");
		String[] tiles = { "grass", "wall", "concrete" };
		int[] colors = { 0xFFFFFFFF, 0xFF333333, 0xFF666666};
		int[][] map = genMap(mapsprite, colors);
		GameTiles gt = new GameTiles(tiles, map, 64);
		setTileMap(gt);
		
		port = Viewport.getInstance();
		port.setBounds(0, 0, 64*64, 64*64);
		port.setPlayer(neko);
		port.setPlayerPositionTolerance(0.0, 0.0);
		port.setPlayerPositionOnScreen(Viewport.PLAYER_VCENTER | Viewport.PLAYER_HCENTER);
	}

	@Override
	public void update() {
		super.update();
		
		if (OnScreenButtons.start)
			// vibrate(pattern);
			if (OnScreenButtons.select)
				neko.setPosition(super.getScreenWidth() / 2 - neko.getSprite().getFrameWidth() / 2, super.getScreenHeight() / 2 - neko.getSprite().getFrameHeight() / 2);
		if (OnScreenButtons.shoulderR)
			Log.d(TAG, "SHOULDER1 has been pressed");
		if (OnScreenButtons.shoulderL)
			Log.d(TAG, "SHOULDER2 has been pressed");
		if (OnScreenButtons.button1)
			neko.setSpeed(neko.getSpeed() - 3);
		if (OnScreenButtons.button2)
			neko.setSpeed(neko.getSpeed() + 3);
		if (OnScreenButtons.button3)
			port.setZoomFactor(port.getZoomFactor()-0.01f);
		if (OnScreenButtons.button4)
			port.setZoomFactor(port.getZoomFactor()+0.01f);
		info.setSpeed(neko.getSpeed(), neko.getySpeed(), neko.getxSpeed(), neko.getCenterX(), neko.getCenterY());
		info.setPort(port.getZoomFactor(), port.getViewportX(), port.getViewportY());
		info.setPosition(port.getViewportX()+200, port.getViewportY()+200);
	}

	private int[][] genMap(Sprite map, int[] colors) {
		Bitmap sprite = map.getSprite();
		int w = sprite.getWidth();
		int h = sprite.getHeight();
		int Map[][] = new int[h][w];
		for (int y = 0; y < h; y++) {
			for (int x = 0; x < w; x++) {
				int tile = -1;
				int px = sprite.getPixel(x, y);
				for(int i = 0; i<colors.length;i++){
					if(px==colors[i])
						tile = i;
				}
				Map[y][x] = tile;
			}
		}
		return Map;
	}

}
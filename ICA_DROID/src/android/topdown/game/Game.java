package android.topdown.game;

import android.content.Context;
import android.content.Intent;
import android.gameengine.icadroids.engine.GameEngine;
import android.gameengine.icadroids.forms.GameForm;
import android.gameengine.icadroids.forms.IFormInput;
import android.gameengine.icadroids.input.OnScreenButtons;
import android.gameengine.icadroids.renderer.GameView;
import android.gameengine.icadroids.renderer.Viewport;
import android.gameengine.icadroids.tiles.Tile;
import android.graphics.Color;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

public class Game extends GameEngine implements IFormInput{

	private static final int MAX_ZOMBIES = 80;
	
	private Player player;
	private Level level;
	private Infobar info;
	private Viewport port;

	private int numZom;
	
	private GameForm endGame;

	public Game() {
		super();
		level = new Level("map");
		player = new Player();
		info = new Infobar(player);
		addPlayer(player, 0, 0);
		addGameObject(info);
		Viewport.useViewport = true;
		if(SoundLib.loading())
			SoundLib.startLoad();
		endGame = null;
	}

	/* (non-Javadoc)
	 * @see android.gameengine.icadroids.engine.GameEngine#initialize()
	 */
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
		port.setBounds(0, 0, Level.TILE_SIZE * level.getGameTiles().getMapWidth(), Level.TILE_SIZE * level.getGameTiles().getMapHeigth());
		port.setPlayer(player);
		port.setPlayerPositionTolerance(0.0, 0.0);
		port.setPlayerPositionOnScreen(Viewport.PLAYER_VCENTER | Viewport.PLAYER_HCENTER);
		Display display = ((WindowManager) GameEngine.getAppContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
		info.setScreenSize(display.getHeight(), display.getWidth());
	}

	/* (non-Javadoc)
	 * @see android.gameengine.icadroids.engine.GameEngine#update()
	 */
	@Override
	public void update() {
		super.update();
		if(player.getHp()<1){//NOW IM GONE
			if(endGame==null){
				endGame = new GameForm("endgame", this, this);
			}
		}
		else{// STILL ALIVE
			if (numZom < MAX_ZOMBIES)// IM DOING SCIENCE
				spawnZombie();// AND IM STILL ALIVE
	
			if (OnScreenButtons.start)
				player.setPosition(player.getFullX() + 64, player.getFullY() + 64);
			if (OnScreenButtons.select)
				player.setPosition(player.getFullX() - 64, player.getFullY() - 64);
		}

		info.setPort(port.getViewportX(), port.getViewportY());
		info.setPosition(port.getViewportX() + 200, port.getViewportY() + 200);
	}

	private void spawnZombie() {

		Tile[][] tile = level.getGameTiles().getTileArray();
		int x = (int) (Math.random() * (tile.length*Level.TILE_SIZE));
		int y = (int) (Math.random() * (tile[0].length*Level.TILE_SIZE));

		if (notInWall(x,y)&&outSideViewport(x,y)) {
			addGameObject(new Zombie(100, 1, 5, player), x, y);
			numZom++;
			int xx = x/(Level.TILE_SIZE*Level.TILE_SIZE);
			int yy = y/(Level.TILE_SIZE*Level.TILE_SIZE);
			Log.d("ZombieSpawn", "spawned at: ("+x+","+y+") or ["+xx+"]["+yy+"] on ID: " + level.getGameTiles().getTileArray()[xx][yy].getTileType());
			Log.d("PlayerLoc", "player at at: (" + (int) player.getFullX() / Level.TILE_SIZE + "," + (int) player.getFullY() / Level.TILE_SIZE + ") on ID: " + level.getGameTiles().getTileArray()[(int) player.getFullX() / Level.TILE_SIZE][(int) player.getFullY() / Level.TILE_SIZE].getTileType());
		} else {
			Log.d("ZombieSpawn","failed");
		}
	}

	/**
	 * @param x
	 * @param y
	 * @return true if outside viewport else false
	 */
	private boolean outSideViewport(int x, int y) {
		if(((x+Level.TILE_SIZE) > port.getViewportX()||x < port.getViewportX() + getScreenWidth())&& 
				((y+Level.TILE_SIZE) > port.getViewportY()||y < port.getViewportY() + getScreenHeight())){
			return true;//TODO FIX THIS SHIT
		}
		return false;
	}

	private boolean notInWall(int x, int y) {
		Tile[][] tile = level.getGameTiles().getTileArray();
		int xx = x/Level.TILE_SIZE;
		int yy = y/Level.TILE_SIZE;
		if(xx+1>=tile.length||yy+1>=tile.length){
			Log.d("ZombieSpawn","attempting spawn at ("+x+","+y+") or ["+xx+"]["+yy+"]");
			return false;
		}
		int top = tile[xx][yy].getTileType();
		int right = tile[xx+1][yy].getTileType();
		int bottom = tile[xx+1][yy+1].getTileType();
		int left = tile[xx][yy+1].getTileType();

		Log.d("ZombieSpawn","attempting spawn at ("+x+","+y+") or ["+xx+"]["+yy+"] on ID: " + level.getGameTiles().getTileArray()[xx][yy].getTileType());
		return top!=Level.ID_WALL&&right!=Level.ID_WALL&&bottom!=Level.ID_WALL&&left!=Level.ID_WALL;
	}

	public void formElementClicked(View touchedElement) {
		if(touchedElement.getId()==R.id.newlevel){// goto new level 
			Toast.makeText(this, "new level", Toast.LENGTH_LONG).show();
			Settings.level += 1;
			this.finish();//TODO activity laten stoppen zonder dat de shit crashed
			startActivity(new Intent(this, Game.class));
		}
		else if (touchedElement.getId()==R.id.yes){
			Toast.makeText(this, "restart", Toast.LENGTH_LONG).show();
			this.finish();
			startActivity(new Intent(this, Game.class));
		}
		else if (touchedElement.getId()==R.id.endthegame){
			this.finish();
		}
	}
	public boolean onKeyDown(int keyCode, KeyEvent event) 
    {
		if(keyCode == KeyEvent.KEYCODE_BACK)
		{// af vangen van de back key
			return false;
		}
		return true;
    }
}
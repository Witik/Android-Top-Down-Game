package android.topdown.game;

import java.util.Vector;

import android.gameengine.icadroids.objects.graphics.Sprite;
import android.gameengine.icadroids.tiles.GameTiles;
import android.graphics.Bitmap;
import android.util.Log;

public class Level{

	public static final int TILE_SIZE = 64;
	public static final int ID_GRASS = 0;
	public static final int ID_WALL = 1;
	public static final int ID_ROAD = 2;
	public static final int ID_ROADSTRIPED = 3;
	public static final int ID_SIDEWALK = 4;
	public static final int ID_MEDKIT = 0;
	public static final int ID_PILLS = 1;
	public static final int ID_PISTOLAMMO = 2;
	public static final int ID_SHOTGUNAMMO = 3;
	public static final int ID_PISTOL = 4;
	public static final int ID_SHOTGUN = 5;

	private static final int COLOR_GRASS = 0xFF00FF00;
	private static final int COLOR_WALL = 0xFF333333;
	private static final int COLOR_ROAD = 0xFFFFFFFF;
	private static final int COLOR_ROADSTRIPED = 0xFF888888;
	private static final int COLOR_SIDEWALK = 0xFF666666;
	private static final int ARG_MEDKIT = 0xFFFF00;
	private static final int ARG_PILLS = 0xFF8000;
	private static final int ARG_PISTOLAMMO = 0xFF0080;
	private static final int ARG_SHOTGUNAMMO = 0xFF00FF;
	private static final int ARG_PISTOL = 0xFF8080;
	private static final int ARG_SHOTGUN = 0xFFFFFF;
	private static final String TILE_GRASS = "grass";
	private static final String TILE_WALL = "wall";
	private static final String TILE_ROAD = "road";
	private static final String TILE_ROADSTRIPED = "roadstriped";
	private static final String TILE_SIDEWALK = "sidewalk";

	private Sprite grass, wall, road, roadstriped, sidewalk, mapsprite, itemsprite;

	private String[] tiles = { TILE_GRASS, TILE_WALL, TILE_ROAD, TILE_ROADSTRIPED, TILE_SIDEWALK };
	private int[] colors = { COLOR_GRASS, COLOR_WALL, COLOR_ROAD, COLOR_ROADSTRIPED, COLOR_SIDEWALK };
	private int[] itemcolors = { ARG_MEDKIT, ARG_PILLS, ARG_PISTOLAMMO, ARG_SHOTGUNAMMO, ARG_PISTOL, ARG_SHOTGUN };

	private GameTiles gt;
	private String map, itemmap;

	/**
	 * @param map the file name of the map without the ".png" extension
	 */
	public Level(String map) {
		this.map = map;
		this.itemmap = map + "items";
	}

	/**
	 * @see android.gameengine.icadroids.objects.graphics.loadSprite(String resourceName)
	 * With a fallback sprite to indicate a missing sprite
	 */
	public Sprite loadSprite(Sprite sprite, String name) {
		try {
			sprite.loadSprite(name);
		} catch (Exception e) {
			System.out.println("Error");
			e.printStackTrace();
			sprite.loadSprite("spritemissing");
		}
		return sprite;
	}

	/**
	 * build the map from the level
	 */
	public void initialize() {
		grass = new Sprite();
		wall = new Sprite();
		road = new Sprite();
		roadstriped = new Sprite();
		sidewalk = new Sprite();
		mapsprite = new Sprite();
		itemsprite = new Sprite();
		loadSprite(grass, TILE_GRASS);
		loadSprite(wall, TILE_WALL);
		loadSprite(road, TILE_ROAD);
		loadSprite(roadstriped, TILE_ROADSTRIPED);
		loadSprite(sidewalk, TILE_SIDEWALK);
		loadSprite(mapsprite, map);
		loadSprite(itemsprite, itemmap);
		int[][] map = genMap(mapsprite, colors);
		addItems(itemsprite, itemcolors);
		gt = new GameTiles(tiles, map, TILE_SIZE);
	}
	
	/**
	 * @return the array with gametiles
	 */
	public GameTiles getGameTiles() {
		return gt;
	}

	private Vector<Pickup> addItems(Sprite items, int[] colors) {
		Bitmap sprite = itemsprite.getSprite();
		int w = sprite.getWidth();
		int h = sprite.getHeight();
		Vector<Pickup> itemVector = new Vector<Pickup>();
		for (int y = 0; y < h; y++) {
			for (int x = 0; x < w; x++) {
				int px = sprite.getPixel(x, y);
				int arg = (px >> 8) & 0xFFFFFF;
				for (int i = 0; i < itemcolors.length; i++)
					if (itemcolors[i] == arg) {
						Log.d(itemmap, itemcolors[i]+"");
						Pickup pickup = null;
						int xx = x * TILE_SIZE;
						int yy = y * TILE_SIZE;
						int respawnrate = (px & 0xFF)*45;
						switch (i) {
						case ID_MEDKIT:
							pickup = new HealthPack(xx, yy, HealthPack.TYPE_MEDKIT, respawnrate);
							break;
						case ID_PILLS:
							pickup = new HealthPack(xx, yy, HealthPack.TYPE_PILLS, respawnrate);
							break;
						case ID_PISTOLAMMO:
							pickup = new Ammo(xx, yy, Ammo.TYPE_PISTOL, Pistol.MAX_AMMO / 4, respawnrate);
							break;
						case ID_SHOTGUNAMMO:
							pickup = new Ammo(xx, yy, Ammo.TYPE_SHOTGUN, Shotgun.MAX_AMMO / 4, respawnrate);
							break;
						case ID_PISTOL:
							pickup = new GunPickup(xx, yy, GunPickup.TYPE_PISTOL, Pistol.MAX_AMMO / 5, respawnrate);
							break;
						case ID_SHOTGUN:
							pickup = new GunPickup(xx, yy, GunPickup.TYPE_SHOTGUN, Shotgun.MAX_AMMO / 5, respawnrate);
							break;
						}
						try {
							itemVector.add(pickup);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
			}
		}
		return itemVector;
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
				for (int i = 0; i < colors.length; i++) {
					if (px == colors[i])
						tile = i;
				}
				Map[y][x] = tile;
			}
		}
		return Map;
	}
}

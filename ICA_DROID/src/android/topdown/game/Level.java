package android.topdown.game;

import android.gameengine.icadroids.objects.graphics.Sprite;
import android.gameengine.icadroids.tiles.GameTiles;
import android.graphics.Bitmap;

public class Level {
	public static final String TILE_GRASS = "grass";
	public static final String TILE_WALL = "wall";
	public static final String TILE_ROAD = "road";
	public static final String TILE_ROADSTRIPED = "roadstriped";
	public static final String TILE_SIDEWALK = "sidewalk";
	public static final int ID_GRASS = 0;
	public static final int ID_WALL = 1;
	public static final int ID_ROAD = 2;
	public static final int ID_ROADSTRIPED = 3;
	public static final int ID_SIDEWALK = 4;
	public static final int COLOR_GRASS = 0xFFFFFFFF;
	public static final int COLOR_WALL = 0xFF333333;
	public static final int COLOR_ROAD = 0xFF666666;
	public static final int COLOR_ROADSTRIPED = 0xFF888888;
	public static final int COLOR_SIDEWALK = 0xFF777777;
	
	private Sprite grass, wall, road, roadstriped, sidewalk, mapsprite;

	String[] tiles = { TILE_GRASS, TILE_WALL, TILE_ROAD, TILE_ROADSTRIPED, TILE_SIDEWALK };
	int[] colors = { COLOR_GRASS, COLOR_WALL, COLOR_ROAD, COLOR_ROADSTRIPED, COLOR_SIDEWALK };

	private GameTiles gt;
	private String map;
	
	public Level(String map) {
		this.map = map;
	}
	
	public Sprite loadSprite(Sprite sprite, String name){
		try {
			sprite.loadSprite(name);
		} catch(Exception e) {
			System.out.println("Error");
			e.printStackTrace();
			sprite.loadSprite("spritemissing");
		}
		return sprite;
	}

	public void initialize() {
		grass = new Sprite();
		wall = new Sprite();
		road = new Sprite();
		roadstriped = new Sprite();
		sidewalk = new Sprite();
		mapsprite = new Sprite();
		loadSprite(grass,TILE_GRASS);
		loadSprite(wall,TILE_WALL);
		loadSprite(road,TILE_ROAD);
		loadSprite(roadstriped,TILE_ROADSTRIPED);
		loadSprite(mapsprite,map);
		int[][] map = genMap(mapsprite, colors);
		gt = new GameTiles(tiles, map, 64);
	}

	public GameTiles getGameTiles() {
		return gt;
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

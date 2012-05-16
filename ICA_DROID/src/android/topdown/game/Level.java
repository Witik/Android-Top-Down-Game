package android.topdown.game;

import android.gameengine.icadroids.objects.graphics.Sprite;
import android.gameengine.icadroids.tiles.GameTiles;
import android.graphics.Bitmap;

public class Level {
	GameTiles gt;
	String map;
	
	public Level(String map){
		this.map=map;
	}
	
	public void initialize(){
		Sprite grass = new Sprite();
		Sprite wall = new Sprite();
		Sprite concrete = new Sprite();
		Sprite mapsprite = new Sprite();
		try{
		grass.loadSprite("grass");
		wall.loadSprite("wall");
		concrete.loadSprite("concrete");
		mapsprite.loadSprite(map);
		} catch(Exception e){
			System.out.println("Error");
			e.printStackTrace();
		}
		String[] tiles = { "grass", "wall", "concrete" };
		int[] colors = { 0xFFFFFFFF, 0xFF333333, 0xFF666666};
		int[][] map = genMap(mapsprite, colors);
		gt = new GameTiles(tiles, map, 64);
	}
	
	public GameTiles getGameTiles(){
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

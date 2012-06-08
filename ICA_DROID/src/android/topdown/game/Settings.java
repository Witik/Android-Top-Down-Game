package android.topdown.game;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;

import android.content.Context;
import android.util.Log;

public class Settings {
	private static final String FILENAME = "profile.txt";
	public static String playername,playertown;
	public static int level;
	private static ArrayList<HighScore[]> scores = new ArrayList<HighScore[]>(0);
	/**
	 * load the saved data
	 * @param context the current context required for access to private files
	 * @return boolean if it worked or not
	 */
	public static boolean load(Context context){
		try{
			Log.d("readSettings", ""+readSettings(context));
			String[] settings= readSettings(context).split("\n");
			playername = settings[0];
			playertown = settings[1];
			try{
				level = Integer.parseInt(settings[2]);
			}
			catch(Exception e){
				Log.e("settings", "Couldn't parse level settings file might damaged error:"+e.toString());
			}
			try{
				scores = new ArrayList<HighScore[]>(0);
				int curLevel = 0;
				for(int z = 3; z<settings.length; z++){
					if(settings[z].contains("l")){// dit is een level regel
						scores.add(new HighScore[10]);
						curLevel++;
					}
					else{// het is een regel met save data
						HighScore[] h = scores.get(scores.size()-1);
						int curC = 0;
						for(HighScore hs:h){
							if(hs==null&&curC<scores.size()){
								h[curC] = hs;
								break;
							}
							curC++;
						}
					}
				}
			}
			catch(Exception e){
				Log.e("settings", "error parsing highscore:"+e.toString());
			}
		}
		catch(Exception e){
			Log.e("settings", "settings file maybe damaged");
		}
		LogHighScores();
		Log.d("settings", "name: "+playername+" playertown: "+ playertown+ " level: "+level);
		if(level>0&&playertown.length()>0&&playername.length()>0){
			return true;
		}
		else{
			return false;
		}
	}
	public static void LogHighScores() {
		Log.i("loiggun", "sdf");
		int level = 1;
		for(HighScore[] z: scores){
			Log.i("level", ""+level);
			for(HighScore s: z){
				if(s!=null)
					Log.i("score", ""+s.getSave());
			}
			level++;
		}
	}
	/**
	 * save the current settings to the private file location
	 * @param context the current context required for access to private files
	 */
	public static void save(Context context){
		sterilizeInput();
		String save = playername+"\n"
				+ playertown+ "\n"+
				level + "\n";
		for(HighScore[] zwam: scores){
			if(zwam.length>0)
				save+= "l\n";
			for(HighScore z:zwam){
				if(z!=null){
					save += z.getSave();
				}
			}
		}
		try{
			FileOutputStream fos = context.openFileOutput(FILENAME, Context.MODE_PRIVATE);
			fos.write(save.getBytes());
			fos.close();
		}
		catch(Exception e){
			Log.e("Settings", "Couldn't write Settings out: "+e.toString());
		}
	}
	private static void sterilizeInput() {
		playername.replaceAll(".", "");
		playertown.replaceAll(".", "");
		playername.replaceAll(" ", "");
		playertown.replaceAll(" ", "");
	}
	private static String readSettings(Context context){
		int ch;
		StringBuffer strContent = new StringBuffer("");
		try{
			FileInputStream fin = context.openFileInput(FILENAME);
			while ((ch = fin.read()) != -1){
				strContent.append((char) ch);
			}
			fin.close();
		}
		catch(Exception e){
			Log.e("settings", "Couldn't load settings:"+e.toString());
		}
		return strContent.toString();
	}
	/**
	 * place a high score for storage
	 * This methode checks if the score belongs in the highscore list
	 * *DOES NOT CALL SAVE YOU STILL NEED TO TO THAT YOUR SELF*
	 * @param level the level involevd
	 * @param score the score that has to be placed
	 */
	public static void pushHighScore(int score){
		if(scores.size()>=level){
			for(int z = 0; z<scores.size();z++){
				if(scores.get(level-1)[z]==null){//TODO tijdelijke implementatie die niet de scores vergelijkt
					HighScore[] s = scores.get(level-1);
					if(s[z]==null)
						s[z] = new HighScore(score,playername,playertown);
					scores.set(level-1, s);
				}
			}
		}
		else{
			scores.add(new HighScore[10]);
			HighScore[] s = scores.get(level-1);
			s[0] = new HighScore(score,playername,playertown);
			scores.set(level-1, s);
		}
	}
	/**
	 * @param level the level you want the hight score list from *can be null if not present*
	 * @return an array containing the highscores
	 */
	public static HighScore[] getHighScores(int level){
		if(scores.size()>=level){// ok we hebben al een score van dit level
			return scores.get(level-1);
		}
		else{
			return null;
		}
	}
}

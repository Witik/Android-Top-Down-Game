package android.topdown.game;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import android.content.Context;
import android.util.Log;

public class Settings {
	private static final String FILENAME = "profile.txt";
	public static String playername,playertown;
	public static int level;
	public static boolean load(Context context){
		try{
			String[] settings= readSettings(context).split("\n");
			Log.w("settings", "Settings:"+settings);
			playername = settings[0];
			playertown = settings[1];
			try{
				level = Integer.parseInt(settings[2]);
			}
			catch(Exception e){
				Log.e("settings", "Couldn't parse level settings file might damaged error:"+e.toString());
			}
		}
		catch(Exception e){
			Log.e("settings", "settings file maybe damaged");
		}
		Log.d("settings", "name: "+playername+" playertown: "+ playertown+ " level: "+level);
		if(level>0&&playertown.length()>0&&playername.length()>0){
			return true;
		}
		else{
			return false;
		}
	}
	public static void save(Context context){
		
		String save = playername+"\n"
				+ playertown+ "\n"+
				level;
		try{
			FileOutputStream fos = context.openFileOutput(FILENAME, Context.MODE_PRIVATE);
			fos.write(save.getBytes());
			fos.close();
		}
		catch(Exception e){
			Log.e("Settings", "Couldn't write Settings out: "+e.toString());
		}
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
}

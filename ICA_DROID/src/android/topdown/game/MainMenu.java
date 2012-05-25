package android.topdown.game;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.gameengine.icadroids.engine.GameEngine;
import android.gameengine.icadroids.forms.GameForm;
import android.gameengine.icadroids.forms.IFormInput;
import android.gameengine.icadroids.renderer.Viewport;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainMenu extends Activity {
	private Button submit,credits,load;
	private EditText naam,plaats;
	private Context context;
	public MainMenu() {
		super();
		context = this;
	}
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mainmenu);
		submit = (Button) findViewById(R.id.submitbuttonmainmenu);
		credits = (Button) findViewById(R.id.creditsbutton);
		load = (Button) findViewById(R.id.loadbutton);//TODO layout aanpassen hier om gebruik te maken van % breedte
		naam = (EditText) findViewById(R.id.namefield);
		plaats = (EditText) findViewById(R.id.plaatsfield);
		setClickListeneres();
	}
	public void onResume(){
		super.onResume();
		if(!Settings.load(context)){
			disableLoad();
		}
		else{
			enableLoad();
		}
	}
	private void setClickListeneres() {
		submit.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				if(v.getId()==R.id.submitbuttonmainmenu){
					Settings.playername = naam.getText().toString();
					Settings.playertown = plaats.getText().toString();
					Settings.level = 1;
					Settings.save(context);
					if(Settings.playername.length()>0&&Settings.playertown.length()>0){
						startGame();
					}
					else{
						Toast.makeText(getBaseContext(), "I asked you something? Y_NO_ANSWER?", Toast.LENGTH_LONG).show();
					}
				}
			}
		});
		credits.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				if(v.getId()==R.id.creditsbutton){
					Toast.makeText(getBaseContext(), "Credits", Toast.LENGTH_SHORT).show();
					startActivity(new Intent(context, Credits.class));
				}
			}
		});
		load.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				if(v.getId()==R.id.loadbutton){
					Settings.load(context);
					Toast.makeText(getBaseContext(), "load:"+Settings.playername+" town:"+Settings.playertown+" level: "+Settings.level, Toast.LENGTH_SHORT).show();
					if(Settings.load(context)){// ok we kunnen naar de game
						startGame();
					}
					else{
						Toast.makeText(getBaseContext(), "No profile present", Toast.LENGTH_LONG).show();
						disableLoad();
					}
				}
			}
		});
	}
	private void startGame(){
		startActivity(new Intent(context, Game.class));
	}
	private void disableLoad(){
		load.clearFocus();
		load.setEnabled(false);
	}
	public void enableLoad(){
		load.setEnabled(true);
	}
}

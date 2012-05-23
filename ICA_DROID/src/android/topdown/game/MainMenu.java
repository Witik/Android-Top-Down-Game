package android.topdown.game;

import android.app.Activity;
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

public class MainMenu extends Activity implements OnClickListener {
	private Button submit;
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
		naam = (EditText) findViewById(R.id.namefield);
		plaats = (EditText) findViewById(R.id.plaatsfield);
		submit.setOnClickListener(this);
	}
	public void onClick(View v) {
		if(v.getId()==R.id.submitbuttonmainmenu){
			Settings.playername = naam.getText().toString();
			Settings.playertown = plaats.getText().toString();
			if(Settings.playername.length()>0&&Settings.playertown.length()>0){
				startActivity(new Intent(this, Game.class));
			}
			else{
				Toast.makeText(getBaseContext(), "I asked you something? Y_NO_ANSWER?", Toast.LENGTH_LONG).show();
			}
		}
	}
}

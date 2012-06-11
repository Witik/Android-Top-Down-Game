package android.topdown.game;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainMenu extends Activity {
	private Button submit,credits,load;
	private EditText naam,plaats;
	private Context context;
	public static Settings settings;
	public MainMenu() {
		super();
		context = this;
		settings = new Settings(context);
	}
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mainmenu);
		submit = (Button) findViewById(R.id.submitbuttonmainmenu);
		credits = (Button) findViewById(R.id.creditsbutton);
		load = (Button) findViewById(R.id.loadbutton);
		naam = (EditText) findViewById(R.id.namefield);
		plaats = (EditText) findViewById(R.id.plaatsfield);
		setClickListeneres();
	}
	/* (non-Javadoc)
	 * @see android.app.Activity#onResume()
	 */
	public void onResume(){
		super.onResume();
		settings.open();
		if(!settings.hasUser()){
			disableLoad();
		}
		else{
			enableLoad();
		}
		settings.close();
	}
	// set all the onclicklisteneres
	private void setClickListeneres() {
		submit.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				if(v.getId()==R.id.submitbuttonmainmenu){
					settings.open();
					settings.setUsername(naam.getText().toString());
					settings.setTown(plaats.getText().toString());
					settings.close();
					if(settings.hasUser()){
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
					startActivity(new Intent(context, Credits.class));
				}
			}
		});
		load.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				if(v.getId()==R.id.loadbutton){
					settings.open();
					if(settings.hasUser()){// ok we kunnen naar de game
						startGame();
					}
					else{
						Toast.makeText(getBaseContext(), "No profile present", Toast.LENGTH_LONG).show();
						disableLoad();
					}
					settings.close();
				}
			}
		});
	}
	/**
	 * start the game with the set parameters
	 */
	private void startGame(){
		Settings.level = 1;
		startActivity(new Intent(context, Game.class));
	}
	/**
	 * disable the load button
	 */
	private void disableLoad(){
		load.clearFocus();
		load.setEnabled(false);
	}
	/**
	 * enable the load button
	 */
	private void enableLoad(){
		load.setEnabled(true);
	}
}

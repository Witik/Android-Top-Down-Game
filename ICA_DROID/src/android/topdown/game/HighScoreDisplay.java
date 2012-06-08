package android.topdown.game;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class HighScoreDisplay extends Activity 
{	
	private Button close;
	private EditText text;
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.credits);
		this.setTitle("High Scores for level:"+Settings.level);
		text = (EditText) this.findViewById(R.id.credittext);
		text.setTextSize(10f);
        close = (Button) this.findViewById(R.id.close);
        close.findFocus();
		close.setOnClickListener(new OnClickListener(){
			public void onClick(View arg0) {
	        	end();
			}
			
		}); 
		setText();
	}
	
	public void onStart(){
		super.onStart();
		setText();
	}
	private void setText() {
		text.setText("");
		int count = 1;
		HighScore[] scores = Settings.getHighScores(Settings.level);
		if(scores!=null){// check for all the null pointers
			for(HighScore s: scores){
				if(s!=null){// cause uncle android gets teh cramps from null pointers
					if(text.getText().length()>1){
						text.setText(text.getText()+"\n"+count+"." + s.getShow());
					}
					else{
						text.setText(count+"." + s.getShow());
					}
				}
				count++;
			}
		}
		else{
			text.setText("No scores have been set for this level.");
		}
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onKeyDown(int, android.view.KeyEvent)
	 */
	public boolean onKeyDown(int keyCode, KeyEvent event) 
    {
		if(keyCode == KeyEvent.KEYCODE_BACK)
		{
			end();
			return false;
		}
		return true;
    }
	/**
	 * finish the activity
	 */
	private void end()
	{
		HighScoreDisplay.this.finish();
	}
}
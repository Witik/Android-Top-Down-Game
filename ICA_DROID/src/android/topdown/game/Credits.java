package android.topdown.game;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * @author Thomas van putten
 * simple acitivity to display our credits dialog
 */
public class Credits extends Activity 
{	
	private Button close;
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.credits);
		this.setTitle(this.getTitle()+" Credits");
        close = (Button) this.findViewById(R.id.close);
        close.findFocus();
		close.setOnClickListener(new OnClickListener(){
			public void onClick(View arg0) {
	        	end();
			}
			
		}); 
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
		Credits.this.finish();
	}
}

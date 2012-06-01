package android.topdown.game;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

/**
 * @author Thomas van putten
 * eenvoudige activity voor de credits weer te geven in een dialog box
 */
public class Credits extends Activity 
{	
	EditText box;
	Button close;
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.credits);
		this.setTitle(this.getTitle()+" Credits");
        box = (EditText) this.findViewById(R.id.credittext);
        close = (Button) this.findViewById(R.id.close);
        close.findFocus();
		close.setOnClickListener(new OnClickListener(){
			public void onClick(View arg0) {
	        	end();
			}
			
		}); 
	}
	public boolean onKeyDown(int keyCode, KeyEvent event) 
    {
		if(keyCode == KeyEvent.KEYCODE_BACK)
		{
			end();
			return false;
		}
		return true;
    }
	private void end()
	{
		Credits.this.finish();
	}
}

package com.game.application.tictactoe;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.app.Activity;
import android.content.Intent;
public class AboutScreen extends Activity 
{
	RelativeLayout layout;
	Animation animation;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_about_screen);
		this.initialize();
		this.layout.setAnimation(animation);	
	}
	private void initialize()
	{
		this.layout=(RelativeLayout)this.findViewById(R.id.pad);
		this.animation=AnimationUtils.loadAnimation(this,R.anim.fade_in);
	}
	@Override
	public void onBackPressed() 
	{
		Intent intent=new Intent(this,HomeScreen.class);
		this.startActivity(intent);
		this.finish();		
	}
	public void gotoLink(View view)
	{
		String url="";
		Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_BROWSABLE);
	    switch(view.getId())
	        {
		       	case R.id.facebook:url="http://www.facebook.com";break;
		       	case R.id.gplus	  :url="http://plus.google.com";break;
		       	case R.id.twitter :url="http://www.twitter.com";break;
	        }
	    intent.setData(Uri.parse(url));
        startActivity(intent);
	}
}

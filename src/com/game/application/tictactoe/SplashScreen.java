package com.game.application.tictactoe;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;
public class SplashScreen extends Activity  
{
	Animation bounce;
	Animation rotate;
	TextView view;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash_screen);
		view=(TextView)this.findViewById(R.id.application_title);
	//	Typeface type=Typeface.createFromAsset(this.getAssets(),
		//        "sfwonder.ttf");
		//view.setTypeface(type);
		bounce=AnimationUtils.loadAnimation(this,R.anim.bounce);
		view.setAnimation(bounce);
		Handler handler=new Handler();

		handler.postDelayed(new Runnable()
		{			
			public void run() 
			{				
				Intent myIntent = new Intent(SplashScreen.this,HomeScreen.class);
				SplashScreen.this.startActivity(myIntent);
				finish();		
			}
		}, 1500);
	}
	@Override
	public void onBackPressed() 
	{
	}
}
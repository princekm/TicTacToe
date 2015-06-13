package com.game.application.tictactoe;
import com.game.application.tictactoe.helper.*;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.Toast;
public class HomeScreen extends Activity
{
	Animation fade_in;
	RelativeLayout layout,dialog_layout;
	MediaPlayer mp3_player;	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);	
		this.setContentView(R.layout
				.activity_home_screen);
		this.fade_in=AnimationUtils.loadAnimation(this,R.anim.fade_in);
		this.layout=(RelativeLayout)this.findViewById(R.id.menu_pad);
		layout.setAnimation(fade_in);
		dialog_layout=(RelativeLayout)this.findViewById(R.id.dialog_box);
		this.databaseLoader();
		this.loadHighScore();
	}
	private void makeSound()
	{
		try
		{
			mp3_player=MediaPlayer.create(this,R.raw.click);
			mp3_player.setVolume(0.8f,0.8f);
			mp3_player.start();
		}
		catch(Exception e)
		{		
		}
	}
	public void okTrigger(View view)
	{
		this.dialog_layout.setVisibility(this.dialog_layout.GONE);
		if(Saver.ifSoundPermission())
			this.makeSound();
		this.finish();

	}
	public void cancelTrigger(View view)
	{
		this.dialog_layout.setVisibility(this.dialog_layout.GONE);	
		this.dialog_layout.setVisibility(this.dialog_layout.GONE);
		if(Saver.ifSoundPermission())
			this.makeSound();
	}
	@Override
	public void onBackPressed()
	{

		if(this.dialog_layout.getVisibility()==dialog_layout.GONE)		
			this.dialog_layout.setVisibility(this.dialog_layout.VISIBLE);					
		else if(this.dialog_layout.getVisibility()==dialog_layout.VISIBLE)
			this.dialog_layout.setVisibility(this.dialog_layout.GONE);						
	}
	public void loadScreen(View view)
	{
		if(Saver.ifSoundPermission())
			this.makeSound();
		int id=view.getId();
		Intent intent=null;		
		switch(id)
		{
			case R.id.new_game:intent=new Intent(this,MainScreen.class);break;
			case R.id.high_scores:intent=new Intent(this,HighScoresScreen.class);break;
			case R.id.preferences:intent=new Intent(this,PreferencesScreen.class);break;
			case R.id.about:intent=new Intent(this,AboutScreen.class);break;
		}
		this.finish();
		this.startActivity(intent);
	}
	private void databaseLoader()
	{
		DatabaseManager manager=new DatabaseManager(this);		
		manager.getWritableDatabase();
		manager.close();
	}
	private void loadHighScore()
	{
		DatabaseManager manager=new DatabaseManager(this);		
		SQLiteDatabase db=manager.getReadableDatabase();
		String query="select score from highscores order by score desc";
		Toast.makeText(this,"highscore="+Saver.high_score,Toast.LENGTH_LONG);
		
		try
		{
			Cursor cursor=manager.getReadableDatabase().rawQuery(query,null);
			if(cursor.moveToFirst())
			{
				Saver.high_score=(int)cursor.getInt(0);	
			Log.d("highscore",""+cursor.getInt(0));
			}
			else
				Saver.high_score=0;	
			cursor.close();	
		}
		catch(Exception e)
		{
			Log.d("error",e.getMessage());
		}
		manager.close();
		
	}
	public void disappear(View view)
	{
		this.dialog_layout.setVisibility(this.dialog_layout.GONE);
	}
}

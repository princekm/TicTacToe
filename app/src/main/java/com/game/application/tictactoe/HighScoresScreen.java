package com.game.application.tictactoe;
import com.game.application.tictactoe.helper.DatabaseManager;
import com.game.application.tictactoe.helper.Saver;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
public class HighScoresScreen extends Activity 
{
	TextView textview;
	MediaPlayer mp3;
	RelativeLayout layout;
	Animation animation;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_high_scores_screen);
		this.initialize();
		this.loadHighScores();
		this.layout.setAnimation(animation);
	}
	private void initialize()
	{
		this.layout=(RelativeLayout)this.findViewById(R.id.wrapper);
		this.animation=AnimationUtils.loadAnimation(this,R.anim.fade_in);		
		textview=(TextView)this.findViewById(R.id.high_scorer);
	}
	@Override
	public void onBackPressed() 
	{
		Intent intent=new Intent(this,HomeScreen.class);
		finish();  
		startActivity(intent);    
	}	
	private  void loadHighScores()
	{
		DatabaseManager manager=new DatabaseManager(this);		
		SQLiteDatabase db=manager.getReadableDatabase();
		String row[][];
		String query="select * from highscores order by score desc,name";
		try
		{
			Cursor cursor=manager.getReadableDatabase().rawQuery(query,null);
			if(cursor!=null)
			{
				row=new String[cursor.getCount()][2];
				int i=0;
				if(cursor.moveToFirst())
				{		
				do
				{
					row[i][0]=cursor.getString(0);
					row[i][1]=cursor.getString(1);					
					i++;
				}while(cursor.moveToNext());
				for(i=0;i<row.length;++i)
					Log.d("info",row[i][0]+"|"+row[i][1]);
				LinearLayout outer=(LinearLayout)this.findViewById(R.id.score_pad);
				LinearLayout inner=null;
				TextView rank,name,point;
				for( i=0;i<row.length;++i)
				{
				inner=(LinearLayout)outer.getChildAt(i);
				rank=(TextView)inner.getChildAt(0);
				name=(TextView)inner.getChildAt(1);
				point=(TextView)inner.getChildAt(2);
				rank.setText(""+(i+1));
				name.setText(row[i][0]);
				point.setText(row[i][1]+"pts");
				}
				Log.d("child count",""+inner.getChildCount());
				}
				
			}
			cursor.close();	
		}
		catch(Exception e)
		{
			Log.d("error",e.getMessage());
		}
		manager.close();

	}
	public void clearHighScores(View view)
	{
		if(!this.textview.getText().toString().trim().equals(""))
		{
			DatabaseManager manager=new DatabaseManager(this);			
			try
			{
				manager.getWritableDatabase().execSQL("delete from highscores");
				Toast.makeText(this,"Cleared",Toast.LENGTH_SHORT).show();
				Saver.high_score=0;
			}
			catch(Exception e)
			{
				Log.d("error",e.getMessage());
			}
		manager.close();
		Intent intent=getIntent();
		finish();
		startActivity(intent);
		}
		if(Saver.ifSoundPermission())
			this.makesound();
	
	}
	private void makesound()
	{
		try
		{
			this.mp3=MediaPlayer.create(this,R.raw.click);
			this.mp3.start();
		}
		catch(Exception e)
		{
			Log.d("error",e.getMessage());
		}
	}
}

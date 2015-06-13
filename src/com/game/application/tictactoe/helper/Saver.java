package com.game.application.tictactoe.helper;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.widget.Toast;

import com.game.application.tictactoe.helper.DatabaseManager;

public class Saver 
{
	public static String player1;
	public static String player2;
	public static String player;
	public static String machine;
	public static int winner_color;
	public static int player1_win_count;
	public static int player2_win_count;
	public static final int HARD=1;
	public static final int EASY=0;	
	public static final int ON=1;
	public static final int OFF=0;
	public static final int PLAYER_VS_PLAYER=0;
	public static final int PLAYER_VS_MACHINE=1;
	public static int completed_row[][]=new int[3][2];
	public static int extra[][]=new int[3][2];
	public static int who_vs_whom;
	public static int extra_bit=OFF;
	public static int vibrate_status;
	public static int sound_status;
	public static int saved_matrix[][]=new int[3][3];
	public static int high_score;
	public static int level;
	static
	{
		player1_win_count=0;
		player2_win_count=0;
		player1="player1";
		player2="player2";
		player="player";
		machine="android";
		who_vs_whom=PLAYER_VS_MACHINE;	
		sound_status=OFF;
		vibrate_status=ON;		
		high_score=0;
		winner_color=-1;
		level=EASY;
		for(int i=0;i<3;++i)
			for(int j=0;j<3;++j)
				saved_matrix[i][j]=0;
	}
	public static boolean ifSoundPermission()
	{
		if(sound_status==ON)
			return true;
		return false;
	}
	public static boolean ifVibratePermission()
	{
		if(vibrate_status==ON)
			return true;
		return false;
	}
	public static void updateHighScores(Context context,String player,int score)
	{
		DatabaseManager manager=new DatabaseManager(context);
		String query="select * from highscores where name='"+player+"'";		
		try
		{					
		Cursor cursor=manager.getReadableDatabase().rawQuery(query,null);
		if(cursor.moveToFirst())
		{
				Log.d("name:",cursor.getString(1));
					query="update highscores set score="+score+" where name='"+player+"'";
				try
				{
				manager.getWritableDatabase().execSQL(query);	
				Log.d("updating",player);
				Toast.makeText(context,"New highscore",Toast.LENGTH_LONG).show();
				high_score=score;
				}
				catch(Exception e)
				{
				Log.d("error",e.getMessage());	
				}
		}
		else 
		{
			query="select count(name) from highscores";
			try
			{
			 cursor=manager.getReadableDatabase().rawQuery(query,null);
			 if(cursor.moveToFirst())
			 {
				 Integer count;
				 Log.d("count",cursor.getString(0));	
				 count=Integer.parseInt(cursor.getString(0));
				 if(count==5)
				 {
					 query="delete from highscores where score=(select min(score) from highscores)";
					 try
					 {					 				 
						 	manager.getWritableDatabase().execSQL(query);
					 }
					 catch(Exception e)
					 {
						 Log.d("error",e.getMessage());
					 }
				 }	 
			 }
			}
			catch(Exception e)
			{
				Log.d("error",e.getMessage());
			}
			query="insert into highscores(name,score) values('"+player+"',"+score+" )";
			try
			{
				manager.getWritableDatabase().execSQL(query);
				Log.d("inserting",player);
				Toast.makeText(context,"New highscore",Toast.LENGTH_LONG).show();
				high_score=score;
			}
			catch(Exception e)
			{
				Log.d("error",e.getMessage());
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
	
}

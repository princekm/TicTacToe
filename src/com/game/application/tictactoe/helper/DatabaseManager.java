package com.game.application.tictactoe.helper;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
public class DatabaseManager extends SQLiteOpenHelper
{
	/*STATIC VARIABLES (COMMON VARIABLES)*/
	private static String DB_NAME="my_db";
	private static int DB_VERSION=1;
	private static CursorFactory CURSOR_FACTORY=null;
	private static String SUBJECT_TABLE="subjects";
	/*DATABASE IS SET WITH PROPER SETTINGS*/
	public DatabaseManager(Context context) 
	{
		super(context,DB_NAME,CURSOR_FACTORY,DB_VERSION);
		Log.d("STATUS:","entered into database");
	}
	/*CREATING REQUIRED TABLES,CALLED BY FRAMEWORK*/
	@Override
	public void onCreate(SQLiteDatabase db)
	{		
		db.execSQL("create table highscores(name text primary key,score integer not null)");
		Log.d("STATUS:","entered into onCreate method");
	}
	/*CREATING REQUIRED TABLES WITH NEW VERSION,CALLED BY FRAMEWORK*/
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
	{
	db.execSQL("drop table if exists "+"sample");	
	onCreate(db);
	Log.d("STATUS:","entered into onUpgrade method");
	}
	
	
}

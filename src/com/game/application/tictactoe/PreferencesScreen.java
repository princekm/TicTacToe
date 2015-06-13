package com.game.application.tictactoe;
import android.media.MediaPlayer;
import android.os.Bundle;
import com.game.application.tictactoe.helper.Saver;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;
public class PreferencesScreen extends Activity 
{
	MediaPlayer clicker;
	Animation animation;
	RelativeLayout layout ;
	EditText input1,input2;
	RadioGroup vs_selector,vibrate_selector,sound_selector,level;
	RadioButton player_vs_player,player_vs_machine,sound_on,sound_off,vibrate_on,vibrate_off,easy,hard;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_preferences_screen);
		this.initialize();
		this.loadDefaultOptions();
		showInputs();
		this.layout.setAnimation(animation);
	}
	private void initialize()
	{
		this.player_vs_player=(RadioButton)this.findViewById(R.id.player_vs_player);
		this.player_vs_machine=(RadioButton)this.findViewById(R.id.player_vs_machine);
		this.sound_on=(RadioButton)this.findViewById(R.id.sound_on);
		this.sound_off=(RadioButton)this.findViewById(R.id.sound_off);
		this.vibrate_on=(RadioButton)this.findViewById(R.id.vibrate_on);
		this.vibrate_off=(RadioButton)this.findViewById(R.id.vibrate_off);
		this.easy=(RadioButton)this.findViewById(R.id.easy);
		this.hard=(RadioButton)this.findViewById(R.id.hard);		
		this.vs_selector=(RadioGroup)this.findViewById(R.id.who_vs_whom);
		this.level=(RadioGroup)this.findViewById(R.id.level);
		this.vibrate_selector=(RadioGroup)this.findViewById(R.id.vibrate_on_off);
		this.sound_selector=(RadioGroup)this.findViewById(R.id.sound_on_off);
		this.input1=(EditText)this.findViewById(R.id.input1);
		this.input2=(EditText)this.findViewById(R.id.input2);
		this.animation=AnimationUtils.loadAnimation(this,R.anim.fade_in);
		this.layout=(RelativeLayout)this.findViewById(R.id.pad);
	}
	private void loadDefaultOptions()
	{
		if(Saver.who_vs_whom==Saver.PLAYER_VS_PLAYER)
			this.player_vs_player.setChecked(true);
		else
		{
			this.player_vs_machine.setChecked(true);
			this.level.setVisibility(level.VISIBLE);
			if(Saver.level==Saver.EASY)
				this.easy.setChecked(true);
			else
				this.hard.setChecked(true);
		}
		if(Saver.ifVibratePermission())
			this.vibrate_on.setChecked(true);
		else
			this.vibrate_off.setChecked(true);
		if(Saver.ifSoundPermission())
			this.sound_on.setChecked(true);
		else
			this.sound_off.setChecked(true);
		if(this.player_vs_player.isChecked())
		{
			this.input1.setText(Saver.player1);
			this.input2.setText(Saver.player2);
		}
		else
		{
			this.input1.setText(Saver.player);
			this.input2.setText(Saver.machine);				
		}
		if(Saver.level==Saver.EASY)
			this.easy.setChecked(true);
		else
			this.hard.setChecked(true);
	}
	private void makeSound()
	{
		try
		{
			clicker=MediaPlayer.create(this,R.raw.click);		
			clicker.start();
		}
		catch(Exception e)
		{		
		}
	}
	@Override
	public void onBackPressed() 
	{
		Intent intent=new Intent(this,HomeScreen.class);
		this.finish();
		this.startActivity(intent);		
	}
	public void savePreferences(View view)
	{
		int	first_switch  = this.vs_selector.getCheckedRadioButtonId();
		int	second_switch = this.vibrate_selector.getCheckedRadioButtonId();
		int third_switch  = this.sound_selector.getCheckedRadioButtonId();
		int fourth_switch = this.level.getCheckedRadioButtonId();
		switch(first_switch)
		{
			case R.id.player_vs_player:Saver.who_vs_whom=Saver.PLAYER_VS_PLAYER;break;
			case R.id.player_vs_machine:Saver.who_vs_whom=Saver.PLAYER_VS_MACHINE;break;
		}
		switch(second_switch)
		{
			case R.id.vibrate_on:Saver.vibrate_status=Saver.ON;break;
			case R.id.vibrate_off:Saver.vibrate_status=Saver.OFF;break;
		}
		switch(third_switch)
		{
			case R.id.sound_on:Saver.sound_status=Saver.ON;break;
			case R.id.sound_off:Saver.sound_status=Saver.OFF;break;
		}
		switch(fourth_switch)
		{
			case R.id.easy:Saver.level=Saver.EASY;break;
			case R.id.hard:Saver.level=Saver.HARD;break;
		}		
		if(this.vs_selector.getCheckedRadioButtonId()==R.id.player_vs_player)
		{
			if(!this.input1.getText().toString().trim().equals(this.input2.getText().toString().trim()))
			{
				if(!this.input1.getText().toString().trim().equals(""))
					Saver.player1=""+this.input1.getText().toString().trim();
				if(!this.input2.getText().toString().trim().equals(""))
					Saver.player2=""+this.input2.getText().toString().trim();
			}
		}
		else
		{
			if(!this.input1.getText().toString().trim().equals("")&&!this.input1.getText().toString().trim().equals(Saver.machine))
				Saver.player =""+this.input1.getText().toString().trim();
			if(!this.input2.getText().toString().trim().equals(""))
				Saver.machine=""+this.input2.getText().toString().trim();
		}
		Toast.makeText(this,"Saved",Toast.LENGTH_SHORT).show();	
		if(Saver.ifSoundPermission())
			makeSound();
	
	}
	public void tryMakingSound(View view)
	{
		if(Saver.ifSoundPermission())
			makeSound();	
	}
	public void showInputs(View view)
	{
		if(Saver.sound_status==Saver.ON)
			makeSound();
		if(view.getId()==R.id.player_vs_player)
		{
			input1.setHint("Player1 name");
			input2.setHint("Player2 name");
			input1.setText(Saver.player1);
			input2.setText(Saver.player2);
			input2.setEnabled(true);
			this.level.setVisibility(level.GONE);
		}
		else
		{
			input1.setHint("Player  name");
			input2.setHint("Machine name");			
			input1.setText(Saver.player);
			input2.setText(Saver.machine);	
			input2.setEnabled(false);
			this.level.setVisibility(level.VISIBLE);
					}			
	}
	public void showInputs()
	{

		if(this.vs_selector.getCheckedRadioButtonId()==R.id.player_vs_player)
		{
			input1.setHint("Player1 name");
			input2.setHint("Player2 name");
			input1.setText(Saver.player1);
			input2.setText(Saver.player2);				
			input2.setEnabled(true);
			this.level.setVisibility(level.GONE);
		}
		else
		{
			input1.setHint("Player  name");
			input2.setHint("Machine name");
			input1.setText(Saver.player);
			input2.setText(Saver.machine);
			input2.setEnabled(false);
			this.level.setVisibility(level.VISIBLE);
		}
	}
}
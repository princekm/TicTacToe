package com.game.application.tictactoe;

import java.util.Random;

import com.game.application.tictactoe.R.id;
import com.game.application.tictactoe.helper.Saver;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewPager.LayoutParams;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainScreen extends Activity {
	private LinearLayout player_switch_pad, button_pad;
	private Animation fade_in;
	private RelativeLayout pad, box, dialog_layout;
	private TextView title, player_no, player1_win_view, player2_win_view;
	private View player_color, child, previous_view_of_player,
			previous_view_of_machine;
	private MediaPlayer mp3_player, clicker;
	private int color;
	private int success_bit = 0;
	private int filled_count = 0;
	private int fill_matrix[][] = new int[3][3];
	private final int PLAYER_1 = 0;
	private final int PLAYER_2 = 1;
	private final int PLAYER = 1;
	private final int MACHINE = 2;
	private final int CORNER = 0;
	private final int CENTER = 1;
	private final int OTHERS = 2;
	private String player;
	private String[] id_names = new String[] { "m11", "m12", "m13", "m21",
			"m22", "m23", "m31", "m32", "m33" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_main_screen);
		this.initialize();
		this.fade_in = AnimationUtils.loadAnimation(this, R.anim.fade_in);
		this.pad.startAnimation(fade_in);
		this.clearFillMatrix();
		this.color = this.tosser();
		// Toast.makeText(this,"player"+(color+1)+" win toss",Toast.LENGTH_SHORT).show();
		this.loadInitialSettings();
		this.setWinCounts();
		this.setVolumeControlStream(AudioManager.STREAM_MUSIC);
		
	}
	



	private void loadInitialSettings() {
		Drawable drawable = null;
		if (Saver.who_vs_whom == Saver.PLAYER_VS_PLAYER) {
			if (this.color == this.PLAYER_1)
				this.player = Saver.player1;
			else
				this.player = Saver.player2;
		} else {
			if (this.color == 0)
				this.player = Saver.player;
			else
				this.player = Saver.machine;

		}
		if (this.color == this.PLAYER_1)
			drawable = this.getResources().getDrawable(R.drawable.custom_ball1);
		else
			drawable = this.getResources().getDrawable(R.drawable.custom_ball);
		this.player_no.setText(this.player + "'s turn");
		this.player_color.setBackgroundDrawable(drawable);
		this.player_switch_pad.setVisibility(this.player_switch_pad.VISIBLE);
		if (Saver.winner_color == -1)
			Toast.makeText(this, this.player + " will start the game",
					50).show();
		else
			Toast.makeText(this, this.player + " will continue the game",
					50).show();

		if (this.color == 1 && Saver.who_vs_whom == Saver.PLAYER_VS_MACHINE)
			this.ifMachine(null);
	}

	private String[] filterList(String List[]) {
		return null;
	}

	private void initialize() {
		this.pad = (RelativeLayout) this.findViewById(R.id.pad);
		this.dialog_layout = (RelativeLayout) this
				.findViewById(R.id.dialog_pad);
		this.player_color = (View) this.findViewById(R.id.color_of_player);
		this.player_no = (TextView) this.findViewById(R.id.player_no);
		this.button_pad = (LinearLayout) this.findViewById(R.id.the_button_pad);
		this.player_switch_pad = (LinearLayout) this
				.findViewById(R.id.player_switch_pad);
		this.player1_win_view = (TextView) this.findViewById(R.id.player1_win);
		this.player2_win_view = (TextView) this.findViewById(R.id.player2_win);
		this.previous_view_of_player = null;
		this.previous_view_of_machine = null;
		this.title = (TextView) this.findViewById(R.id.dialog_title_view);
	}

	private int tosser() {
		if (Saver.winner_color == -1) {
			Random number = new Random();
			int random_number = number.nextInt(2);
			return random_number;
		}
		return Saver.winner_color;
	}

	private void successBlink() {
		String id[] = new String[3];
		for (int i = 0; i < 3; ++i) {
			id[i] = "";
			id[i] += "m" + (Saver.completed_row[i][0] + 1)
					+ (Saver.completed_row[i][1] + 1);
		}
		for (int i = 0; i < 3; ++i)
			this.selector(id[i]);
		if (Saver.extra_bit == 1) {
			for (int i = 0; i < 3; ++i) {
				id[i] = "";
				id[i] += "m" + (Saver.extra[i][0] + 1)
						+ (Saver.extra[i][1] + 1);
			}
			for (int i = 0; i < 3; ++i)
				this.selector(id[i]);
		}
	}

	private void selector(String select) {
		RelativeLayout parent = null;
		Drawable drawable = this.getResources().getDrawable(
				R.drawable.static_background);
		if (select.equals("m11"))
			parent = (RelativeLayout) this.findViewById(R.id.m11).getParent();
		else if (select.equals("m12"))
			parent = (RelativeLayout) this.findViewById(R.id.m12).getParent();
		else if (select.equals("m13"))
			parent = (RelativeLayout) this.findViewById(R.id.m13).getParent();
		else if (select.equals("m21"))
			parent = (RelativeLayout) this.findViewById(R.id.m21).getParent();
		else if (select.equals("m22"))
			parent = (RelativeLayout) this.findViewById(R.id.m22).getParent();
		else if (select.equals("m23"))
			parent = (RelativeLayout) this.findViewById(R.id.m23).getParent();
		else if (select.equals("m31"))
			parent = (RelativeLayout) this.findViewById(R.id.m31).getParent();
		else if (select.equals("m32"))
			parent = (RelativeLayout) this.findViewById(R.id.m32).getParent();
		else if (select.equals("m33"))
			parent = (RelativeLayout) this.findViewById(R.id.m33).getParent();
		parent.setBackgroundDrawable(drawable);
		Animation blink = AnimationUtils.loadAnimation(this, R.anim.blink);
		parent.getChildAt(0).setAnimation(blink);
	}

	private void ifMachine(View view) {
		Drawable next_drawable = null;
		Drawable drawable = null;
		String id_name = this.randomGenerator(this.returnUnfilledPositions());
		int flag = 0;
		if (Saver.level == Saver.HARD || Saver.level == Saver.EASY) {
			if (this.previous_view_of_player != null) {
				if (fill_matrix[1][1] == 0 ) 
				{
					id_name = "m22";
					flag = 1;
				}
			}
			if (this.previous_view_of_machine != null && flag == 0
					&& Saver.level == Saver.HARD) 
			{
				if (this.ifPlayerCanWin(this
						.winMoveOfPlayer(this.MACHINE, null))) 
				{
					int result[] = this.winMoveOfPlayer(this.MACHINE, null);
					if (result[0] != -1 && result[2] == -1) 
					{
						flag = 1;
						id_name = "m" + (result[0] + 1) + (result[1] + 1);
					}
					else if (result[0] != -1 && result[2] != -1) 
					{
						String id1, id2;
						id1 = "m" + (result[0] + 1) + (result[1] + 1);
						id2 = "m" + (result[0] + 1) + (result[1] + 1);
						id_name = this.randomGenerator(new String[] { id1, id2 });
						flag = 1;
					}
				}
				if (flag == 0) 
				{
					String result[] = this.returnUnfilledPositions();
					for (int i = 0; i < result.length; ++i)
						if (this.ifPlayerCanWin(this.winMoveOfPlayer(this.MACHINE, result[i]))) 
						{
							id_name = result[i];
							flag = 1;
							break;
						}
					Log.d("check","point-1");
				}
			}
			if (this.previous_view_of_player != null && flag == 0) 
			{
				Log.d("check","point00");
				if (fill_matrix[0][0] == 0 && fill_matrix[0][2] == 0
						&& fill_matrix[2][0] == 0 && fill_matrix[2][2] == 0
						&& Saver.level == Saver.HARD)
					id_name = this.randomGenerator(new String[] { "m11", "m13",
							"m31", "m33" });
				if (this.ifPlayerCanWin(this.winMoveOfPlayer(this.PLAYER, null))) 
				{
					/*if player can win in next move*/
					int result[] = this.winMoveOfPlayer(this.PLAYER, null);
					if (result[0] != -1 && result[2] == -1)
						id_name = "m" + (result[0] + 1) + (result[1] + 1);
					else if (result[0] != -1 && result[2] != -1) 
					{
						String id1, id2;
						id1 = "m" + (result[0] + 1) + (result[1] + 1);
						id2 = "m" + (result[0] + 1) + (result[1] + 1);
						id_name = this
								.randomGenerator(new String[] { id1, id2 });
					}
				} 
				else if (this.filled_count == 2 && fill_matrix[1][1] == 2) 
				{
					/*if player cannot win in next move*/
					if (fill_matrix[0][0] == 1 || fill_matrix[0][2] == 1
							|| fill_matrix[2][0] == 1 || fill_matrix[2][2] == 1) 
					{
						String array[] = { "m12", "m21", "m23", "m32" };
						id_name = this.randomGenerator(array);
					}
					Log.d("check","point0");

				} 
				else if (this.filled_count == 3 && fill_matrix[1][1] == 2) 
				{
					/*if player started the game*/
					if ((fill_matrix[0][0] == 1 && fill_matrix[2][2] == 1)
							|| (fill_matrix[2][0] == 1 && fill_matrix[0][2] == 1)) 
					{
						/*plus random array*/
						String array[] = { "m12", "m21", "m23", "m32" };
						id_name = this.randomGenerator(array);
						flag = 1;
					}
					
					else if (fill_matrix[0][0] == 0 && fill_matrix[0][1] == 1
							&& fill_matrix[0][2] == 0) {

						String id[] = { "m11", "m13" };
						id_name = this.randomGenerator(id);
					} else if (fill_matrix[0][0] == 0 && fill_matrix[1][0] == 1
							&& fill_matrix[2][0] == 0) {
						String id[] = { "m11", "m31" };
						id_name = this.randomGenerator(id);
					} else if (fill_matrix[2][0] == 0 && fill_matrix[2][1] == 1
							&& fill_matrix[2][2] == 0) {
						String id[] = { "m31", "m33" };
						id_name = this.randomGenerator(id);
					} else if (fill_matrix[0][2] == 0 && fill_matrix[1][2] == 1
							&& fill_matrix[2][2] == 0) {
						String id[] = { "m13", "m33" };
						id_name = this.randomGenerator(id);
					}
					else if(fill_matrix[1][0]==1&&fill_matrix[0][1]==1)
					{
						String id[] = { "m11", "m13","m31" };
						id_name = this.randomGenerator(id);
					}
					else if(fill_matrix[0][1]==1&&fill_matrix[1][2]==1)
					{
						String id[] = { "m11", "m13","m33" };
						id_name = this.randomGenerator(id);
					}
					else if(fill_matrix[1][2]==1&&fill_matrix[2][1]==1)
					{
						String id[] = { "m13", "m33","m31" };
						id_name = this.randomGenerator(id);
					}
					else if(fill_matrix[2][1]==1&&fill_matrix[1][0]==1)
					{
						String id[] = { "m31", "m11","m33" };
						id_name = this.randomGenerator(id);
					}
					

				}
				else if(this.filled_count==3&&Saver.level==Saver.HARD)
				{
					if((this.fill_matrix[0][0]==2&&fill_matrix[1][1]==1&&fill_matrix[2][2]==1)||(this.fill_matrix[0][0]==1&&fill_matrix[1][1]==1&&fill_matrix[2][2]==2))						
					{
						String id[] = { "m13", "m31" };
						id_name = this.randomGenerator(id);
					}
					else if((this.fill_matrix[0][2]==2&&fill_matrix[1][1]==1&&fill_matrix[2][0]==1)||(this.fill_matrix[0][2]==1&&fill_matrix[1][1]==1&&fill_matrix[2][0]==2))
					{
						String id[] = { "m11", "m33" };
						id_name = this.randomGenerator(id);
					}
					
				}

			}
		}
		if (this.whoFilled(id_name) == 0 && this.success_bit == 0) 
		{
			this.child = this.returnViewByIdName(id_name);
			this.previous_view_of_machine = this.child;
			drawable = this.getResources().getDrawable(R.drawable.custom_ball);
			next_drawable = this.getResources().getDrawable(
					R.drawable.custom_ball1);
			this.child.setBackgroundDrawable(drawable);
			this.child.setVisibility(this.child.VISIBLE);
			if (Saver.ifSoundPermission())
				this.makePlayerSound(1);
			this.color = 0;
			this.fillMatrix(id_name);
			this.player_color.setBackgroundDrawable(next_drawable);
			this.player = Saver.player;
			this.player_no.setText(this.player + "'s turn");
			this.filled_count += 1;
			if (this.nearByPositionsFilled(id_name)) 
			{
				this.successBlink();
				Saver.extra_bit = Saver.OFF;
				this.success_bit = 1;
				this.createDialogBox("\nYou loose !\n");
				Saver.player2_win_count += 1;
				this.player2_win_view.setText("" + Saver.player2_win_count
						+ "pts");
				drawable = this.getResources().getDrawable(
						R.drawable.custom_ball);
				this.player_color.setBackgroundDrawable(drawable);
				this.player_no.setText(Saver.machine + " won !");
				Saver.winner_color = 1;
				if (Saver.ifSoundPermission())
					this.makeWonSound();
			}
			if (this.success_bit == 0 && this.ifAllCellsFilled()) {
				this.createDialogBox("\nTied !\n");
				this.player_no.setText("Tied !");
				drawable = this.getResources().getDrawable(
						R.drawable.custom_dead_ball);
				this.player_color.setBackgroundDrawable(drawable);
				if (Saver.ifSoundPermission())
					this.makeTieSound();
				Saver.winner_color = -1;
			}
		}
	}

	private void ifPlayer(View view) {
		RelativeLayout box = (RelativeLayout) view;
		Drawable next_drawable = null;
		Drawable drawable = null;
		this.child = box.getChildAt(0);
		if (this.whoFilled(this.getResourceId(child)) == 0
				&& this.success_bit == 0) {
			drawable = this.getResources().getDrawable(R.drawable.custom_ball1);
			next_drawable = this.getResources().getDrawable(
					R.drawable.custom_ball);
			this.child.setBackgroundDrawable(drawable);
			this.child.setVisibility(this.child.VISIBLE);
			this.previous_view_of_player = this.child;
			if (Saver.ifSoundPermission())
				this.makePlayerSound(0);
			this.player_color.setBackgroundDrawable(next_drawable);
			this.player = Saver.machine;
			this.player_no.setText(this.player + "'s turn");
			this.filled_count += 1;
			this.color = 1;
			this.fillMatrix(this.getResourceId(child));
			Log.d("map", this.returnMatrixMap());
			if (this.nearByPositionsFilled(this.getResourceId(child))) {
				this.success_bit = 1;
				this.successBlink();
				this.createDialogBox("\nCongrats " + Saver.player
						+ "\n You won !\n");
				Saver.player1_win_count += 1;
				this.player1_win_view.setText("" + Saver.player1_win_count
						+ "pts");
				Saver.winner_color = 0;
				drawable = this.getResources().getDrawable(
						R.drawable.custom_ball1);
				this.player_color.setBackgroundDrawable(drawable);
				this.player_no.setText(Saver.player + " won !");
				if (Saver.ifSoundPermission())
					this.makeWonSound();
				Saver.extra_bit = Saver.OFF;
			}
			if (this.ifAllCellsFilled() && success_bit == 0) {
				this.createDialogBox("\nTied !\n");
				drawable = this.getResources().getDrawable(
						R.drawable.custom_dead_ball);
				this.player_no.setText("Tied !");
				this.player_color.setBackgroundDrawable(drawable);
				if (Saver.ifSoundPermission())
					this.makeTieSound();
				Saver.winner_color = -1;
			} else if (!this.ifAllCellsFilled())
				this.ifMachine(null);
			Log.d("fill", "" + this.filled_count);
		} else {
			if (Saver.ifSoundPermission())
				this.makeErrorSound();
			if (Saver.ifVibratePermission())
				this.vibrate();
		}
	}

	private String randomGenerator(String[] list) {
		Random number = new Random();
		int random_number = number.nextInt(list.length);
		return list[random_number];
	}

	private String[] returnUnfilledPositions() {
		String list[] = new String[9 - this.filled_count];

		try {
			int count = 0;
			for (int i = 0; i < 3; ++i)
				for (int j = 0; j < 3; ++j)
					if (fill_matrix[i][j] == 0) {
						list[count] = "m" + (i + 1) + (j + 1);
						count += 1;
					}
		} catch (Exception e) {
			Log.d("error", e.getMessage());
		}

		return list;
	}

	private void playerVsMachine(View view) {
		if (color == 0)
			this.ifPlayer(view);
	}

	private String returnWonPosition() {
		String pattern = "";
		for (int l = 0; l < 3; ++l) {
			for (int m = 0; m < 2; ++m)
				pattern += "" + Saver.completed_row[l][m];
			pattern += "\n";
		}
		return pattern;
	}

	private View returnViewByIdName(String id_name) {
		if (id_name.equals("m11"))
			return this.findViewById(R.id.m11);
		else if (id_name.equals("m12"))
			return this.findViewById(R.id.m12);
		else if (id_name.equals("m13"))
			return this.findViewById(R.id.m13);
		else if (id_name.equals("m21"))
			return this.findViewById(R.id.m21);
		else if (id_name.equals("m22"))
			return this.findViewById(R.id.m22);
		else if (id_name.equals("m23"))
			return this.findViewById(R.id.m23);
		else if (id_name.equals("m31"))
			return this.findViewById(R.id.m31);
		else if (id_name.equals("m32"))
			return this.findViewById(R.id.m32);
		else if (id_name.equals("m33"))
			return this.findViewById(R.id.m33);
		else
			return null;
	}

	private void playerVsPlayer(View view) {
		Drawable next_drawable = null;
		Drawable drawable = null;
		Vibrator error_click = (Vibrator) this
				.getSystemService(getApplicationContext().VIBRATOR_SERVICE);
		MediaPlayer mp3_player = null, wrong_detector = null;
		int flag = 1;
		this.box = (RelativeLayout) view;
		this.child = this.box.getChildAt(0);
		if (whoFilled(this.getResourceId(child)) == 0 && success_bit == 0)/*
																		 * if
																		 * true,
																		 * box
																		 * is
																		 * unfilled
																		 */
		{
			if (color == this.PLAYER_1) {
				if (Saver.ifSoundPermission())
					this.makePlayerSound(this.color);
				drawable = this.getResources().getDrawable(
						R.drawable.custom_ball1);
				next_drawable = this.getResources().getDrawable(
						R.drawable.custom_ball);
				this.color = this.PLAYER_2;
			} else {
				if (Saver.ifSoundPermission())
					this.makePlayerSound(this.color);
				drawable = this.getResources().getDrawable(
						R.drawable.custom_ball);
				next_drawable = this.getResources().getDrawable(
						R.drawable.custom_ball1);
				this.color = this.PLAYER_1;
			}
			fillMatrix(this.getResourceId(this.child));
			this.child.setBackgroundDrawable(drawable);
			this.child.setVisibility(child.VISIBLE);
			/* checking for success */
			if (nearByPositionsFilled(this.getResourceId(child))) {
				/* success occurs */
				this.success_bit = 1;
				if (Saver.sound_status == Saver.ON)
					this.makeWonSound();
				this.successBlink();
				Saver.extra_bit = Saver.OFF;
				Log.d("success pattern", this.returnWonPosition());
				if (this.color == this.PLAYER_1) {
					Saver.winner_color = 1;
					this.player = Saver.player2;
					Saver.player2_win_count += 1;
					if (Saver.player2_win_count > Saver.high_score)
						Saver.updateHighScores(this, this.player,
								Saver.player2_win_count);
					this.player2_win_view.setText("" + Saver.player2_win_count
							+ "pts");

				} else {
					Saver.winner_color = 0;
					this.player = Saver.player1;
					Saver.player1_win_count += 1;
					if (Saver.player1_win_count > Saver.high_score)
						Saver.updateHighScores(this, this.player,
								Saver.player1_win_count);
					this.player1_win_view.setText("" + Saver.player1_win_count
							+ "pts");

				}
				this.player_no.setText(this.player + " won !");
				this.createDialogBox("\nCongrats " + this.player
						+ "\nYou won !\n");
			} else {
				if (!this.ifAllCellsFilled()) {
					if (this.color == 0)
						this.player = Saver.player1;
					else
						this.player = Saver.player2;
					this.player_no.setText(this.player + "'s turn");
					this.player_color.setBackgroundDrawable(next_drawable);
				} else {
					this.player_no.setText("Tied !");
					Saver.winner_color = -1;
					drawable = this.getResources().getDrawable(
							R.drawable.custom_dead_ball);
					this.player_color.setBackgroundDrawable(drawable);
					this.createDialogBox("\nTied !\n");
					if (Saver.ifSoundPermission())
						this.makeTieSound();
				}
			}
		} else {
			if (Saver.ifVibratePermission())
				error_click.vibrate(80);
			if (Saver.ifSoundPermission())
				this.makeErrorSound();
		}

	}

	public void setChild(View view) {
		if (Saver.who_vs_whom == Saver.PLAYER_VS_PLAYER)
			this.playerVsPlayer(view);
		else
			this.playerVsMachine(view);
	}

	private int[] winMoveOfPlayer(int player_or_machine, String id) {
		int position[] = new int[2];
		int result[] = new int[4];
		int overflow = 0;
		for (int i = 0; i < 4; ++i)
			result[i] = -1;
		if (true) {
			int add = 0, mul = 1, zero_count = 0, player_color_count = 0;
			if (id == null) {
				if (player_or_machine == this.PLAYER)
					position = this.returnMatrixPosition(this
							.getResourceId(previous_view_of_player));
				else if (player_or_machine == this.MACHINE)
					position = this.returnMatrixPosition(this
							.getResourceId(previous_view_of_machine));
			} else if (id != null)
				position = this.returnMatrixPosition(id);
			int row = position[0];
			int column = position[1];
			for (int i = 0; i < 3; ++i) {
				if (fill_matrix[row][i] == 0) {
					zero_count += 1;
					result[0 + overflow] = row;
					result[1 + overflow] = i;
				} else if (fill_matrix[row][i] == player_or_machine) {
					player_color_count += 1;
				}
			}
			if (!(player_color_count == 2 && zero_count == 1)) {
				result[0 + overflow] = -1;
				result[1 + overflow] = -1;
			} else
				overflow += 2;
			zero_count = 0;
			player_color_count = 0;
			for (int i = 0; i < 3; ++i) {
				if (fill_matrix[i][column] == 0) {
					zero_count += 1;
					result[0 + overflow] = i;
					result[1 + overflow] = column;
				} else if (fill_matrix[i][column] == player_or_machine) {
					player_color_count += 1;
				}

			}
			if (!(player_color_count == 2 && zero_count == 1)) {
				result[0 + overflow] = -1;
				result[1 + overflow] = -1;
			} else
				overflow += 2;
			int pos[][] = new int[3][2];
			if (overflow != 4) {
				zero_count = player_color_count = 0;
				pos[0][1] = pos[0][0] = 0;
				pos[1][1] = pos[1][0] = 1;
				pos[2][1] = pos[2][0] = 2;
				for (int i = 0; i < 3; ++i) {
					if (fill_matrix[pos[i][0]][pos[i][1]] == 0) {
						zero_count += 1;
						result[0 + overflow] = pos[i][0];
						result[1 + overflow] = pos[i][1];
					} else if (fill_matrix[pos[i][0]][pos[i][1]] == player_or_machine) {
						player_color_count += 1;
					}
				}
				if (!(player_color_count == 2 && zero_count == 1)) {
					result[0 + overflow] = -1;
					result[0 + overflow] = -1;
				} else
					overflow += 2;
			}
			if (overflow != 4) {
				zero_count = 0;
				player_color_count = 0;
				pos[0][1] = 2;
				pos[0][0] = 0;
				pos[1][1] = 1;
				pos[1][0] = 1;
				pos[2][1] = 0;
				pos[2][0] = 2;
				for (int i = 0; i < 3; ++i) {
					if (fill_matrix[pos[i][0]][pos[i][1]] == 0) {
						zero_count += 1;
						result[0 + overflow] = pos[i][0];
						result[1 + overflow] = pos[i][1];
					} else if (fill_matrix[pos[i][0]][pos[i][1]] == player_or_machine) {
						player_color_count += 1;
					}
				}
				if (!(player_color_count == 2 && zero_count == 1)) {
					result[0 + overflow] = -1;
					result[1 + overflow] = -1;
				}
			}
		}
		return result;
	}

	private boolean ifPlayerCanWin(int[] position) {
		if (position[0] == -1 && position[1] == -1 && position[2] == -1
				&& position[3] == -1)
			return false;
		return true;
	}

	private int[] returnMatrixPosition(String id_name) {

		int position[] = new int[2];
		position[0] = (int) id_name.charAt(1) - 49;
		position[1] = (int) id_name.charAt(2) - 49;
		return position;
	}

	/* 888888888888888888888888888888888888888888888888888888888888888888888 */
	public void disappear(View view) {
		this.dialog_layout.setVisibility(this.dialog_layout.GONE);
	}

	public void okTrigger(View view) {
		this.dialog_layout.setVisibility(this.dialog_layout.GONE);
		Intent intent = new Intent(this, HomeScreen.class);
		this.finish();
		this.startActivity(intent);
		Saver.winner_color = -1;
		Saver.player2_win_count = 0;
		Saver.player1_win_count = 0;
		if (Saver.ifSoundPermission())
			this.makeSound();
	}

	public void cancelTrigger(View view) {
		this.dialog_layout.setVisibility(this.dialog_layout.GONE);
		if (Saver.ifSoundPermission())
			this.makeSound();
	}

	@Override
	public void onBackPressed() {
		this.title.clearAnimation();
		if (this.dialog_layout.getVisibility() == dialog_layout.GONE) {
			this.dialog_layout.setVisibility(this.dialog_layout.VISIBLE);
			this.title.setVisibility(this.title.VISIBLE);
			this.title.setText("Go back?");
			if (this.button_pad.getVisibility() == this.button_pad.GONE)
				this.button_pad.setVisibility(this.button_pad.VISIBLE);
		} else if (this.dialog_layout.getVisibility() == dialog_layout.VISIBLE)
			this.dialog_layout.setVisibility(this.dialog_layout.GONE);
	}

	public void fillMatrix(String id) {
		int row = (int) id.charAt(1) - 49;
		int column = (int) id.charAt(2) - 49;
		if (this.color == this.PLAYER_1)
			fill_matrix[row][column] = 2;
		else if (this.color == 1)
			fill_matrix[row][column] = 1;
	}

	public void restartGame(View view) {
		if (Saver.ifSoundPermission())
			this.makeSound();
		Intent intent = getIntent();
		this.finish();
		this.startActivity(intent);
	}

	private void clearFillMatrix() {
		for (int i = 0; i < 3; ++i)
			for (int j = 0; j < 3; ++j)
				this.fill_matrix[i][j] = 0;
	}

	private String getResourceId(View view) {
		return this.getResources().getResourceEntryName(view.getId());
	}

	private int whoFilled(String id) {
		int row = (int) id.charAt(1) - 49;
		int column = (int) id.charAt(2) - 49;
		return this.fill_matrix[row][column];
	}

	private boolean nearByPositionsFilled(String id) {
		int flag = 0;
		int row = (int) id.charAt(1) - 49;
		int column = (int) id.charAt(2) - 49;
		Log.d("type=", "" + returnMatrixMap());
		if (positionType(id) == this.OTHERS) {
			int count = 1;
			for (int i = 0; i < 3; ++i)
				count *= this.fill_matrix[row][i];
			if (count == 1 || count == 8) {
				for (int m = 0; m < 3; ++m) {
					Saver.completed_row[m][0] = row;
					Saver.completed_row[m][1] = m;
				}
				flag = 1;
			}
			count = 1;
			for (int i = 0; i < 3; ++i)
				count *= this.fill_matrix[i][column];
			if (count == 1 || count == 8) {
				for (int m = 0; m < 3; ++m) {
					if (flag == 0) {
						Saver.completed_row[m][0] = m;
						Saver.completed_row[m][1] = column;
					} else {
						Saver.extra[m][0] = m;
						Saver.extra[m][1] = column;
					}
				}
				if (flag == 0) {
					flag = 1;
				} else
					Saver.extra_bit = 1;
			}
			Log.d("extra", "" + Saver.extra_bit + "others");
			if (flag == 1)
				return true;
			return false;
		} else if (positionType(id) == this.CORNER
				|| positionType(id) == this.CENTER) {
			int count = 1;
			int i;
			for (i = 0; i < 3; ++i) {
				count *= fill_matrix[row][i];
			}
			if (count == 1 || count == 8) {
				for (int m = 0; m < 3; ++m) {
					Saver.completed_row[m][0] = row;
					Saver.completed_row[m][1] = m;
				}
				flag = 1;
			}
			count = 1;
			for (i = 0; i < 3; ++i) {
				count *= this.fill_matrix[i][column];
			}
			if (count == 1 || count == 8) {
				for (int m = 0; m < 3; ++m) {
					if (flag == 0) {
						Saver.completed_row[m][0] = m;
						Saver.completed_row[m][1] = column;
					} else {
						Saver.extra[m][0] = m;
						Saver.extra[m][1] = column;
					}
				}
				if (flag == 0)
					flag = 1;
				else
					Saver.extra_bit = 1;
			}
			count = this.fill_matrix[0][0] * this.fill_matrix[1][1]
					* this.fill_matrix[2][2];
			if (count == 1 || count == 8) {
				if (flag == 0) {
					Saver.completed_row[0][0] = 0;
					Saver.completed_row[0][1] = 0;
					Saver.completed_row[1][0] = 1;
					Saver.completed_row[1][1] = 1;
					Saver.completed_row[2][0] = 2;
					Saver.completed_row[2][1] = 2;
				} else {
					Saver.extra[0][0] = 0;
					Saver.extra[0][1] = 0;
					Saver.extra[1][0] = 1;
					Saver.extra[1][1] = 1;
					Saver.extra[2][0] = 2;
					Saver.extra[2][1] = 2;

				}
				if (flag == 0)
					flag = 1;
				else
					Saver.extra_bit = 1;
			}
			count = fill_matrix[0][2] * fill_matrix[1][1] * fill_matrix[2][0];
			if (count == 1 || count == 8) {
				if (flag == 0) {
					Saver.completed_row[0][0] = 0;
					Saver.completed_row[0][1] = 2;
					Saver.completed_row[1][0] = 1;
					Saver.completed_row[1][1] = 1;
					Saver.completed_row[2][0] = 2;
					Saver.completed_row[2][1] = 0;
				} else {
					Saver.extra[0][0] = 0;
					Saver.extra[0][1] = 2;
					Saver.extra[1][0] = 1;
					Saver.extra[1][1] = 1;
					Saver.extra[2][0] = 2;
					Saver.extra[2][1] = 0;
				}
				if (flag == 0)
					flag = 1;
				else
					Saver.extra_bit = 1;
			}
			Log.d("extra", "" + Saver.extra_bit + "corners/middles");
			if (flag == 1)
				return true;
			return false;
		} else {
			return true;
		}

	}

	private int positionType(String id) {
		int row = (int) id.charAt(1) - 49;
		int column = (int) id.charAt(2) - 49;
		if ((row == 0 && column == 0) || (row == 0 && column == 2)
				|| (row == 2 && column == 0) || (row == 2 && column == 2))
			return this.CORNER;
		else if (row == 1 && column == 1)
			return this.CENTER;
		else
			return this.OTHERS;
	}

	private String returnMatrixMap() {
		String map = "";
		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 3; ++j)
				map += "|" + this.fill_matrix[i][j];
			map += "\n";
		}
		return map;
	}

	private boolean ifAllCellsFilled() {
		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 3; ++j) {
				if (fill_matrix[i][j] == 0)
					return false;
			}
		}
		return true;
	}

	private void setWinCounts() {
		this.player1_win_view.setText("" + Saver.player1_win_count + "pts");
		this.player2_win_view.setText("" + Saver.player2_win_count + "pts");
	}

	private void makeWonSound() {
		MediaPlayer winplayer = null;
		try {
			winplayer = MediaPlayer.create(this, R.raw.success);
			winplayer.start();
		} catch (Exception e) {
		}
	}

	private void makeTieSound() {
		MediaPlayer winplayer = null;
		try {
			winplayer = MediaPlayer.create(this, R.raw.tie);
			winplayer.start();
		} catch (Exception e) {
		}
	}

	private void showDialog(String string) {
		new AlertDialog.Builder(this).setTitle(string).show();
	}

	private void makeSound() {
		try {
			this.clicker = MediaPlayer.create(this, R.raw.click);
			this.clicker.start();
		} catch (Exception e) {
		}
	}

	private void makeErrorSound() {
		MediaPlayer winplayer;
		try {
			winplayer = MediaPlayer.create(this, R.raw.wrong);
			winplayer.start();
		} catch (Exception e) {
		}
	}

	private void vibrate() {
		Vibrator error_click = (Vibrator) this
				.getSystemService(getApplicationContext().VIBRATOR_SERVICE);
		error_click.vibrate(80);

	}

	private void makePlayerSound(int player_color) {
		try {
			if (player_color == this.PLAYER_1)
				this.mp3_player = MediaPlayer.create(this, R.raw.player1);
			else
				this.mp3_player = MediaPlayer.create(this, R.raw.player2);
			this.mp3_player.setVolume(0.1f, 0.1f);
			this.mp3_player.start();
		} catch (Exception e) {
		}
	}

	private void createDialogBox(String string) {

		this.dialog_layout.setVisibility(this.dialog_layout.VISIBLE);
		this.button_pad.setVisibility(this.button_pad.GONE);
		this.title.setText(string);
		Animation blink = AnimationUtils.loadAnimation(this, R.anim.blink);
		this.title.setAnimation(blink);
	}

	private String LshapeArray() {
		int corner[][] = new int[4][2];
		corner[0][0] = 0;
		corner[0][1] = 0;
		corner[1][0] = 0;
		corner[1][1] = 2;
		corner[2][0] = 2;
		corner[2][1] = 0;
		corner[3][0] = 2;
		corner[3][1] = 2;
		int top = 0, bottom = 0, left = 0, right = 0;
		for (int i = 0; i < 3; ++i) {
			if (fill_matrix[0][i] == 1)
				top += 1;
			if (fill_matrix[2][i] == 1)
				bottom += 1;
			if (fill_matrix[i][0] == 1)
				left += 1;
			if (fill_matrix[i][2] == 1)
				left += 1;
		}
		if (top == 1 && left == 1)
			return "top_left";
		if (top == 1 && right == 1)
			return "top_right";
		if (bottom == 1 && left == 1)
			return "bottom_left";
		if (bottom == 1 && right == 1)
			return "bottom_right";
		return "";

	}
}

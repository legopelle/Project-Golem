package com.game.droids;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Level extends Activity {

	public int fps = 40;

	Handler Redrawer = new Handler();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setupLayout();
		setupGrid();
		setupRobots();
		startRedrawer();
	}

	public Context THIS = this;

	public int step = 0;
	public int[] program = new int[0];
	public int current_function = -1; //This is the function the robots are running

	public int grid_width = 8;
	public int grid_height = 8;

	public Robot[] robots;

	public Cell[][] Grid = new Cell[grid_width][grid_height];
	public Wall[] walls = new Wall[] {
			new Wall(2,3,2,4),
			new Wall(3,3,3,4),
			new Wall(1,1,2,1),
			new Wall(5,2,4,2),
			new Wall(5,3,5,4),
			new Wall(1,4,1,5),
			new Wall(1,4,2,4),
			new Wall(1,3,2,3)};

	public LvlGraph Graphics;

	public TextView TempText;
	public Button Executor;
	public Button MakeProgram;



	public void setupLayout() {
		LinearLayout ScreenLayout = new LinearLayout(this);
		ScreenLayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		ScreenLayout.setOrientation(LinearLayout.VERTICAL);

		setContentView(ScreenLayout);

		Graphics = new LvlGraph(this);
		Graphics.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 0, 5f));

		Executor = new Button(this);
		Executor.setText("EXECUTE MY COMMANDS!");
		Executor.setOnClickListener(Execute);
		Executor.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 0, 1f));

		MakeProgram= new Button(this);
		MakeProgram.setText("Decide commands");
		MakeProgram.setOnClickListener(MakePrgm);
		MakeProgram.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 0, 1f));

		TempText = new TextView(this);
		TempText.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 0, 1f));

		ScreenLayout.addView(Graphics);
		ScreenLayout.addView(Executor);
		ScreenLayout.addView(MakeProgram);
		ScreenLayout.addView(TempText);

	}

	public void setupGrid() {

		for (int x = 0; x<grid_width; x++) {
			for (int y = 0; y < grid_height; y++ ) {

				Grid[x][y] = new Cell(new coords(x,y));

			}
		}
	}

	public void setupRobots() {
		robots = new Robot[3];

		for (int i = 0; i < robots.length; i++) {
			robots[i] = new Robot(new coords(2*i,i), this);
		}
	}

	public void startRedrawer() {
		Graphics.invalidate();
		Graphics.width = Graphics.getWidth();
		Graphics.height = Graphics.getHeight();
		Redrawer.postDelayed(Redraw,100);
	}

	public View.OnClickListener Execute = new View.OnClickListener() {

		@Override
		public void onClick(View arg0) {

			Executor.setClickable(false);

			if (step < program.length) {
				for (Robot R : robots) {
					current_function = program[step];
					R.Execute(program[step]);
				}
				step++;
			}

			drawQueue();

		}
	};

	public View.OnClickListener MakePrgm = new View.OnClickListener() {

		@Override
		public void onClick(View arg0) {

			Intent myIntent = new Intent(THIS, ProgramMaker.class);
			startActivityForResult(myIntent, 1);

		}
	};

	public Runnable Redraw = new Runnable() { 
		public void run() { 

			Graphics.invalidate();
			
			Graphics.updateSize(Graphics.getWidth(), Graphics.getHeight());
			Redrawer.postDelayed(Redraw,(int) 1000f/fps);
			
			for (Robot R : robots) {
				if (R.moving) {
					R.Execute(current_function);
				}
			}
			
			for (Robot R : robots) {
				if (R.moving) {
					return;
				}
			}
			Executor.setClickable(true);
			current_function = -1;

		} 
	};

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if(resultCode==RESULT_OK && requestCode == 1){
			program = data.getIntArrayExtra("Program");
			drawQueue();
		}
	}

	public void drawQueue() {
		String Remains = "";
		for (int i = step; i < program.length; i++) {
			switch(program[i]) {
			case (cons.U): Remains += "UP,";break;
			case (cons.D): Remains += "DOWN,";break;
			case (cons.R): Remains += "RIGHT,";break;
			case (cons.L): Remains += "LEFT,";break;
			}
		}
		if (Remains.length() > 0) {
			Remains = Remains.substring(0, Remains.length()-1);
		}
		TempText.setText(Remains);
	}
}

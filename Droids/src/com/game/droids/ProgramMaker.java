package com.game.droids;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

public class ProgramMaker extends Activity{

	Context THIS = this;

	LinearLayout RowsLayout;
	LinearLayout[] Rows;

	int index;

	FuncButton[] Functions = new FuncButton[16]; 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setupLayout();
	}

	public void setupLayout() {
		RowsLayout = new LinearLayout(this);
		RowsLayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		RowsLayout.setOrientation(LinearLayout.VERTICAL);

		Rows = new LinearLayout[4];
		for (int i = 0; i < 4; i++) {
			Rows[i] = new LinearLayout(this);
			Rows[i].setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 0, 1));
			Rows[i].setOrientation(LinearLayout.HORIZONTAL);
			RowsLayout.addView(Rows[i]);
		}

		LinearLayout ButtonLayout = new LinearLayout(this);
		ButtonLayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 0, 1));
		ButtonLayout.setOrientation(LinearLayout.HORIZONTAL);
		ButtonLayout.setBackgroundColor(Color.rgb(100,100,100));

		Button UpBtn = new Button(this);
		UpBtn.setLayoutParams(new LayoutParams(0, LayoutParams.MATCH_PARENT, 1));
		UpBtn.setText("U");
		ButtonLayout.addView(UpBtn);

		Button DownBtn = new Button(this);
		DownBtn.setLayoutParams(new LayoutParams(0, LayoutParams.MATCH_PARENT, 1));
		DownBtn.setText("D");
		ButtonLayout.addView(DownBtn);

		Button RightBtn = new Button(this);
		RightBtn.setLayoutParams(new LayoutParams(0, LayoutParams.MATCH_PARENT, 1));
		RightBtn.setText("R");
		ButtonLayout.addView(RightBtn);

		Button LeftBtn = new Button(this);
		LeftBtn.setLayoutParams(new LayoutParams(0, LayoutParams.MATCH_PARENT, 1));
		LeftBtn.setText("L");
		ButtonLayout.addView(LeftBtn);

		UpBtn.setOnClickListener(AddFunc);
		DownBtn.setOnClickListener(AddFunc);
		RightBtn.setOnClickListener(AddFunc);
		LeftBtn.setOnClickListener(AddFunc);

		Button DoneBtn = new Button(this);
		DoneBtn.setLayoutParams(new LayoutParams(0, LayoutParams.MATCH_PARENT, 1));
		DoneBtn.setText("Done");
		ButtonLayout.addView(DoneBtn);
		DoneBtn.setOnClickListener(Done);

		RowsLayout.addView(ButtonLayout);

		setContentView(RowsLayout);
	}

	public View.OnClickListener AddFunc = new View.OnClickListener() {

		@Override
		public void onClick(View Btn) {

			if (index < Functions.length) {
				FuncButton newFunc = new FuncButton(new Button(THIS), index);
				newFunc.Btn.setText(((Button) Btn).getText());
				newFunc.Btn.setLayoutParams(new LayoutParams(0, LayoutParams.MATCH_PARENT, 1f));
				Functions[index] = newFunc;
				newFunc.Btn.setOnClickListener(DelFunc);
				index++;
				reDraw();
			}

		}
	};

	public View.OnClickListener DelFunc = new View.OnClickListener() {

		@Override
		public void onClick(View Btn) {

			FuncButton ClickedFunc = null;
			for (FuncButton FB : Functions) {
				if (FB != null) {
					if (FB.Btn == Btn) {
						ClickedFunc = FB;
					}
				}
			}

			FuncButton[] TempFunctions = new FuncButton[Functions.length];

			for (int i = 0; i < ClickedFunc.index; i++) {
				TempFunctions[i] = Functions[i];
			}

			for (int i = ClickedFunc.index + 1; i < Functions.length; i++) {
				TempFunctions[i-1] = Functions[i];
				if (TempFunctions[i-1] != null) { 
					TempFunctions[i-1].index--;
				}
			}

			Functions = TempFunctions;
			index--;

			reDraw();

		}
	};

	public void reDraw() {

		for (LinearLayout L : Rows) {
			L.removeAllViews();
		}
		for (int i = 0; i < index; i++) {
			Rows[i / 4].addView(Functions[i].Btn);
		}
	}

	public View.OnClickListener Done = new View.OnClickListener() {

		@Override
		public void onClick(View Btn) {

			int[] Program = new int[Functions.length];

			for (int i = 0; i < Functions.length; i++) {
				if (Functions[i] != null) {
					String FunctionVal = (String) (Functions[i].Btn.getText());
					if (FunctionVal.equals("U")) {
						Program[i] = cons.U;
					}
					if (FunctionVal.equals("D")) {
						Program[i] = cons.D;
					}
					if (FunctionVal.equals("L")) {
						Program[i] = cons.L;
					}
					if (FunctionVal.equals("R")) {
						Program[i] = cons.R;
					}
				}
			}

			Intent data = new Intent(); 
			data.putExtra("Program", Program);
			setResult(RESULT_OK, data);
			finish();
		}
	};

}


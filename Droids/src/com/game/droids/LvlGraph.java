package com.game.droids;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

public class LvlGraph extends View {

	int width; //width in pixels
	int height; //height in pixels
	float cellwidth;
	Level level;
	
	public Paint myPaint = new Paint();
	public Paint LinePaint = new Paint();

	public LvlGraph(Context context) {
		super(context);
		level = (Level) context;

		width = 200;
		height = 200;
		cellwidth = ((float) width / level.grid_width);
		
		LinePaint.setStyle(Paint.Style.STROKE);

	}

	
	
	@Override
	public void onDraw(Canvas canvas) {

		LinePaint.setStrokeWidth(cellwidth * 0.1f);
		
		for (Cell[] CellRow: level.Grid) {
			for (Cell C : CellRow) {

				LinePaint.setColor(Color.rgb(200, 200, 200));
				canvas.drawRect(C.rect(cellwidth), LinePaint);	
			}
		}

		for (Wall W : level.walls) {
			LinePaint.setColor(Color.rgb(0,0,0));
			LinePaint.setStrokeWidth(cellwidth * 0.2f);
			canvas.drawLine(W.line(cellwidth)[0], W.line(cellwidth)[1], W.line(cellwidth)[2], W.line(cellwidth)[3], LinePaint);
			LinePaint.setStrokeWidth(cellwidth * 0.1f);
		}
		
		for (Robot R : level.robots) {
			myPaint.setColor(Color.rgb(0,0,(int)(Math.random()*100)));
			canvas.drawRect(R.ani_rect(cellwidth), myPaint);	
		}
		
		LinePaint.setColor(Color.rgb(0,0,0));
		LinePaint.setStrokeWidth(cellwidth * 0.2f);
		canvas.drawRect(0,0,width,height, LinePaint);
	}

	public void updateSize(int w, int h) {
		width = w;
		height = h;
		
		int sidelength = Math.min(w, h);
		
		cellwidth = ((float) sidelength / level.grid_width);
		
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(sidelength,sidelength);
        params.weight = 0;
        params.gravity = Gravity.CENTER;

        this.setLayoutParams(params);
	}



}

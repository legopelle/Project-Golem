package com.game.droids;

import android.graphics.Rect;
import android.graphics.RectF;

public class Cell {
	coords pos;
	
	public Cell(coords posin) {
		pos = posin;
	}
	
	public RectF rect(float cw) {
		//cw  is the cellwidth
		return new RectF(pos.x*cw, pos.y*cw, (pos.x+1)*cw, (pos.y+1)*cw);
	}
	
}

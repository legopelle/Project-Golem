package com.game.droids;

import android.graphics.RectF;
import android.util.Log;

public class Robot {

	coords pos;
	Level level;

	int ani_step = 0; //step in animation (movement) between two points.
	coords old_pos;
	int move_dir = -1;
	Boolean moving = false;
	Boolean moving_onestep = false;

	public Robot(coords posin, Level parent) {
		pos = posin;
		level = parent;
	}

	public RectF rect(float cw) {
		//cw  is the cellwidth

		return new RectF(pos.x*cw+0.2f*cw, pos.y*cw+0.2f*cw,
				(pos.x+1)*cw-0.2f*cw, (pos.y+1)*cw-0.2f*cw);

	}

	public RectF ani_rect(float cw) {
		if (moving && ani_step < 10) {
			RectF ReturnRect = rect(cw);
			if (move_dir == cons.U) {
				ReturnRect.offset(0,cw-(ani_step*cw)/10);
			}
			if (move_dir == cons.D) {
				ReturnRect.offset(0,-cw+(ani_step*cw)/10);
			}
			if (move_dir == cons.L) {
				ReturnRect.offset(+cw-(ani_step*cw)/10,0);
			}
			if (move_dir == cons.R) {
				ReturnRect.offset(-cw+(ani_step*cw)/10,0);
			}
			ani_step++;
			return ReturnRect;
		}
		else {
			moving_onestep = false;
			move_dir = -1;
			ani_step = 0;
			return rect(cw);
		}

	}

	public void Move(int dir) {

		old_pos = pos;
		moving_onestep = true;

		switch(dir) {
		case (cons.U): {MoveUp(); return;}
		case (cons.D): {MoveRight(); return;}
		case (cons.L): {MoveDown(); return;}
		case (cons.R): {MoveLeft(); return;}
		}
	}

	public void MoveToWall(int dir) {
		if (!moving_onestep) {
			if(canMove(dir)) {
				moving = true;
				Move(dir);
			}
			else {
				moving = false;
			}
		}
	}

	public void MoveOnce(int dir) {
		if (!moving_onestep) {
			Move(dir);
		}
	}

	public void MoveUp() {
		if (canMove(cons.U)) {
			move_dir = cons.U;
			pos.y--;
		}
	}

	public void MoveDown() {
		if (canMove(cons.D)) {
			move_dir = cons.D;
			pos.y++;
		}
	}

	public void MoveLeft() {
		if (canMove(cons.L)) {
			move_dir = cons.L;
			pos.x--;
		}
	}

	public void MoveRight() {
		if (canMove(cons.R)) {
			move_dir = cons.R;
			pos.x++;
		}
	}

	public Boolean isWall(coords from, coords to) { 
		for (Wall w : level.walls) {
			Log.d("Wallfrom", Integer.toString(w.from.x)+", "+Integer.toString(w.from.y));
			Log.d("Wallto", Integer.toString(w.to.x)+", "+Integer.toString(w.to.y));
			Log.d("Robotfrom", Integer.toString(from.x)+", "+Integer.toString(from.y));
			Log.d("Robotto", Integer.toString(to.x)+", "+Integer.toString(to.y));
			Log.d("---", "---");
			if (from.equals(w.from) && to.equals(w.to)) {
				return true;
			}
			if (to.equals(w.from) && from.equals(w.to)) {
				return true;
			}
		}
		return false;
	}

	public Boolean canMove(int dir) {

		if (isWall(pos, posDir(dir))) {
			return false;
		}

		switch (dir) {
		case (cons.U): {
			if (pos.y == 0) {
				return false;
			}
			break;
		}
		case (cons.D): {
			if (pos.y == level.grid_height-1) {
				return false;
			}
			break;
		}
		case (cons.L): {
			if (pos.x == 0) {
				return false;
			}
			break;
		}
		case (cons.R): {
			if (pos.x == level.grid_width - 1) {
				return false;
			}
			break;
		}
		}
		return true;
	}

	public coords posDir(int dir) { //returns the coords of the cell in direction dir
		switch (dir) {
		case (cons.U): {
			return (new coords(pos.x, pos.y - 1));
		}
		case (cons.D): {
			return (new coords(pos.x, pos.y + 1));
		}
		case (cons.L): {
			return (new coords(pos.x-1, pos.y));
		}
		case (cons.R): {
			return (new coords(pos.x+1, pos.y));
		}
		}
		return pos; //Should not happen.
	}

	public void Execute(int func) {
		if (0 <= func && func <=3) {
			MoveOnce(func);
		}
		if (4 <= func && func <=7) {
			MoveToWall(func);
		}
	}
}

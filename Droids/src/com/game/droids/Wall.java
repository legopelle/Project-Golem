package com.game.droids;

public class Wall {
	coords from;
	coords to;
	int dir;

	final private int UP = 0;
	final private int RIGHT = 1;
	final private int DOWN = 2;
	final private int LEFT = 3;

	public Wall (int fromx, int fromy, int tox, int toy) {
		from = new coords(fromx, fromy);
		to = new coords(tox, toy);
		
		if(from.x == to.x) {
			if (from.y == to.y - 1) {
				dir = DOWN;
			}
			else if (from.y == to.y + 1){
				dir = UP;
			}
		}
		else if (from.y == to.y) {
			if (from.x == to.x - 1) {
				dir = RIGHT;
			}
			if (from.x == to.x + 1) {
				dir = LEFT;
			}
		}
	}

	public float[] line(float cw) {
		//cw  is the cellwidth

		float f_xc = from.x + cw*0.5f; //horizontal center of from
		float f_yc = from.y + cw*0.5f; //vertical center of from				

		if(dir == UP) {
			return new float[] {from.x*cw, from.y*cw, from.x*cw + cw, from.y*cw};
		}
		else if(dir == DOWN) {
			return new float[] {from.x*cw, from.y*cw+cw, from.x*cw + cw, from.y*cw+cw};
		}
		else if (dir == RIGHT) {
			return new float[] {from.x*cw+cw, from.y*cw, from.x*cw + cw, from.y*cw+cw};
		}
		else if (dir == LEFT) {
			return new float[] {from.x*cw, from.y*cw, from.x*cw, from.y*cw+cw};
		}
		
		else {
			return null; //Should not really happen.
		}
	}


}

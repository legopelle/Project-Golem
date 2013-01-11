package com.game.droids;

public class coords {
	public int x;
	public int y;

	public coords(int xin, int yin) {
		x = xin;
		y = yin;
	}

	public Boolean equals(coords compare) {
		return (x == compare.x && y == compare.y);	
	}

}

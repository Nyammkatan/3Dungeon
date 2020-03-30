package com.nyammkatan.game.utils;

import java.util.TreeMap;

public class Room {
	
	public float x, y;
	public int w, h;
	
	public byte biom = 0;
	
	public Room(float x, float y, int w, int h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		dists = new TreeMap<Integer, Room>();
		
	}
	
	public boolean overlaps(Room r, int bound) {
		x = (float) Math.floor(x);
		y = (float) Math.floor(y);
		if (x-bound < r.x-bound + r.w+bound && x-bound + w+bound > r.x-bound &&
				y-bound < r.y-bound + r.h+bound && y-bound + r.h+bound > r.y-bound) {
			return true;
		} else
			return false;
		
	}
	
	public TreeMap<Integer, Room> dists;
	
	public void addRoomDist(Room room) {
		int dist = this.distTo(room);
		dists.put(dist, room);
		
	}
	
	public int distTo(Room room) {
		return (int) Math.sqrt(Math.pow((room.x-x), 2) + Math.pow((room.y-y), 2));
		
	}
	
}

package com.nyammkatan.game.map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.nyammkatan.game.Game;
import com.nyammkatan.game.utils.Funcs;
import com.nyammkatan.game.utils.MapGeneration;
import com.nyammkatan.game.utils.Room;
import com.nyammkatan.game.utils.SimplexNoise;

public class Map {
	
	public int[][] array;
	public int[][] typeArray;
	public static int size = 250;
	public Random random;
	public ArrayList<Room> rooms;
	
	public Map(Random random) {
		this.random = random;
		array = new int[size][size];
		typeArray = new int[size][size];
		rooms = MapGeneration.generateRooms(size, random, array);
		MapGeneration.createAllRoutes(rooms);
		ArrayList<MapGeneration.Rib> ribs = MapGeneration.getPrim(rooms);
		MapGeneration.bindRibs(ribs, array);
		this.setBiomsWithNoise();
		MapGeneration.drawMapColor(typeArray, "test.png");
		
		
	}
	
	public void setBiomsWithNoise() {
		for (int i=0; i < typeArray.length; i++) {
			for (int j=0; j < typeArray[i].length; j++) {
				float value = (float) SimplexNoise.noise(j/120f, i/120f);
				value = (int)((value + 1f)/2f*255);
				typeArray[i][j] = (int) value;
				typeArray[i][j] = (int) Funcs.lerp(0, 6.5f, 0, typeArray[i][j], 255);
			}
			
		}
		
		
	}
	
	public void setBioms() {
		Room first = rooms.get(0);
		HashMap<Room, Float> dsts = new HashMap<Room, Float>();
		float maxDist = 0;
		int maxDistRoomIndex = -1;
		for (int i=0; i < rooms.size(); i++) {
			float dist = first.distTo(rooms.get(i));
			if (dist >= maxDist) {
				maxDist = dist;
				maxDistRoomIndex = i;
			}
			dsts.put(rooms.get(i), dist);
		}
		for (Room r: rooms) {
			byte value = (byte) Funcs.lerp(0, 6.9f, 0, dsts.get(r), maxDist);
			r.biom = value;
			for (int i=(int) r.y; i < r.y+r.h; i++) {
				for (int j=(int) r.x; j < r.x+r.w; j++) {
					typeArray[i][j] = r.biom;
				}
			}
			
		}
		
	}

}

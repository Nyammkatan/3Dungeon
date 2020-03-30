package com.nyammkatan.game.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

import javax.imageio.ImageIO;

import com.badlogic.gdx.graphics.Color;

public class MapGeneration {
	
	public static class Rib {
		
		public Room r1, r2;
		public int dist = 0;
		
		Rib(Room r1, Room r2){
			this.r1 = r1;
			this.r2 = r2;
			this.dist = r1.distTo(r2);
			
		}
		
	}
	
	public static void drawMapColor(int[][] array, String s) {
		BufferedImage image = new BufferedImage(array.length, array.length, BufferedImage.TYPE_INT_RGB);
		for (int i=0; i < array.length; i++) {
			for (int j=0; j < array[i].length; j++) {
				image.setRGB(j, i, array[i][j]);
			}
		}
		try {
			ImageIO.write(image, "PNG", new File(s));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void drawMap(int[][] array, String s) {
		BufferedImage image = new BufferedImage(array.length, array.length, BufferedImage.TYPE_INT_RGB);
		for (int i=0; i < array.length; i++) {
			for (int j=0; j < array[i].length; j++) {
				if (array[i][j] == 1) {
					image.setRGB(j, i, 16711680);
				} else 
				if (array[i][j] == 2) {
					image.setRGB(j, i, 16711680);
				}
			}
		}
		try {
			ImageIO.write(image, "PNG", new File(s));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private static boolean roomIntersection(Room r, ArrayList<Room> rooms) {
		for (Room room: rooms) {
			if (room.overlaps(r, 5)) {
				return true;
			}
		}
		return false;
	}
	
	public static void bindTwo(Room r1, Room r2, int[][] array) {
		int r1x = ((int) (r1.x)) + r1.w/2;
		int r1y = ((int) (r1.y)) + r1.h/2;
		int r2x = ((int) (r2.x)) + r2.w/2;
		int r2y = ((int) (r2.y)) + r2.h/2;
		
		int distx = r2x - r1x;
		int shiftx = 0;
		if (distx < 0) {
			shiftx = distx;
			distx *= -1;
		}
		for (int i=r1x; i < r1x+distx+1; i++) {
			try {
				array[r1y][i+shiftx] = 2;
			} catch (Exception e) {}
		}
		
		int disty = r2y - r1y;
		int shifty = 0;
		if (disty < 0) {
			shifty = disty;
			disty *= -1;
		}
		for (int i=r1y; i < r1y+disty+1; i++) {
			try {
				array[i+shifty][r2x] = 2;
			} catch (Exception e) {}
		}
		
		
	}
	
	public static void createAllRoutes(ArrayList<Room> rooms) {
		for (Room r: rooms) {
			for (Room r2: rooms) {
				if (r != r2) {
					r.addRoomDist(r2);
				}
			}
		}
	}
	
	public static ArrayList<Rib> getPrim(ArrayList<Room> rooms){
		ArrayList<Rib> ribs = new ArrayList<Rib>();
		HashSet<Room> inTree = new HashSet<Room>();
		
		Room r1 = rooms.get(0);
		Room r2 = r1.dists.firstEntry().getValue();
		ribs.add(new Rib(r1, r2));
		inTree.add(r1);
		inTree.add(r2);
		
		while (inTree.size() != rooms.size()) {
			ArrayList<Rib> minRibs = new ArrayList<Rib>();
			for (Room r: inTree) {
				for (Room rpair: r.dists.values()) {
					if (!inTree.contains(rpair)) {
						minRibs.add(new Rib(r, rpair));
						break;
					}
				}
			}
			Rib minimalRib = minRibs.get(0);
			for (Rib rib: minRibs) {
				if (rib.dist < minimalRib.dist) {
					minimalRib = rib;
				}
			}
			ribs.add(minimalRib);
			if (!inTree.contains(minimalRib.r1))
				inTree.add(minimalRib.r1);
			if (!inTree.contains(minimalRib.r2))
				inTree.add(minimalRib.r2);
			
		}
		
		return ribs;
		
	}

	public static ArrayList<Room> generateRooms(int count, Random random, int[][] array) {
		ArrayList<Room> rooms = new ArrayList<Room>();
		for (int i=0; i < count; i++) {
			int w = random.nextInt(5)+5;
			int h = random.nextInt(5)+5;
			if (w % 2 == 0) w+=1;
			if (h % 2 == 0) h+=1; 
			Room r = new Room(array.length/2-w/2, array.length/2-h/2, w, h);
			if (roomIntersection(r, rooms)) {
				int angle = random.nextInt(360);
				double rad = Math.toRadians(angle);
				while (roomIntersection(r, rooms)) {
					r.x += Math.cos(rad)*2;
					r.y += Math.sin(rad)*2;
					
				}
			}
			r.x = (float) Math.floor(r.x);
			r.y = (float) Math.floor(r.y);
			if (roomInArea(r, array))
				rooms.add(r);
		}
		
		fillMapWithRooms(rooms, array);
		return rooms;
	}

	private static boolean roomInArea(Room r, int[][] array) {
		if (r.x >= 1 && r.x+r.w < array.length-2 &&
				r.y >= 1 && r.y+r.h < array.length-2) {
			return true;
		} else
			return false;
	}

	private static void fillMapWithRooms(ArrayList<Room> rooms, int[][] array) {
		for (Room r: rooms) {
			for (int x = (int) r.x; x < r.x+r.w; x++) {
				for (int y = (int) r.y; y < r.y+r.h; y++) {
					try {
						array[y][x] = 1;
					} catch (Exception e) {}
				}
			}
		}
	}

	public static void bindRibs(ArrayList<Rib> ribs, int[][] array) {
		for (Rib r: ribs) {
			MapGeneration.bindTwo(r.r1, r.r2, array);
			
		}
		
	}

}

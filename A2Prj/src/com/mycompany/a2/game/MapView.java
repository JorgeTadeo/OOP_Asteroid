package com.mycompany.a2.game;

import java.util.Observable;
import java.util.Observer;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Container;

/*
 * 1 of 2 Observer that observes GameWorld
 * 
 * Purpose : 
 */
public class MapView extends Container implements Observer{

	private int mapViewHeight;
	private int mapViewWidth;
	
	/*
	 * Constructor
	 */
	public MapView() {
		this.mapViewHeight = this.getHeight();
		this.mapViewWidth = this.getWidth();
		
		this.getAllStyles().setFgColor(ColorUtil.rgb(0, 0, 255));
	}
	
	
	/*
	 * Getters/Setters for MapView Height & Width
	 */
	public int getMapViewWidth() { return mapViewWidth; }
	public int getMapViewHeight() { return mapViewHeight; }
	public void setMapViewWidth(int width) { this.mapViewWidth = width; }
	public void setMapViewHeight(int height) { this.mapViewHeight = height; }
	
	
	@Override
	public void update(Observable observable, Object data) {
		((GameWorld)observable).printMap();
	}

	

}

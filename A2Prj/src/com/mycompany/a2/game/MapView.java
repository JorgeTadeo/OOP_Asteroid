package com.mycompany.a2.game;

import java.util.Observable;
import java.util.Observer;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Container;
import com.codename1.ui.Label;
import com.codename1.ui.TextArea;
import com.mycompany.a2.gameobjects.IIterator;

/*
 * 1 of 2 Observer that observes GameWorld
 * 
 * Purpose : 
 */
public class MapView extends Container implements Observer{

	private int mapViewHeight;
	private int mapViewWidth;
	
	private GameWorld gw;
	private TextArea mapValueTextArea;
	/*
	 * Constructor
	 */
	public MapView(GameWorld myGW) {
		this.mapViewHeight = this.getHeight();
		this.mapViewWidth = this.getWidth();
		this.gw = myGW;
		this.getAllStyles().setFgColor(ColorUtil.rgb(0, 0, 255));
		
		mapValueTextArea = new TextArea();
		this.add(mapValueTextArea);
		mapValueTextArea.setEditable(false);
		mapValueTextArea.setFocusable(false);

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
		

		IGameWorld gw = (IGameWorld) data;
		mapValueTextArea.setText("");
		
		IIterator iter = gw.getGameObjectIterator();
		while(iter.hasNext()) {
			mapValueTextArea.setText(mapValueTextArea.getText() + "\n" + iter.getNext());
		}
		
		
		this.repaint();
		
	
	}

	

}

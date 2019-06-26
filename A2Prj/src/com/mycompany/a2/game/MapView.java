package com.mycompany.a2.game;

import java.util.Observable;
import java.util.Observer;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Container;
import com.codename1.ui.TextArea;
import com.mycompany.a2.gameobjects.IIterator;

/*
 * 1 of 2 Observer that observes GameWorld
 * This is the center region of the game
 * Purpose : 
 */
public class MapView extends Container implements Observer{

	private int mapViewHeight;
	private int mapViewWidth;
	
	private TextArea mapValueTextArea;
	
	
	/*
	 * Constructor
	 */
	public MapView(GameWorld myGW) {
		this.mapViewHeight = this.getHeight();
		this.mapViewWidth = this.getWidth();
		this.getAllStyles().setFgColor(ColorUtil.rgb(0, 0, 255));
		
		//Add Component to Container
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
	
	
	/**
	 * Description: Draw each object to mapview.
	 */
	@Override
	public void update(Observable observable, Object data) {
		
		/*
		 * Prints all gameObject using GameObjectIterator to textArea
		 */
		IGameWorld gw = (IGameWorld) data;
		mapValueTextArea.setText("");
		
		IIterator iter = gw.getGameObjectIterator();
		while(iter.hasNext()) {
			mapValueTextArea.setText(mapValueTextArea.getText() + "\n" + iter.getNext());
		}
		
		
		this.repaint();
		
	
	}

	

}

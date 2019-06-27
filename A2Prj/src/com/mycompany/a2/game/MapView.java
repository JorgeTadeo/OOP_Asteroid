package com.mycompany.a2.game;

import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;

import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Container;
import com.codename1.ui.Graphics;
import com.codename1.ui.Slider;
import com.codename1.ui.TextArea;
import com.mycompany.a2.gameobjects.GameObject;
import com.mycompany.a2.gameobjects.IIterator;
import com.mycompany.a2.gameobjects.moveableobject.PlayerShip;

/*
 * 1 of 2 Observer that observes GameWorld
 * This is the center region of the game
 * Purpose : 
 */
public class MapView extends Container implements Observer{

	private int mapViewHeight;
	private int mapViewWidth;
	
	private TextArea mapValueTextArea;
	private IGameWorld gw;
	
	/*
	 * Constructor
	 */
	public MapView(GameWorld myGW) {
		this.mapViewHeight = this.getHeight();
		this.mapViewWidth = this.getWidth();
		this.getAllStyles().setFgColor(ColorUtil.rgb(0, 0, 255));
		/*
		//Add Component to Container
		mapValueTextArea = new TextArea();
		mapValueTextArea.setEditable(false);
		mapValueTextArea.setFocusable(false);
	
		this.add(mapValueTextArea);
		*/
		
		

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
		/* OLD 
		IGameWorld gw = (IGameWorld) data;
		mapValueTextArea.setText("");
		
		IIterator iter = gw.getGameObjectIterator();
		while(iter.hasNext()) {
			//mapValueTextArea.setText(mapValueTextArea.getText() + "\n" + iter.getNext());
		}
		*/
		
		/*
		 * NEW
		 */
		this.gw = (IGameWorld) data;
		this.repaint();
		
	
	}
	
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Point mapViewOrigin = new Point(getX(),getY());
		//System.out.println("In Mapview.paint()");
	//	System.out.println(this.getWidth()  + " , " + this.getHeight());
		if(gw != null) {
			IIterator iter = gw.getGameObjectIterator();
			while(iter.hasNext()) {
				GameObject obj = iter.getNext();
				if(obj instanceof PlayerShip) {
					obj.draw(g, mapViewOrigin);
				}
			}
		}
		g.fillRect(getX()+10, getY()+10, 200, 200);
		
	}
	

}

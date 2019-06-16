package com.mycompany.a2.game;

import java.util.Observable;
import java.util.Observer;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Container;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BoxLayout;


/*
 * 1 of 2 Observer that observes GameWorld
 */
public class PointsView extends Container implements Observer {

	/*
	 * Fields 
	 */
	private Label pointsValueLabel;
	
	
	/*
	 * Constructor
	 */
	public PointsView() {
		
		// Points Label setup
		Label pointsTextLabel = new Label("Points:"); 	
		pointsTextLabel.getAllStyles().setFgColor(ColorUtil.rgb(0,0,255)); 		 
		pointsValueLabel = new Label("XXX");  		
		
		//Adding a container with Box Layout 
		Container myContainer = new Container();
		myContainer.setLayout(new BoxLayout(BoxLayout.X_AXIS));
		
		//Add labels to container 
		myContainer.add(pointsTextLabel);
		myContainer.add(pointsValueLabel);
		
		//Add container to PointsView content pane
		this.add(myContainer);
	}
	
	
	

	
	@Override
	public void update(Observable observable, Object data) {
		IGameWorld gw = (IGameWorld) data;
		//this.pointsValueLabel.setText("" + gw.getPlayerScore());
		//TODO Update more stuff about gameworld
		//this.repaint();
	}

}

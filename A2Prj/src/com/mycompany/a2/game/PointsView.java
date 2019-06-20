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
	private Label missileValueLabel;
	private Label gameTickValueLabel;
	private Label lifeValueLabel;
	private GameWorld gw;
	private Label soundValueLabel;
	
	
	/*
	 * Constructor
	 */
	public PointsView(GameWorld myGW) {
		
		// Points Label setup
		Label pointsTextLabel = new Label("Points:"); 	
		pointsTextLabel.getAllStyles().setFgColor(ColorUtil.rgb(0,0,255)); 		 
		pointsValueLabel = new Label(Integer.toString(myGW.getPlayerScore()));  		
		
		//Lives setup 
		Label lifeTextLabel = new Label("Life:"); 	
		lifeTextLabel.getAllStyles().setFgColor(ColorUtil.rgb(0,0,255)); 		 
		lifeValueLabel = new Label(Integer.toString(myGW.getLife()));  	
		
		//# of missile setup 
		Label numMissileTextLabel = new Label("Missiles Left:");
		numMissileTextLabel.getAllStyles().setFgColor(ColorUtil.rgb(0,0,255)); 	
		missileValueLabel = new Label(Integer.toString(myGW.getPSMissileCount()));
		
		//Time  setup 
		Label gameTickTextLabel = new Label("Time:");
		gameTickTextLabel.getAllStyles().setFgColor(ColorUtil.rgb(0,0,255)); 
		gameTickValueLabel = new Label(Integer.toString(myGW.getGameTime()));
		
		//Sound setup
		Label soundTextLabel = new Label("Sound:");
		soundTextLabel.getAllStyles().setFgColor(ColorUtil.rgb(0,0,255)); 
		soundValueLabel = new Label("OFF");
		
		//Adding a container with Box Layout 
		Container myContainer = new Container();
		myContainer.setLayout(new BoxLayout(BoxLayout.X_AXIS));
		
		//Add labels to container 
		myContainer.add(pointsTextLabel);
		myContainer.add(pointsValueLabel);
		myContainer.add(numMissileTextLabel);
		myContainer.add(missileValueLabel);
		myContainer.add(gameTickTextLabel);
		myContainer.add(gameTickValueLabel);
		myContainer.add(lifeTextLabel);
		myContainer.add(lifeValueLabel);
		myContainer.add(soundTextLabel);
		myContainer.add(soundValueLabel);
		
		//Add container to PointsView content pane
		this.add(myContainer);
	}
	
	
	

	
	@Override
	public void update(Observable observable, Object data) { //data is proxy , observable is real gw 
		//TODO Update playerscore , timer .. and other pointView related data
		IGameWorld gw = (IGameWorld) data;
		this.pointsValueLabel.setText("" + gw.getPlayerScore());
		this.missileValueLabel.setText("" + gw.getPSMissileCount());
		this.gameTickValueLabel.setText("" + gw.getGameTime());
		this.lifeValueLabel.setText("" + gw.getLife());
		
		if(gw.getSound() == true) {
			this.soundValueLabel.setText("ON");
		}else {
			this.soundValueLabel.setText("OFF");
		}
		
		//System.out.println("PointView Updated...");
		this.repaint();
	}

}

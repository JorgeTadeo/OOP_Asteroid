package com.mycompany.a2.game;

import java.util.Observable;
import java.util.Observer;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BoxLayout;

public class PointsView extends Container implements Observer {

	/*
	 * Fields 
	 */
	private Label pointsValueLabel;
	
	
	/*
	 * Constructor
	 */
	public PointsView() {
		//Initialize text label
		Label pointsTextLabel = new Label("Points:");
		//Initilize value label 
		pointsValueLabel = new Label("XXX");
		//set color 
		pointsTextLabel.getAllStyles().setFgColor(ColorUtil.rgb(0,0,255));
		
		//Adding a container with boxlayout 
		Container myContainer = new Container();
		myContainer.setLayout(new BoxLayout(BoxLayout.X_AXIS));
		
		//Adding all labels in order 
		myContainer.add(pointsTextLabel);
		this.add(myContainer);
	}
	
	
	

	
	@Override
	public void update(Observable observable, Object data) {
		// TODO Auto-generated method stub
		
	}

}

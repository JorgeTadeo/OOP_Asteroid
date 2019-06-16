package com.mycompany.a2.game;

import com.codename1.ui.Form;

public class Game extends Form{

	/*
	 * Fields 
	 */
	private GameWorld gw;
	private MapView mv;
	private PointsView pv;
	
	/*
	 * Constructor
	 */
	public Game() {
		gw = new GameWorld();  //Create "Observable" 
		mv = new MapView();    //Create "Observer"
		pv = new PointsView(); //Create "Observer"
		
		gw.addObserver(mv); //Register mv 
		gw.addObserver(pv); //Register pv 
		
		
		/*
		 * TODO
		 * 
		 * create menus , create command objects for each command ,
		 * add command to command menu, create a control panel for the buttons,
		 * add buttons to the control panel, add commands to the buttons, and 
		 * add control panel, MapView panel, and PointsView panel to the form
		 * 
		 */
		
		this.show();
		
	}
	
}

package com.mycompany.a2.game;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Button;
import com.codename1.ui.CheckBox;
import com.codename1.ui.Container;
import com.codename1.ui.Form;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.UITimer;
import com.mycompany.a2.command.CommandAbout;
import com.mycompany.a2.command.CommandAddAsteroid;
import com.mycompany.a2.command.CommandAddNonPlayerShip;
import com.mycompany.a2.command.CommandAddPlayerShip;
import com.mycompany.a2.command.CommandAddSpaceStation;
import com.mycompany.a2.command.CommandDecreasePlayerShipSpeed;
import com.mycompany.a2.command.CommandIncreasePlayerShipSpeed;
import com.mycompany.a2.command.CommandJump;
import com.mycompany.a2.command.CommandKillAsteroidByAsteroid;
import com.mycompany.a2.command.CommandKillNonPlayerShipByAsteroid;
import com.mycompany.a2.command.CommandKillNonPlayerShipByPSMissile;
import com.mycompany.a2.command.CommandKillPlayerShipByAsteroid;
import com.mycompany.a2.command.CommandKillPlayerShipByMissile;
import com.mycompany.a2.command.CommandKillPlayerShipByNonPlayerShip;
import com.mycompany.a2.command.CommandKillsAsteroidByPSMissile;
import com.mycompany.a2.command.CommandNew;
import com.mycompany.a2.command.CommandNonPlayerShipFireMissile;
import com.mycompany.a2.command.CommandPlayerShipFireMissile;
import com.mycompany.a2.command.CommandPrintMap;
import com.mycompany.a2.command.CommandQuit;
import com.mycompany.a2.command.CommandReloadPlayerShipMissile;
import com.mycompany.a2.command.CommandSave;
import com.mycompany.a2.command.CommandSoundToggle;
import com.mycompany.a2.command.CommandTick;
import com.mycompany.a2.command.CommandTurnMissileLauncherLeft;
import com.mycompany.a2.command.CommandTurnMissileLauncherRight;
import com.mycompany.a2.command.CommandTurnPlayerShipLeft;
import com.mycompany.a2.command.CommandTurnPlayerShipRight;
import com.mycompany.a2.command.CommandUndo;

public class Game extends Form implements Runnable{

	/*
	 * Fields 
	 */
	private GameWorld gw;
	private MapView mv;
	private PointsView pv;
	
	UITimer timer = new UITimer(this);
	
	/*
	 * Constructor
	 */
	public Game() {
		gw = GameWorld.getInstance();  //Create "Observable" / GameWorld
		gw.init(); //Intialize GameWorld
		mv = new MapView(gw);    //Create "Observer"
		pv = new PointsView(gw); //Create "Observer"
		

		gw.addObserver(mv); //Register Observer : mv
		gw.addObserver(pv); //Register Observer : pv
		
		timer.schedule(1000,true,this);
		
		//Game Form layout setup  
		setLayout(new BorderLayout());

		
		
		/**********LEFTMENU BAR SETUP **********/
		
		
		//Toolbar 
		Toolbar toolBar = new Toolbar();
		Toolbar.setOnTopSideMenu(false);
		setToolbar(toolBar);
		toolBar.setTitle("Asteroid Game");

		//Toolbar Menu Items 
		CheckBox soundCheckBox = new CheckBox();
		Button quitButton = new Button();	
		Button aboutButton = new Button();	
		Button newButton = new Button();		
		Button saveButton = new Button();
		Button undoButton = new Button();

		//Make Menu Items fancy
		soundCheckBox = applyMakeUp(soundCheckBox);
		quitButton = applyMakeup(quitButton);
		aboutButton = applyMakeup(aboutButton);
		newButton = applyMakeup(newButton);
		saveButton = applyMakeup(saveButton);
		undoButton = applyMakeup(undoButton);
		
		//Disable key navigation
		soundCheckBox.setFocusable(false);
		quitButton.setFocusable(false);
		aboutButton.setFocusable(false);
		newButton.setFocusable(false);
		saveButton.setFocusable(false);
		undoButton.setFocusable(false);
		
		
		//Command Listener for checkboxes and buttons 
		soundCheckBox.setCommand(new CommandSoundToggle(gw));
		quitButton.setCommand(new CommandQuit(gw));
		aboutButton.setCommand(new CommandAbout(gw));
		newButton.setCommand(new CommandNew(gw));
		saveButton.setCommand(new CommandSave(gw));
		undoButton.setCommand(new CommandUndo(gw));
		

		//Add components to toolbar side menu 
		toolBar.addComponentToSideMenu(soundCheckBox);
		toolBar.addComponentToSideMenu(quitButton);
		toolBar.addComponentToSideMenu(aboutButton);
		toolBar.addComponentToSideMenu(newButton);
		toolBar.addComponentToSideMenu(saveButton);
		toolBar.addComponentToSideMenu(undoButton);
		
		
		/******* END OF LEFTMENU BAR SETUP ******/
		
		
		
		
		
		
		/****** WEST CONTAINER SETUP  *******/
		
		//West Container
		Container leftContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));
		
		
		//West Container Buttons 
		Button addAsteroidButton = new Button();
		Button mapButton = new Button(); //DEBUG ONLY
		Button addNPSButton = new Button();
		Button addSpaceStationButton = new Button();
		Button addPlayerShipButton = new Button();
		Button playerShipFireButton = new Button();
		Button jumpButton = new Button();
		
		//Make buttons fancy
		addAsteroidButton = applyMakeup(addAsteroidButton);
		mapButton = applyMakeup(mapButton);
		addNPSButton = applyMakeup(addNPSButton);
		addSpaceStationButton = applyMakeup(addSpaceStationButton);
		addPlayerShipButton = applyMakeup(addPlayerShipButton);
		playerShipFireButton = applyMakeup(playerShipFireButton);
		jumpButton = applyMakeup(jumpButton);

		//Disable arrow key navigation for buttons
		addAsteroidButton.setFocusable(false);
		addNPSButton.setFocusable(false);
		addSpaceStationButton.setFocusable(false);
		addPlayerShipButton.setFocusable(false);
		jumpButton.setFocusable(false);
		playerShipFireButton.setFocusable(false);
		
		//Set commands for buttons
		addAsteroidButton.setCommand(new CommandAddAsteroid(gw));
		mapButton.setCommand(new CommandPrintMap(gw));
		addNPSButton.setCommand(new CommandAddNonPlayerShip(gw));
		addSpaceStationButton.setCommand(new CommandAddSpaceStation(gw));
		addPlayerShipButton.setCommand(new CommandAddPlayerShip(gw));
		playerShipFireButton.setCommand(new CommandPlayerShipFireMissile(gw));
		jumpButton.setCommand(new CommandJump(gw));


		//Add components to West Container
		leftContainer.add(addAsteroidButton);
		leftContainer.add(addNPSButton);
		leftContainer.add(addSpaceStationButton);
		leftContainer.add(addPlayerShipButton);
		leftContainer.add(playerShipFireButton);
		leftContainer.add(jumpButton);
		
		
		/****** END OF WEST CONTAINER SETUP  *******/
		
		
		
		
		
		
		
		//Key Bindings
		addKeyListener('m', mapButton.getCommand()); //DEBUG ONLY
		addKeyListener('i', new CommandIncreasePlayerShipSpeed(gw));
		addKeyListener(-91 , new CommandIncreasePlayerShipSpeed(gw)); //Up arrow 
		addKeyListener('d', new CommandDecreasePlayerShipSpeed(gw)); 
		addKeyListener(-92 , new CommandDecreasePlayerShipSpeed(gw)); //Down arrow  
		addKeyListener('l', new CommandTurnPlayerShipLeft(gw));
		addKeyListener(-93 , new CommandTurnPlayerShipLeft(gw)); //Left arrow 
		addKeyListener('r', new CommandTurnPlayerShipRight(gw));
		addKeyListener(-94, new CommandTurnPlayerShipRight(gw)); //Right arrow 
		addKeyListener(44 , new CommandTurnMissileLauncherLeft(gw));
		addKeyListener(46, new CommandTurnMissileLauncherRight(gw));
		addKeyListener(-90, new CommandPlayerShipFireMissile(gw)); //Space Bar
		addKeyListener('L' , new CommandNonPlayerShipFireMissile(gw));
		addKeyListener('j' , new CommandJump(gw));
		addKeyListener('n', new CommandReloadPlayerShipMissile(gw));
		addKeyListener('k', new CommandKillsAsteroidByPSMissile(gw));
		addKeyListener('e', new CommandKillNonPlayerShipByPSMissile(gw));
		addKeyListener('E', new CommandKillPlayerShipByMissile(gw));
		addKeyListener('c', new CommandKillPlayerShipByAsteroid(gw));
		addKeyListener('h', new CommandKillPlayerShipByNonPlayerShip(gw));
		addKeyListener('x', new CommandKillAsteroidByAsteroid(gw));
		addKeyListener('I', new CommandKillNonPlayerShipByAsteroid(gw));
		addKeyListener('t', new CommandTick(gw));
		this.addKeyListener('z', quitButton.getCommand()); //QUIT
		

		
		/*
		 * Add all container to contentpane
		 */
		add(BorderLayout.WEST, leftContainer);
		add(BorderLayout.NORTH , pv);
		add(BorderLayout.CENTER,mv);

		
		
		this.show();
		
		
		System.out.println("Game GUI Setup Completed with the following stats :");
		
		System.out.println("Form Content pane size : " + this.getWidth() + "," + this.getHeight());
		System.out.println("MapView size : " + mv.getWidth() + "," + mv.getHeight());
		System.out.println("MapView Origin : " + mv.getX() + "," + mv.getY());
		
		
	}
	
	
	
	private Button applyMakeup(Button obj) {
		obj.getAllStyles().setBgTransparency(255);
		obj.getUnselectedStyle().setBgColor(ColorUtil.rgb(0, 150, 150));
		obj.getAllStyles().setFgColor(ColorUtil.rgb(255, 255, 255));
		obj.getAllStyles().setPadding(TOP, 5);
		obj.getAllStyles().setPadding(BOTTOM, 5);
		return obj;
	}
	
	private CheckBox applyMakeUp(CheckBox obj) {
		obj.getAllStyles().setBgTransparency(255);
		obj.getUnselectedStyle().setBgColor(ColorUtil.rgb(0, 150, 150));
		obj.getAllStyles().setFgColor(ColorUtil.rgb(255, 255, 255));
		obj.getAllStyles().setPadding(TOP, 5);
		obj.getAllStyles().setPadding(BOTTOM, 5);
		return obj;
	}


	/**
	 * Invoke when timer ticks
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		gw.tick();
		
	}
	
}

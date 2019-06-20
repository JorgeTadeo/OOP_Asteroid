package com.mycompany.a2.game;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Button;
import com.codename1.ui.CheckBox;
import com.codename1.ui.Container;
import com.codename1.ui.Form;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
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

public class Game extends Form{

	/*
	 * Fields 
	 */
	private GameWorld gw;
	private MapView mv;
	private PointsView pv;
	private GameWorldProxy gwProxy;
	
	/*
	 * Constructor
	 */
	public Game() {
		gw = GameWorld.getInstance();  //Create "Observable" 
		gw.init();
		gwProxy = new GameWorldProxy(gw);
		mv = new MapView(gw);    //Create "Observer"
		pv = new PointsView(gw); //Create "Observer"
		

		gw.addObserver(mv); //Register mv 
		gw.addObserver(pv); //Register pv 
		
		
		//Game UI layout setup  
		setLayout(new BorderLayout());

		
		//Toolbar 
		Toolbar toolBar = new Toolbar();
		Toolbar.setOnTopSideMenu(false);
		setToolbar(toolBar);

		//Toolbar Menu Items 
		CheckBox soundCheckBox = new CheckBox();
		soundCheckBox.getAllStyles().setBgTransparency(255);
		soundCheckBox.getUnselectedStyle().setBgColor(ColorUtil.rgb(0, 150, 150));
		soundCheckBox.getAllStyles().setFgColor(ColorUtil.rgb(255, 255, 255));
		soundCheckBox.getAllStyles().setPadding(TOP, 5);
		soundCheckBox.getAllStyles().setPadding(BOTTOM, 5);
		

		
		Button quitButton = new Button("quitButton");
		quitButton.getAllStyles().setBgTransparency(255);
		quitButton.getUnselectedStyle().setBgColor(ColorUtil.rgb(0, 150, 150));
		quitButton.getAllStyles().setFgColor(ColorUtil.rgb(255, 255, 255));
		quitButton.getAllStyles().setPadding(TOP, 5);
		quitButton.getAllStyles().setPadding(BOTTOM, 5);
		
		Button aboutButton = new Button();
		aboutButton.getAllStyles().setBgTransparency(255);
		aboutButton.getUnselectedStyle().setBgColor(ColorUtil.rgb(0, 150, 150));
		aboutButton.getAllStyles().setFgColor(ColorUtil.rgb(255, 255, 255));
		aboutButton.getAllStyles().setPadding(TOP, 5);
		aboutButton.getAllStyles().setPadding(BOTTOM, 5);
		
		Button newButton = new Button();
		newButton.getAllStyles().setBgTransparency(255);
		newButton.getUnselectedStyle().setBgColor(ColorUtil.rgb(0, 150, 150));
		newButton.getAllStyles().setFgColor(ColorUtil.rgb(255, 255, 255));
		newButton.getAllStyles().setPadding(TOP, 5);
		newButton.getAllStyles().setPadding(BOTTOM, 5);
		
		Button saveButton = new Button();
		saveButton.getAllStyles().setBgTransparency(255);
		saveButton.getUnselectedStyle().setBgColor(ColorUtil.rgb(0, 150, 150));
		saveButton.getAllStyles().setFgColor(ColorUtil.rgb(255, 255, 255));
		saveButton.getAllStyles().setPadding(TOP, 5);
		saveButton.getAllStyles().setPadding(BOTTOM, 5);
		
		Button undoButton = new Button();
		undoButton.getAllStyles().setBgTransparency(255);
		undoButton.getUnselectedStyle().setBgColor(ColorUtil.rgb(0, 150, 150));
		undoButton.getAllStyles().setFgColor(ColorUtil.rgb(255, 255, 255));
		undoButton.getAllStyles().setPadding(TOP, 5);
		undoButton.getAllStyles().setPadding(BOTTOM, 5);
		
		
		
		//Command Listener for checkboxes and buttons 
		soundCheckBox.setCommand(new CommandSoundToggle(gw));
		quitButton.setCommand(new CommandQuit(gw));
		aboutButton.setCommand(new CommandAbout(gw));
		newButton.setCommand(new CommandNew(gw));
		saveButton.setCommand(new CommandSave(gw));
		undoButton.setCommand(new CommandUndo(gw));
		

		toolBar.addComponentToSideMenu(soundCheckBox);
		toolBar.addComponentToSideMenu(quitButton);
		toolBar.addComponentToSideMenu(aboutButton);
		toolBar.addComponentToSideMenu(newButton);
		toolBar.addComponentToSideMenu(saveButton);
		toolBar.addComponentToSideMenu(undoButton);
		
		//keybinding for side menu bar 
		this.addKeyListener('z', quitButton.getCommand());



		
		setToolbar(toolBar);
		
		
		
		//Buttons 
		Button addAsteroidButton = new Button("newAsteroidButton");
		addAsteroidButton.getAllStyles().setBgTransparency(255);
		addAsteroidButton.getUnselectedStyle().setBgColor(ColorUtil.rgb(0, 150, 150));
		addAsteroidButton.getAllStyles().setFgColor(ColorUtil.rgb(255, 255, 255));
		addAsteroidButton.getAllStyles().setPadding(TOP, 5);
		addAsteroidButton.getAllStyles().setPadding(BOTTOM, 5);
		
		
		Button mapButton = new Button("mapButton");
		mapButton.getAllStyles().setBgTransparency(255);
		mapButton.getUnselectedStyle().setBgColor(ColorUtil.rgb(0, 150, 150));
		mapButton.getAllStyles().setFgColor(ColorUtil.rgb(255, 255, 255));
		mapButton.getAllStyles().setPadding(TOP, 5);
		mapButton.getAllStyles().setPadding(BOTTOM, 5);
		
		Button addNPSButton = new Button("newNPSButton");
		addNPSButton.getAllStyles().setBgTransparency(255);
		addNPSButton.getUnselectedStyle().setBgColor(ColorUtil.rgb(0, 150, 150));
		addNPSButton.getAllStyles().setFgColor(ColorUtil.rgb(255, 255, 255));
		addNPSButton.getAllStyles().setPadding(TOP, 5);
		addNPSButton.getAllStyles().setPadding(BOTTOM, 5);
		
		Button addSpaceStationButton = new Button("newSpaceStationButton");
		addSpaceStationButton.getAllStyles().setBgTransparency(255);
		addSpaceStationButton.getUnselectedStyle().setBgColor(ColorUtil.rgb(0, 150, 150));
		addSpaceStationButton.getAllStyles().setFgColor(ColorUtil.rgb(255, 255, 255));
		addSpaceStationButton.getAllStyles().setPadding(TOP, 5);
		addSpaceStationButton.getAllStyles().setPadding(BOTTOM, 5);
		
		Button addPlayerShipButton = new Button("newAddPlayerShipButton");
		addPlayerShipButton.getAllStyles().setBgTransparency(255);
		addPlayerShipButton.getUnselectedStyle().setBgColor(ColorUtil.rgb(0, 150, 150));
		addPlayerShipButton.getAllStyles().setFgColor(ColorUtil.rgb(255, 255, 255));
		addPlayerShipButton.getAllStyles().setPadding(TOP, 5);
		addPlayerShipButton.getAllStyles().setPadding(BOTTOM, 5);
		
		Button playerShipFireButton = new Button("PlayerShipFireButton");
		playerShipFireButton.getAllStyles().setBgTransparency(255);
		playerShipFireButton.getUnselectedStyle().setBgColor(ColorUtil.rgb(0, 150, 150));
		playerShipFireButton.getAllStyles().setFgColor(ColorUtil.rgb(255, 255, 255));
		playerShipFireButton.getAllStyles().setPadding(TOP, 5);
		playerShipFireButton.getAllStyles().setPadding(BOTTOM, 5);
		
		Button jumpButton = new Button("JumpButton");
		jumpButton.getAllStyles().setBgTransparency(255);
		jumpButton.getUnselectedStyle().setBgColor(ColorUtil.rgb(0, 150, 150));
		jumpButton.getAllStyles().setFgColor(ColorUtil.rgb(255, 255, 255));
		jumpButton.getAllStyles().setPadding(TOP, 5);
		jumpButton.getAllStyles().setPadding(BOTTOM, 5);
		
		addAsteroidButton.setFocusable(false);
		addNPSButton.setFocusable(false);
		addSpaceStationButton.setFocusable(false);
		addPlayerShipButton.setFocusable(false);
		jumpButton.setFocusable(false);
		playerShipFireButton.setFocusable(false);
		
	
		//Command Container 
		Container leftContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));
		

		addAsteroidButton.setCommand(new CommandAddAsteroid(gw));
		mapButton.setCommand(new CommandPrintMap(gw));
		addNPSButton.setCommand(new CommandAddNonPlayerShip(gw));
		addSpaceStationButton.setCommand(new CommandAddSpaceStation(gw));
		addPlayerShipButton.setCommand(new CommandAddPlayerShip(gw));
		playerShipFireButton.setCommand(new CommandPlayerShipFireMissile(gw));
		jumpButton.setCommand(new CommandJump(gw));


		
		addKeyListener('m', mapButton.getCommand());
		
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
		
		
		leftContainer.add(addAsteroidButton);
		leftContainer.add(addNPSButton);
		leftContainer.add(addSpaceStationButton);
		leftContainer.add(addPlayerShipButton);
		leftContainer.add(playerShipFireButton);
		leftContainer.add(jumpButton);
		
		//leftContainer.add(mapButton);
		
		
		
		

		
		
		
		
		
		//Add everything to content pane 
		
		//leftContainer.setFocusable(false);
		this.setFocusable(false);

		add(BorderLayout.WEST, leftContainer);
		add(BorderLayout.NORTH , pv);
		add(BorderLayout.CENTER,mv);

		
		
		this.show();
		
	}
	
}

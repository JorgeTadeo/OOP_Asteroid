package com.mycompany.a2.game;

import java.util.Observable;

/*
 * MODEL 
 */
public class GameWorld extends Observable implements IGameWorld{

	/*
	 * Fields 
	 */
	//private GameCollection gameObjects;
	
	private boolean soundON;
	
	/*
	 * Constructor
	 */
	public GameWorld() {
	//	gameObjects = new GameCollection();
	//	this.init();
	}

	
	
	//This method will be called by MapView to update mapview
	public void printMap() {
		// TODO Auto-generated method stub
		
	}
	
	
}

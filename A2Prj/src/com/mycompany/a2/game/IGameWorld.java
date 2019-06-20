package com.mycompany.a2.game;

import com.mycompany.a2.gameobjects.IIterator;

public interface IGameWorld {


	int getPlayerScore();
	public int getGameTime();
	public int getPSMissileCount();

	
	
	void printMap();
	
	void tick();
	void addAsteroid();
	void addPlayerShip();
	void addNonPlayerShip();
	void addSpaceStation();
	void addPlayerMissile();
	void addNonPlayerMissile();
	void printDisplay();
	void turnMissileLauncherLeft();
	void turnMissileLauncherRight();
	void playerShipReload();
	void killAsteroid();
	void killNonPlayerShipByMissile();
	void killPlayerShipByMissile();
	void killPlayerShipByAsteroid();
	void killPlayerShipByNPS();
	void asteroidCollision();
	void killNonPlayerShipByAsteroid();
	void increasePlayerShipSpeed();
	void decreasePlayerShipSpeed();
	void turnPSLeft();
	void turnPSRight();
	void jumpToHyperSpace();
	public IIterator getGameObjectIterator();
	
}

package com.mycompany.a2.game;

import java.util.Observable;

import com.mycompany.a2.gameobjects.IIterator;

public class GameWorldProxy extends Observable implements IGameWorld {

	
	private GameWorld gw;
	
	public GameWorldProxy(GameWorld gw) { this.gw = gw; }
	

	
	@Override
	public void addAsteroid() {
		// TODO Auto-generated method stub
		gw.addAsteroid();
	}



	@Override
	public int getPlayerScore() {
		// TODO Auto-generated method stub
		return gw.getPlayerScore();
	}



	@Override
	public void printMap() {
		 gw.printMap();
	}



	@Override
	public void addNonPlayerShip() {
		// TODO Auto-generated method stub
		gw.addNonPlayerShip();
	}



	@Override
	public void addSpaceStation() {
		// TODO Auto-generated method stub
		gw.addSpaceStation();
	}



	@Override
	public void tick() {
		// TODO Auto-generated method stub
		gw.tick();
	}



	@Override
	public void addPlayerShip() {
		// TODO Auto-generated method stub
		gw.addPlayerShip();
	}



	@Override
	public void addPlayerMissile() {
		// TODO Auto-generated method stub
		gw.addPlayerMissile();
	}



	@Override
	public void addNonPlayerMissile() {
		// TODO Auto-generated method stub
		gw.addNonPlayerMissile();
	}



	@Override
	public void printDisplay() {
		// TODO Auto-generated method stub
		gw.printDisplay();
	}





	@Override
	public void playerShipReload() {
		// TODO Auto-generated method stub
		gw.playerShipReload();
	}



	@Override
	public void killAsteroid() {
		// TODO Auto-generated method stub
		gw.killAsteroid();
	}



	@Override
	public void killNonPlayerShipByMissile() {
		// TODO Auto-generated method stub
		gw.killNonPlayerShipByMissile();
	}



	@Override
	public void killPlayerShipByMissile() {
		// TODO Auto-generated method stub
		gw.killPlayerShipByMissile();
	}



	@Override
	public void killPlayerShipByAsteroid() {
		// TODO Auto-generated method stub
		gw.killPlayerShipByAsteroid();
	}



	@Override
	public void killPlayerShipByNPS() {
		// TODO Auto-generated method stub
		gw.killPlayerShipByNPS();
	}



	@Override
	public void asteroidCollision() {
		// TODO Auto-generated method stub
		gw.asteroidCollision();
	}



	@Override
	public void killNonPlayerShipByAsteroid() {
		// TODO Auto-generated method stub
		gw.killNonPlayerShipByAsteroid();
	}



	@Override
	public void increasePlayerShipSpeed() {
		// TODO Auto-generated method stub
		gw.increasePlayerShipSpeed();
	}



	@Override
	public void decreasePlayerShipSpeed() {
		// TODO Auto-generated method stub
		gw.decreasePlayerShipSpeed();
	}



	@Override
	public void turnPSLeft() {
		// TODO Auto-generated method stub
		gw.turnPSLeft();
	}



	@Override
	public void turnPSRight() {
		// TODO Auto-generated method stub
		gw.turnPSRight();
	}



	@Override
	public void jumpToHyperSpace() {
		// TODO Auto-generated method stub
		gw.jumpToHyperSpace();
	}



	@Override
	public void turnMissileLauncherLeft() {
		// TODO Auto-generated method stub
		gw.turnMissileLauncherLeft();
	}



	@Override
	public void turnMissileLauncherRight() {
		// TODO Auto-generated method stub
		gw.turnMissileLauncherRight();
	}



	@Override
	public int getGameTime() {
		return gw.getGameTime();
	}



	@Override
	public int getPSMissileCount() {
		return gw.getPSMissileCount();
	}



	@Override
	public IIterator getGameObjectIterator() {
		// TODO Auto-generated method stub
		return gw.getGameObjectIterator();
	}

}

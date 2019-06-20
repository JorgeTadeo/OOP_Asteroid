package com.mycompany.a2.game;

import java.util.ArrayList;
import java.util.Observable;

import com.mycompany.a2.gameobjects.moveableobject.Asteroids;
import com.mycompany.a2.gameobjects.moveableobject.NonPlayerShip;
import com.mycompany.a2.gameobjects.fixedobject.FixedObject;
import com.mycompany.a2.gameobjects.fixedobject.SpaceStation;
import com.mycompany.a2.gameobjects.moveableobject.Missiles;
import com.mycompany.a2.gameobjects.moveableobject.MoveableObject;
import com.mycompany.a2.gameobjects.moveableobject.PlayerShip;
import com.mycompany.a2.gameobjects.moveableobject.Ship;
import com.mycompany.a2.game.GameWorld;
import com.mycompany.a2.gameobjects.GameObject;
import com.mycompany.a2.gameobjects.IIterator;
import com.mycompany.a2.gameobjects.SpaceCollection;

/*
 * MODEL 
 */
public class GameWorld extends Observable implements IGameWorld{

	/*
	 * Fields 
	 */
	//private GameCollection gameObjects;
	
	final private int gameWorldHeight = 1024;
	final private int gameWorldWidth = 768;
	private int playerScore;
	private int timer = 0;
	private SpaceCollection gameObjects;
	private int asteroidCount;
	private int psCount;
	private int npsCount;
	private int stationCount;
	private boolean soundOn;
	private int life;
	
	/*
	 * Constructor
	 */
	//Singleton GameWorld to enforce only instance of gameworld can ever be created.
	private volatile static GameWorld gw;
	GameWorld() {};
	public static GameWorld getInstance() {
		if(gw == null) {
			synchronized (GameWorld.class) {
				if(gw == null)
					gw = new GameWorld();
			}
		}
		return gw;
	}

	/*
	 * Initialize Game
	 */
	public void init() {
		this.playerScore = 0;
		this.timer = 0;
		gameObjects = new SpaceCollection();
		this.asteroidCount = 0;
		this.psCount = 0;
		this.npsCount = 0;
		this.stationCount = 0;
		this.soundOn = false;
		this.life = 3;
	}
	
	
	public int getPlayerScore() {
		return this.playerScore;	
	}
	
	public int getGameTime() {
		return this.timer;
	}
	
	public int getPSMissileCount() {
		/*
		for(int i = 0 ; i < gameObjects.size(); i++) {
			if(gameObjects.get(i) instanceof PlayerShip) {
				return ((PlayerShip)gameObjects.get(i)).getMissileCount();
			}
		}
		*/
		IIterator iter = gameObjects.getIterator();
		while(iter.hasNext()) {
			GameObject obj = iter.getNext();
			if(obj instanceof PlayerShip) {
				return ((PlayerShip)obj).getMissileCount();
			}
		}
		return 0;
	}
	
	

	/*
	 * GAME METHODS :
	 */
	
	
	/*
	 * Game tick
	 */
	public void tick() {
		timer++;
		/*
		for(int i = 0 ; i < gameObjects.size(); i++) {

			if(gameObjects.get(i) instanceof Missiles) {
				if(((Missiles) gameObjects.get(i)).getFuel() == 1) {
					gameObjects.remove(i);
					System.out.println("Missile out of fuel and is removed");
					return;
				}else {
					((Missiles) gameObjects.get(i)).decrementFuel();
				}
			}
			if(!(gameObjects.get(i) instanceof FixedObject))
				((MoveableObject) gameObjects.get(i)).move();
		}
		*/
		IIterator iter = gameObjects.getIterator();
		while(iter.hasNext()) {
			int index = iter.getCurrIndex();
			GameObject obj = iter.getNext();
			if(obj instanceof Missiles) {
				if(((Missiles) obj).getFuel() == 1){
					iter.remove(index);
					System.out.println("Missile out of fuel and is removed");
					return;
				}else {
					((Missiles) obj).decrementFuel();
				}
			}
			if(!(obj instanceof FixedObject))
				((MoveableObject) obj).move();
		}
			
		this.setChanged();
		this.notifyObservers(new GameWorldProxy(this));
	}
	
	public IIterator getGameObjectIterator() {
		return gameObjects.getIterator();
	}
	
	public void addAsteroid() {
		gameObjects.add(new Asteroids());
		this.asteroidCount++;
		this.setChanged();
		this.notifyObservers(new GameWorldProxy(this));
	}
	public void addPlayerShip() {
			PlayerShip ps = PlayerShip.getInstance(psCount);
			if(psCount == 0) {			
				gameObjects.add(ps);
				this.psCount = 1;
			}
			this.setChanged();
			this.notifyObservers(new GameWorldProxy(this));
	}
	public void addNonPlayerShip() {
		gameObjects.add(new NonPlayerShip());
		this.npsCount++;
		this.setChanged();
		this.notifyObservers(new GameWorldProxy(this));
	}
	public void addSpaceStation() {
		gameObjects.add(new SpaceStation());
		this.stationCount++;
		this.setChanged();
		this.notifyObservers(new GameWorldProxy(this));
	}
	public void addPlayerMissile() {	
		/*
		for(int i = 0 ; i < gameObjects.size(); i++) {
			if(gameObjects.get(i) instanceof PlayerShip ) {
				if(((Ship) gameObjects.get(i)).getMissileCount() > 0 ) {
					gameObjects.add(new Missiles((PlayerShip) gameObjects.get(i)));
					//Print Fired Missile
					System.out.println("Player ship fired missile");
					System.out.println(((Ship)gameObjects.get(i)).getMissileCount() + " missiles left");
					this.setChanged();
					this.notifyObservers(new GameWorldProxy(this));
				}else {
					//Print Ran out of missile 
					System.out.println("Player ship ran out of missile");
				}
				return;
			}
		}
		*/
		IIterator iter = gameObjects.getIterator();
		while(iter.hasNext()) {
			GameObject obj = iter.getNext();
			if(obj instanceof PlayerShip) {
				if(((PlayerShip) obj).getMissileCount() > 0) {
					gameObjects.add(new Missiles((PlayerShip) obj));
					System.out.println("Player ship fired missile");
					System.out.println(((PlayerShip) obj).getMissileCount() + " missiles left");
					this.setChanged();
					this.notifyObservers(new GameWorldProxy(this));
				}else {
					System.out.println("Player ship ran out of missile");
				}
				return;
			}
		}
		//No PlayerShip
		System.out.println("There exists no playership to fire missile");
		return;
	
	}
	public void addNonPlayerMissile() {
		/*
		for(int i = 0 ; i < gameObjects.size(); i++) {
			if(gameObjects.get(i) instanceof NonPlayerShip ) {
				if(((Ship) gameObjects.get(i)).getMissileCount() > 0) {
					gameObjects.add(new Missiles((NonPlayerShip) gameObjects.get(i)));
					System.out.println("Non-Player ship fired missile");
					System.out.println(((Ship)gameObjects.get(i)).getMissileCount() + " missiles left");
					this.setChanged();
					this.notifyObservers(new GameWorldProxy(this));
				}else {
					System.out.println("Non-Player ship ran out of missile");
				}
				return;
			}
		}
		*/
		IIterator iter = gameObjects.getIterator();
		while(iter.hasNext()) {
			GameObject obj = iter.getNext();
			if(obj instanceof NonPlayerShip ) {
				if(((Ship) obj).getMissileCount() > 0) {
					gameObjects.add(new Missiles((NonPlayerShip) obj));
					System.out.println("Non-Player ship fired missile");
					System.out.println(((Ship) obj).getMissileCount() + " missiles left");
					this.setChanged();
					this.notifyObservers(new GameWorldProxy(this));
				}else {
					System.out.println("Non-Player ship ran out of missile");
				}
				return;
			}
		}
		//No PlayerShip
		System.out.println("There exists no non-playership to fire missile");
		return;
	}

	public void printDisplay() {
		/*
		for(int i = 0 ; i < gameObjects.size(); i++) {
			if(gameObjects.get(i) instanceof PlayerShip) {
				System.out.println( "Current Score=" + this.playerScore +
						" Number of missile in ship=" + ((Ship) gameObjects.get(i)).getMissileCount() + 
						" Current elapased time=" + this.timer + 
						" Number of lives=" + ((PlayerShip) gameObjects.get(i)).getLife()
				);
			}
		}
		*/
		IIterator iter = gameObjects.getIterator();
		while(iter.hasNext()) {
			GameObject obj = iter.getNext();
			if(obj instanceof PlayerShip) {
				System.out.println( "Current Score=" + this.playerScore +
						" Number of missile in ship=" + ((Ship) obj).getMissileCount() + 
						" Current elapased time=" + this.timer + 
						" Number of lives=" + ((PlayerShip) obj).getLife()
				);
			}
		}

	}
	
	public void printMap() {
		System.out.println("\nMap :");
		/*
		for(int i = 0 ; i< gameObjects.size() ; i++) {
			System.out.println(gameObjects.get(i).toString());
		}
		*/
		IIterator iter = gameObjects.getIterator();
		while(iter.hasNext()) {
			GameObject obj = iter.getNext();
			System.out.println(obj.toString());
		}
		System.out.println("---------------------------");
	}
	
	public void turnMissileLauncherLeft() {
		/*
		for(int i = 0 ; i < gameObjects.size(); i++) {
			if(gameObjects.get(i) instanceof PlayerShip) {
				PlayerShip ps = (PlayerShip) gameObjects.get(i);
				ps.getMl().turnLeft();
				System.out.println("PlayerShip rotated left by 1 degree");
				System.out.println(ps.toString());
				this.setChanged();
				this.notifyObservers(new GameWorldProxy(this));
			}
		}
		*/
		
		IIterator iter = gameObjects.getIterator();
		while(iter.hasNext()) {
			GameObject obj = iter.getNext();
			if(obj instanceof PlayerShip) {
				PlayerShip ps = (PlayerShip) obj;
				ps.getMl().turnLeft();
				System.out.println("MissileLauncher rotated left by 1 degree");
				System.out.println(ps.toString());
				this.setChanged();
				this.notifyObservers(new GameWorldProxy(this));
			}
		}
	}
	
	public void turnMissileLauncherRight() {
		/*
		for(int i = 0 ; i < gameObjects.size(); i++) {
			if(gameObjects.get(i) instanceof PlayerShip) {
				PlayerShip ps = (PlayerShip) gameObjects.get(i);
				ps.getMl().turnRight();
				System.out.println("PlayerShip rotated right by 1 degree");
				System.out.println(ps.toString());
				this.setChanged();
				this.notifyObservers(new GameWorldProxy(this));
			}
		}
		*/
		
		IIterator iter = gameObjects.getIterator();
		while(iter.hasNext()) {
			GameObject obj = iter.getNext();
			if(obj instanceof PlayerShip) {
				PlayerShip ps = (PlayerShip) obj;
				ps.getMl().turnRight();
				System.out.println("MissileLauncher rotated right by 1 degree");
				System.out.println(ps.toString());
				this.setChanged();
				this.notifyObservers(new GameWorldProxy(this));
			}
		}
	}
	public void playerShipReload() {
		/*
		for(int i = 0 ; i < gameObjects.size(); i++) {
			if(gameObjects.get(i) instanceof PlayerShip) {
				PlayerShip ps = (PlayerShip) gameObjects.get(i);
				ps.reload();
				System.out.println("PlayerShip's missile has been reloaded");
				System.out.println(ps.toString());
				this.setChanged();
				this.notifyObservers(new GameWorldProxy(this));
				return;
			}
		}
		*/
		
		IIterator iter = gameObjects.getIterator();
		while(iter.hasNext()) {
			GameObject obj = iter.getNext();
			if(obj instanceof PlayerShip && this.stationCount > 0) {
				PlayerShip ps = (PlayerShip) obj;
				if(ps.getMissileCount() == ps.getMaxMissileCount()) {
					System.out.println("Playership already have max missile");
				}else {
					ps.reload();
					System.out.println("PlayerShip's missile has been reloaded");
					System.out.println(ps.toString());
					this.setChanged();
					this.notifyObservers(new GameWorldProxy(this));
				}
				return;
			}
		}
		System.out.println("No Playership to reload or Blinking Station is not yet created");
	}
	public void killAsteroid() { 
		if(asteroidCount >= 1 && psCount == 1) {
			/*
			for(int i = 0 ; i < gameObjects.size(); i++) {
				if(gameObjects.get(i) instanceof Missiles) {
					((Missiles) gameObjects.get(i)).getOwner().decrementMissile();
					gameObjects.remove(i);
					this.playerScore += 10;
					for(int j = 0 ; j < gameObjects.size(); j++) {
						if(gameObjects.get(j) instanceof Asteroids) {
							gameObjects.remove(j);
							asteroidCount--;
							System.out.println("Asteroid is destroyed by Playership Missile");
							this.setChanged();
							this.notifyObservers(new GameWorldProxy(this));
							return;
						}
					}
				}
			}
			*/
			IIterator iter = gameObjects.getIterator();
			while(iter.hasNext()) {
				int index = iter.getCurrIndex();
				GameObject obj = iter.getNext();
				if(obj instanceof Missiles) {
				//	((Missiles) obj).getOwner().decrementMissile();
					iter.remove(index);
					this.playerScore += 10;
					IIterator iter2 = gameObjects.getIterator();
					while(iter2.hasNext()) {
						int index2 = iter2.getCurrIndex();
						GameObject obj2 = iter2.getNext();
						if(obj2 instanceof Asteroids) {
							iter2.remove(index2);
							asteroidCount--;
							System.out.println("Asteroid is destroyed by Playership Missile");
							this.setChanged();
							this.notifyObservers(new GameWorldProxy(this));
							return;
						}
					}
				}
			}
			System.out.println("Error : Asteroid or Playership or PlayerMissile does not exists");
		}else {
			System.out.println("Error : Asteroid or Playership or PlayerMissile does not exists");
		}
	}
	public void killNonPlayerShipByMissile() {

	if(npsCount >= 1) {
		/*
        for(int i = 0; i < gameObjects.size(); i++) {
            if(gameObjects.get(i) instanceof Missiles) {
                if(((Missiles) gameObjects.get(i)).getOwner() instanceof PlayerShip) {
                	//Remove missile and decrease missile count for PS 
                	((Missiles) gameObjects.get(i)).getOwner().decrementMissile();
                	this.playerScore += 10;
                  	gameObjects.remove(i);
                    for(int j = 0 ; j < gameObjects.size(); j++) {
                    	if(gameObjects.get(j) instanceof NonPlayerShip) {
                    		gameObjects.remove(j);
                    		npsCount--;
                    		System.out.println("NonPlayerShip was destroyed by PlayerShip Missile");
                    		this.setChanged();
                    		this.notifyObservers(new GameWorldProxy(this));
                    		return;
                    	}
                    }
                 }
            }  
        }
        */
		IIterator iter = gameObjects.getIterator();
		while(iter.hasNext()) {
			int index = iter.getCurrIndex();
			GameObject obj = iter.getNext();
            if(obj instanceof Missiles) {
                if(((Missiles) obj).getOwner() instanceof PlayerShip) {
                	//Remove missile and decrease missile count for PS 
                	((Missiles) obj).getOwner().decrementMissile();
                	this.playerScore += 10;
                  	iter.remove(index);
            		IIterator iter2 = gameObjects.getIterator();
            		while(iter2.hasNext()) {
            			int index2 = iter2.getCurrIndex();
            			GameObject obj2 = iter2.getNext();
            			if(obj2 instanceof NonPlayerShip) {
                    		iter2.remove(index2);
                    		npsCount--;
                    		System.out.println("NonPlayerShip was destroyed by PlayerShip Missile");
                    		this.setChanged();
                    		this.notifyObservers(new GameWorldProxy(this));
                    		return;
                    	}
            		}
                 }
            } 
		}
    	System.out.println("Either PlayerShip,NonplayerShip,NonPlayerShip's missile does not exist");
    	return;
	}else {
    	System.out.println("Either PlayerShip,NonplayerShip,NonPlayerShip's missile does not exist");
	}
    	
}
    	

	
	public void killPlayerShipByMissile() {
		/*
        for(int i = 0; i < gameObjects.size(); i++) {
            if(gameObjects.get(i) instanceof Missiles &&  psCount == 1) {
                if(((Missiles) gameObjects.get(i)).getOwner() instanceof NonPlayerShip) {
                	//Remove missile and decrease missile count for NPS 
                	((Missiles) gameObjects.get(i)).getOwner().decrementMissile();
                  	gameObjects.remove(i);
                  	//Update Ps status 
                  	for(int j = 0 ; j < gameObjects.size(); j++) {
                  		if(gameObjects.get(j) instanceof PlayerShip) {
                  			if(((PlayerShip) gameObjects.get(j)).getLife() > 0) {
                            	((PlayerShip) gameObjects.get(j)).respawn();
                            	System.out.println("PlayerShip was destroyed by NPS's Missile");
                        		this.setChanged();
                        		this.notifyObservers(new GameWorldProxy(this));
                            	return;
                        	}else {
                        		gameObjects.remove(j);
                        		System.out.println("Game Over");
                        		return;
                        	}
                  		}
                  	}
                }
            }    
        }
        */
		IIterator iter = gameObjects.getIterator();
		while(iter.hasNext()) {
			int index = iter.getCurrIndex();
			GameObject obj = iter.getNext();
			if(obj instanceof Missiles &&  psCount == 1) {
                if(((Missiles) obj).getOwner() instanceof NonPlayerShip) {
                	//Remove missile and decrease missile count for NPS 
                	((Missiles) obj).getOwner().decrementMissile();
                  	iter.remove(index);
                  	//Update Ps status 
            		IIterator iter2 = gameObjects.getIterator();
            		while(iter2.hasNext()) {
            			int index2 = iter2.getCurrIndex();
            			GameObject obj2 = iter2.getNext();
            			if(obj2 instanceof PlayerShip) {
                  			if(this.life > 0) {
                            	//((PlayerShip) obj2).respawn();
                  				iter.remove(index2);
                  				psCount = 0;
                            	System.out.println("PlayerShip was destroyed by NPS's Missile");
                        		this.setChanged();
                        		this.notifyObservers(new GameWorldProxy(this));
                            	return;
                        	}else {
                        		iter2.remove(index2);
                        		System.out.println("Game Over");
                        		return;
                        	}
                  		}
            		}
                }
            }    
		}
    	System.out.println("Either PlayerShip,NonplayerShip,NonPlayerShip's missile does not exist");	
	}
	public void killPlayerShipByAsteroid() {
		
		if(asteroidCount >= 1 &&  psCount == 1) {
			/*
			for(int i = 0; i < gameObjects.size(); i++) {
	            if(gameObjects.get(i) instanceof PlayerShip) {
	            	if(((PlayerShip) gameObjects.get(i)).getLife() > 0) {
	                	((PlayerShip) gameObjects.get(i)).respawn();
	                	System.out.println("Playership is destroyed by Asteroid. Life decreased by 1");
	            		this.setChanged();
	            		this.notifyObservers(new GameWorldProxy(this));
	            	}else {
	            		gameObjects.set(i,null);
	            		gameObjects.remove(i);
	            		psCount = 0;
	            		this.setChanged();
	            		this.notifyObservers(new GameWorldProxy(this));
	            		System.out.println("Game Over");
	            	}
	                break;
	            }
	        }
	        */
			IIterator iter = gameObjects.getIterator();
			while(iter.hasNext()) {
				int index = iter.getCurrIndex();
				GameObject obj = iter.getNext();
				 if(obj instanceof PlayerShip) {
		            	if(this.life > 0) {
		                	//((PlayerShip) obj).respawn();
		            		this.life--;
		            		iter.remove(index);
		            		psCount = 0;
		                	System.out.println("Playership is destroyed by Asteroid. Life decreased by 1");
		            		this.setChanged();
		            		this.notifyObservers(new GameWorldProxy(this));
		            	}else {
		            		iter.setEmpty(index);
		            		iter.remove(index);
		            		psCount = 0;
		            		this.setChanged();
		            		this.notifyObservers(new GameWorldProxy(this));
		            		System.out.println("Game Over");
		            	}
		                break;
		            }
			}
			/*
			for(int i = 0; i < gameObjects.size(); i++) {
	            if(gameObjects.get(i) instanceof Asteroids) {
	                gameObjects.remove(i);
	                asteroidCount--;
	        		this.setChanged();
	        		this.notifyObservers(new GameWorldProxy(this));
	                break;
	            }
	        }
	        */
			IIterator iter2 = gameObjects.getIterator();
			while(iter2.hasNext()) {
				int index2 = iter2.getCurrIndex();
				GameObject obj2 = iter2.getNext();
	            if(obj2 instanceof Asteroids) {
	                iter2.remove(index2);
	                asteroidCount--;
	        		this.setChanged();
	        		this.notifyObservers(new GameWorldProxy(this));
	                break;
	            }
			}
		}else {
			System.out.println("Either PlayerShip or Asteroid does not exist");
		}
	}
	public void killPlayerShipByNPS() {

		if(psCount == 1 && npsCount >= 1) {
			/*
			for(int i = 0; i < gameObjects.size(); i++) {
	            if(gameObjects.get(i) instanceof PlayerShip) {
	            	if(((PlayerShip) gameObjects.get(i)).getLife() > 0) {
	                	((PlayerShip) gameObjects.get(i)).respawn();
	                	System.out.println("Playership is destroyed by NonPlayerShip. Life decreased by 1");
	            		this.setChanged();
	            		this.notifyObservers(new GameWorldProxy(this));
	            	}else {
	            		gameObjects.set(i,null);
	            		gameObjects.remove(i);
	            		psCount = 0;
	            		this.setChanged();
	            		this.notifyObservers(new GameWorldProxy(this));
	            		System.out.println("Game Over");
	            	}
	                break;
	            }
	        }
	        */
			IIterator iter = gameObjects.getIterator();
			while(iter.hasNext()) {
				int index = iter.getCurrIndex();
				GameObject obj = iter.getNext();
				if(obj instanceof PlayerShip) {
	            	if(this.life > 0) {
	            		this.life--;
	                	//((PlayerShip) obj).respawn();
	            		iter.remove(index);
	            		psCount = 0;
	                	System.out.println("Playership is destroyed by NonPlayerShip. Life decreased by 1");
	            		this.setChanged();
	            		this.notifyObservers(new GameWorldProxy(this));
	            	}else {
	            		iter.setEmpty(index);
	            		iter.remove(index);
	            		psCount = 0;
	            		this.setChanged();
	            		this.notifyObservers(new GameWorldProxy(this));
	            		System.out.println("Game Over");
	            	}
	                break;
	            }
			}

			/*
			for(int i = 0; i < gameObjects.size(); i++) {		
	            if(gameObjects.get(i) instanceof NonPlayerShip) {
	                gameObjects.remove(i);
	                npsCount--;
	        		this.setChanged();
	        		this.notifyObservers(new GameWorldProxy(this));
	                break;
	            }
	        }	
	        */
			IIterator iter2 = gameObjects.getIterator();
			while(iter2.hasNext()) {
				int index2 = iter2.getCurrIndex();
				GameObject obj2 = iter2.getNext();
	            if(obj2 instanceof NonPlayerShip) {
	                iter2.remove(index2);
	                npsCount--;
	        		this.setChanged();
	        		this.notifyObservers(new GameWorldProxy(this));
	                break;
	            }
			}
		}else {
			System.out.println("Either PlayerShip or NonPlayerShip does not exist");
		}
	}
	public void asteroidCollision() {

		if(asteroidCount >= 2) {
			/*
			for(int i = 0 ; i < gameObjects.size(); i++) {
				if(gameObjects.get(i) instanceof Asteroids) {
					gameObjects.remove(i);
					asteroidCount--;
					this.setChanged();
					this.notifyObservers(new GameWorldProxy(this));
					break;
				}
			}
			for(int i = 0 ; i < gameObjects.size(); i++) {
				if(gameObjects.get(i) instanceof Asteroids) {
					gameObjects.remove(i);
					asteroidCount--;
					this.setChanged();
					this.notifyObservers(new GameWorldProxy(this));
					break;
				}
			}
			*/
			IIterator iter = gameObjects.getIterator();
			while(iter.hasNext()) {
				int index = iter.getCurrIndex();
				GameObject obj = iter.getNext();
				if(obj instanceof Asteroids) {
					iter.remove(index);
					asteroidCount--;
					this.setChanged();
					this.notifyObservers(new GameWorldProxy(this));
					break;
				}
			}
			IIterator iter2 = gameObjects.getIterator();
			while(iter2.hasNext()) {
				int index2 = iter2.getCurrIndex();
				GameObject obj2 = iter2.getNext();
				if(obj2 instanceof Asteroids) {
					iter2.remove(index2);
					asteroidCount--;
					this.setChanged();
					this.notifyObservers(new GameWorldProxy(this));
					break;
				}
			}
			System.out.println("2 Asteroids Collided and both were destroyed.");
		}else {
			System.out.println("Error : NO 2 Asteroid to kill each other");
		}	
	}
	public void killNonPlayerShipByAsteroid() {
		
		if(asteroidCount >=1 && npsCount >= 1) {
			/*
			for(int i = 0 ; i < gameObjects.size(); i++) {
				if(gameObjects.get(i) instanceof Asteroids) {
					gameObjects.remove(i);
					asteroidCount--;
					this.setChanged();
					this.notifyObservers(new GameWorldProxy(this));
					break;
				}
			}
			for(int i = 0 ; i < gameObjects.size(); i++) {
				if(gameObjects.get(i) instanceof NonPlayerShip) {
					gameObjects.remove(i);
					npsCount--;
					this.setChanged();
					this.notifyObservers(new GameWorldProxy(this));
					break;
				}
			}
			*/
			IIterator iter = gameObjects.getIterator();
			while(iter.hasNext()) {
				int index = iter.getCurrIndex();
				GameObject obj = iter.getNext();
				if(obj instanceof Asteroids) {
					iter.remove(index);
					asteroidCount--;
					this.setChanged();
					this.notifyObservers(new GameWorldProxy(this));
					break;
				}
			}
			IIterator iter2 = gameObjects.getIterator();
			while(iter2.hasNext()) {
				int index2 = iter2.getCurrIndex();
				GameObject obj2 = iter2.getNext();
				if(obj2 instanceof NonPlayerShip) {
					iter2.remove(index2);
					npsCount--;
					this.setChanged();
					this.notifyObservers(new GameWorldProxy(this));
					break;
				}
			}
			System.out.println("NonPlayer Ship was destroyed by an Asteroid");
		}else {
			System.out.println("Error : NO asteroid or NonPlayerShip to kill each other");
		}
	}
	public void increasePlayerShipSpeed() {
		/*
		for(int i = 0 ; i < gameObjects.size(); i++) {
			if(gameObjects.get(i) instanceof PlayerShip) {
				((PlayerShip) gameObjects.get(i)).increaseSpeed();
				System.out.println("PlayerShip speed increased to " + ((MoveableObject) gameObjects.get(i)).getSpeed());
				this.setChanged();
				this.notifyObservers(new GameWorldProxy(this));
				return;
			}
		}
		*/
		IIterator iter = gameObjects.getIterator();
		while(iter.hasNext()) {
			int index = iter.getCurrIndex();
			GameObject obj = iter.getNext();
			if(obj instanceof PlayerShip) {
				((PlayerShip) obj).increaseSpeed();
				System.out.println("PlayerShip speed increased to " + ((MoveableObject) obj).getSpeed());
				this.setChanged();
				this.notifyObservers(new GameWorldProxy(this));
				return;
			}
		}
		System.out.println("There exists no playership to increase speed");
	}
	public void decreasePlayerShipSpeed() {
		/*
		for(int i = 0 ; i < gameObjects.size(); i++) {
			if(gameObjects.get(i) instanceof PlayerShip) {
				((PlayerShip) gameObjects.get(i)).decreaseSpeed();
				System.out.println("PlayerShip speed decreased to " + ((MoveableObject) gameObjects.get(i)).getSpeed());
				this.setChanged();
				this.notifyObservers(new GameWorldProxy(this));
				return;
			}
		}
		*/
		IIterator iter = gameObjects.getIterator();
		while(iter.hasNext()) {
			int index = iter.getCurrIndex();
			GameObject obj = iter.getNext();
			if(obj instanceof PlayerShip) {
				((PlayerShip) obj).decreaseSpeed();
				System.out.println("PlayerShip speed decreased to " + ((MoveableObject) obj).getSpeed());
				this.setChanged();
				this.notifyObservers(new GameWorldProxy(this));
				return;
			}
		}
		System.out.println("There exists no playership to decrease speed");	
	}
	public void turnPSLeft() {
		/*
		for(int i = 0 ; i < gameObjects.size(); i++) {
			if(gameObjects.get(i) instanceof PlayerShip) {
				((PlayerShip) gameObjects.get(i)).turnLeft();
				System.out.println("Playership turned left by 1");
				this.setChanged();
				this.notifyObservers(new GameWorldProxy(this));
				return;
			}
		}
		*/
		IIterator iter = gameObjects.getIterator();
		while(iter.hasNext()) {
			int index = iter.getCurrIndex();
			GameObject obj = iter.getNext();
			if(obj instanceof PlayerShip) {
				((PlayerShip) obj).turnLeft();
				System.out.println("Playership turned left by 1");
				this.setChanged();
				this.notifyObservers(new GameWorldProxy(this));
				return;
			}
		}
		System.out.println("There exists no playership to turn left");	
	}
	public void turnPSRight() {
		/*	
		for(int i = 0 ; i < gameObjects.size(); i++) {
			if(gameObjects.get(i) instanceof PlayerShip) {
				((PlayerShip) gameObjects.get(i)).turnRight();
				System.out.println("Playership turned right by 1");
				this.setChanged();
				this.notifyObservers(new GameWorldProxy(this));
				return;
			}
		}
		*/
		IIterator iter = gameObjects.getIterator();
		while(iter.hasNext()) {
			int index = iter.getCurrIndex();
			GameObject obj = iter.getNext();
			if(obj instanceof PlayerShip) {
				((PlayerShip) obj).turnRight();
				System.out.println("Playership turned right by 1");
				this.setChanged();
				this.notifyObservers(new GameWorldProxy(this));
				return;
			}
		}
		System.out.println("There exists no playership to turn right");		
	}
	public void jumpToHyperSpace() {
		/*
		for(int i = 0 ; i < gameObjects.size(); i++) {
			if(gameObjects.get(i) instanceof PlayerShip) {
				((PlayerShip) gameObjects.get(i)).resetPosition();;
				this.setChanged();
				this.notifyObservers(new GameWorldProxy(this));
				return;
			}
		}
		*/
		IIterator iter = gameObjects.getIterator();
		while(iter.hasNext()) {
			int index = iter.getCurrIndex();
			GameObject obj = iter.getNext();
			if(obj instanceof PlayerShip) {
				((PlayerShip) obj).resetPosition();;
				this.setChanged();
				this.notifyObservers(new GameWorldProxy(this));
				System.out.println("PlayerShip jumped through hyperspace");
				return;
			}
		}
		System.out.println("There exists no playership to jump through hyperspace");		
	}
	@Override
	public void setSound(boolean b) {
		this.soundOn = b;
		this.setChanged();
		this.notifyObservers(new GameWorldProxy(this));
	}
	
	@Override
	public boolean getSound() {
		return this.soundOn;
	}
	
	@Override
	public int getLife() {
		return this.life;
	}
	
	
}

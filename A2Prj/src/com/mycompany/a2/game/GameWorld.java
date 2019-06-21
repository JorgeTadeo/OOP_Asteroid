package com.mycompany.a2.game;

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
	private boolean GameOver;
	
	/*
	 * Constructor
	 * 
	 * NOTE : Singleton GameWorld to enforce only instance of gameworld can ever be created.
	 */
	
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
	 * Initialize Game , called by Game class
	 */
	public void init() {
		gameObjects = new SpaceCollection();
		playerScore = 0;
		timer = 0;
		asteroidCount = 0;
		psCount = 0;
		npsCount = 0;
		stationCount = 0;
		soundOn = false;
		life = 3;
		GameOver = false;
	}
	
	
	/*******************************
	 * Observer helper functions : *
	 *******************************/
	
	/*
	 * Returns player score
	 */
	public int getPlayerScore() {
		return playerScore;	
	}
	
	/*
	 * Returns game timer
	 */
	public int getGameTime() {
		return timer;
	}
	
	/*
	 * Returns remain missile count of playership ,  0 if playership does not exist. 
	 */
	public int getPSMissileCount() {
		IIterator iter = gameObjects.getIterator();
		while(iter.hasNext()) {
			GameObject obj = iter.getNext();
			if(obj instanceof PlayerShip) {
				return ((PlayerShip)obj).getMissileCount();
			}
		}
		System.out.println("There exists no playership , returned 0");
		return 0;
	}
	
	/*
	 * Returns an iterator to iterate GameObjects collection
	 */
	public IIterator getGameObjectIterator() {
		return gameObjects.getIterator();
	}
	

	
	
	/********************
	 * IN GAME METHODS  *
	 ********************/
	
	
	/*
	 * Game tick : 
	 * 
	 * 	PostCondition : game timer increments by 1 tick. All gameObject should reflect changes.
	 */
	public void tick() {
		
		IIterator iter = gameObjects.getIterator();
	
		while(iter.hasNext()) {
			
			int ptr = iter.getCurrIndex();
			GameObject obj = iter.getNext();
			
			//If is a missile with 1 fuel left
			if(obj instanceof Missiles && ((Missiles) obj).getFuel() == 1 ) {
				iter.remove(ptr);
				System.out.println("Missile out of fuel and is removed");
			//If is a missile with more than 1 fuel left
			}else if(obj instanceof Missiles && ((Missiles) obj).getFuel() != 1) {
				((Missiles) obj).decrementFuel();
			//If is a moveable object
			}else if(!(obj instanceof FixedObject)) {
				((MoveableObject) obj).move();
			//Not a moveable object , no change.
			}else {
				continue;
			}
			this.setChanged();
			this.notifyObservers(new GameWorldProxy(this));
		}//END WHILE 
		
		timer++;
	}
	
	
	/*
	 * Adds asteroid to game 
	 * 	PreCondition : life is not 0 / GameOver is not over
	 * 	PostCondition : Asteroid is added to gameObjects and all observers are notified
	 */
	public void addAsteroid() {
		
		if(GameOver) {
			System.out.println("Unable to add Asteroid , GameOver already...");
			return;
		}
		
		gameObjects.add(new Asteroids());
		this.asteroidCount++;
		this.setChanged();
		this.notifyObservers(new GameWorldProxy(this));	
	}
	
	
	
	/*
	 * Adds PlayerShip to game
	 * 	PreCondition : life is not 0 / Game is not over
	 * 	PostCondition : Playership added to gameObjects and notify observers
	 */
	public void addPlayerShip() {
		
		if(GameOver) {
			System.out.println("Unable to add PlayerShip , GameOver already...");
			return;
		}
		
		if(PlayerShip.isDead()) {
			gameObjects.add(PlayerShip.getInstance(psCount));
			this.psCount = 1;
			this.setChanged();
			this.notifyObservers(new GameWorldProxy(this));
			return;
		}else {
			System.out.println("PlayerShip Already Exists...");
		}
	}
	
	
	/*
	 * Adds Non-PlayerShip to game
	 * 	PreCondition : life is not 0 / Game is not over
	 * 	PostCondition : Non-Playership added to gameObjects and notify observers
	 */
	public void addNonPlayerShip() {
		
		if(GameOver) {
			System.out.println("Unable to add Non-PlayerShip , GameOver already...");
			return;
		}
		
		gameObjects.add(new NonPlayerShip());
		this.npsCount++;
		this.setChanged();
		this.notifyObservers(new GameWorldProxy(this));
	}
	
	
	/*
	 * Adds Blinking Space Station to game
	 * 	PreCondition : life is not 0 / Game is not over
	 * 	PostCondition : Blinking Space Station added to gameObjects and notify observers
	 */
	public void addSpaceStation() {
		
		if(GameOver) {
			System.out.println("Unable to add Blinking Space Station , GameOver already...");
			return;
		}	
		gameObjects.add(new SpaceStation());
		this.stationCount++;
		this.setChanged();
		this.notifyObservers(new GameWorldProxy(this));
	}
	
	
	/*
	 * Adds Player Missile to game
	 * 	PreCondition : life is not 0 / Game is not over && PlayerShip Exists && PlayerShip ML Exists && enough ammo
	 * 	PostCondition : PlayerShip Missile added to gameObjects and notify observers
	 */
	public void addPlayerMissile() {	
		if(GameOver) {
			System.out.println("Unable to fire playership missile , GameOver already...");
			return;
		}	
		if(psCount != 1) {
			System.out.println("Unable to fire missile , Playership does not exist.");
			return;
		}
		IIterator iter = gameObjects.getIterator();
		while(iter.hasNext()) {
			
			GameObject obj = iter.getNext();
			//If obj is a Playership with MissileLauncher and ammo  then fire
			if(obj instanceof PlayerShip ) {
				if(((PlayerShip) obj).getMl() != null){
					if(((PlayerShip) obj).getMissileCount() > 0) {
						gameObjects.add(new Missiles((PlayerShip) obj));
						System.out.println("Player ship fired missile");
						System.out.println(((PlayerShip) obj).getMissileCount() + " missiles left");
						this.setChanged();
						this.notifyObservers(new GameWorldProxy(this));
					}else {
						System.out.println("Player ship ran out of missile");
					}
				}else {
					System.out.println("Unable to fire missile , Playership MissileLauncher does not exist.");
				}
				return;
			}
		}
		//This line should never run 
		System.out.println("psCount == 1 , but No Playership is found in GameObjects");
		return;
	}
	
	
	/*
	 * Adds Non-PlayerShip Missile to game
	 * 	PreCondition : life is not 0 / Game is not over && NPS Exists && NPS's ML Exists && enough ammo
	 * 	PostCondition : NPS Missile added to gameObjects and notify observers
	 */
	public void addNonPlayerMissile() {
		if(GameOver) {
			System.out.println("Unable to fire non-playership missile , GameOver already...");
			return;
		}	
		if(npsCount < 1) {
			System.out.println("Unable to fire missile , Non-Playership does not exist.");
			return;
		}
		IIterator iter = gameObjects.getIterator();
		while(iter.hasNext()) {
			
			GameObject obj = iter.getNext();
			
			if(obj instanceof NonPlayerShip ) {
				if(((NonPlayerShip)obj).getMl() != null) {
					if(((NonPlayerShip) obj).getMissileCount() > 0) {
						gameObjects.add(new Missiles((NonPlayerShip) obj));
						System.out.println("Non-Player ship fired missile");
						System.out.println(((NonPlayerShip) obj).getMissileCount() + " missiles left");
						this.setChanged();
						this.notifyObservers(new GameWorldProxy(this));
					}else {
						System.out.println("Non-Player ship ran out of missile");
					}
				}else {
					System.out.println("Unable to fire missile , Non-Playership MissileLauncher does not exist.");
				}
				return;
			}
		}
		//This line should never run 
		System.out.println("npsCount > 1 , but NPS is not found in GameObjects");
		return;
	}

	
	//TODO @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
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

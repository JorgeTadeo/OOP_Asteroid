package com.mycompany.a2.command;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a2.game.IGameWorld;

public class CommandKillAsteroidByAsteroid extends Command {

	
	private IGameWorld gw;
	
	public CommandKillAsteroidByAsteroid(IGameWorld gw) {
		super("Asteroid*\n(Asteroid)");
		this.gw = gw;
	}
	
	public void actionPerformed(ActionEvent e) {
		gw.asteroidCollision();
	}
	
	
}

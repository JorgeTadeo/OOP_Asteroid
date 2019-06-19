package com.mycompany.a2.command;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a2.game.IGameWorld;

public class CommandAddAsteroid extends Command {

	
	private IGameWorld gw;
	
	public CommandAddAsteroid(IGameWorld gw) {
		super("+ Asteroid");
		this.gw = gw;
	}
	
	public void actionPerformed(ActionEvent e) {
		gw.addAsteroid();
	}
	
	
}

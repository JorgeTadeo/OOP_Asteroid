package com.mycompany.a2.command;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a2.game.IGameWorld;

public class CommandAddPlayerShip extends Command {

	
	private IGameWorld gw;
	
	public CommandAddPlayerShip(IGameWorld gw) {
		super("+ PS (1)");
		this.gw = gw;
	}
	
	public void actionPerformed(ActionEvent e) {
		gw.addPlayerShip();
	}
	
	
}

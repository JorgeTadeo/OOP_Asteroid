package com.mycompany.a2.command;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a2.game.IGameWorld;

public class CommandNew extends Command {

	
	private IGameWorld gw;

	
	public CommandNew(IGameWorld gw) {
		super("New");
		this.gw = gw;
	}
	

	public void actionPerformed(ActionEvent e) {
		//TODO
	}
	
	
}

package com.mycompany.a2.command;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a2.game.IGameWorld;

public class CommandUndo extends Command {

	
	private IGameWorld gw;

	
	public CommandUndo(IGameWorld gw) {
		super("Undo");
		this.gw = gw;
	}
	

	public void actionPerformed(ActionEvent e) {
		//TODO
	}
	
	
}

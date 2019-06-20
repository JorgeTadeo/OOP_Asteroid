package com.mycompany.a2.command;

import com.codename1.ui.CheckBox;
import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a2.game.IGameWorld;

public class CommandQuit extends Command {

	
	private IGameWorld gw;

	
	public CommandQuit(IGameWorld gw) {
		super("Quit*");
		this.gw = gw;
	}
	

	public void actionPerformed(ActionEvent e) {
		System.exit(0);
	}
	
	
}

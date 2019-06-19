package com.mycompany.a2.command;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a2.game.IGameWorld;

public class CommandAddSpaceStation extends Command {

	
	private IGameWorld gw;
	
	public CommandAddSpaceStation(IGameWorld gw) {
		super("+ Space Station");
		this.gw = gw;
	}
	
	public void actionPerformed(ActionEvent e) {
		gw.addSpaceStation();
	}
	
	
}

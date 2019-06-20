package com.mycompany.a2.command;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a2.game.IGameWorld;

public class CommandDecreasePlayerShipSpeed extends Command {

	
	private IGameWorld gw;
	
	public CommandDecreasePlayerShipSpeed(IGameWorld gw) {
		super("PS Speed (-)*");
		this.gw = gw;
	}
	
	public void actionPerformed(ActionEvent e) {
		gw.decreasePlayerShipSpeed();
	}
	
	
}

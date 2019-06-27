package com.mycompany.a2.gameobjects.fixedobject;

import java.util.Random;

import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;

public class SpaceStation extends FixedObject {

	
	private int blinkRate;
	private boolean isOn;
	private int myId;
	public SpaceStation() {
		super(ColorUtil.YELLOW);
		this.blinkRate = new Random().nextInt(7);
		myId = FixedObject.id++;
		System.out.println("Added Blinking SpaceStation");
		System.out.println(this.toString()); //DEBUG LINE
	}
	
	public boolean getBlinkStatus() {
		return isOn;
	}
	public void setBlinkStatus(boolean x) {
		isOn = x;
	}
	public int getBlinkRate() {
		return this.blinkRate;
	}
	public void setBlinkRate(int x) {
		this.blinkRate = x;
	}
	public int getId() {
		return this.myId;
	}
	
	@Override 
	public String toString() {
		return (
			"Station: loc=" + Math.round(this.getX()*10.0)/10.0 + "," + Math.round(this.getY()*10.0)/10.0 + 
			" color=" + this.getColorToString() +
			" rate=" + this.getBlinkRate()
		);				
	}

	@Override
	public void draw(Graphics g, Point offsetRelToParent) {
		// TODO Auto-generated method stub
		System.out.println("TODO Draw SpaceStation");
	}

}

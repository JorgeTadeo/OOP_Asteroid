package com.mycompany.a2.gameobjects.moveableobject;

import java.util.Random;

import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.mycompany.a2.gameobjects.moveableobject.IMoveable;
import com.mycompany.a2.gameobjects.moveableobject.MoveableObject;

public class Asteroids extends MoveableObject implements IMoveable {

	
	int size;
	
	public Asteroids() {
		super(ColorUtil.BLACK);
		final int MIN_SIZE = 6;
		final int MAX_SIZE = 30;
		this.size =  new Random().nextInt(MAX_SIZE - MIN_SIZE + 1) + MIN_SIZE;
		System.out.println("Asteroid is created");
	//	System.out.println(this.toString()); //DEBUG LINE
	}
	
	public int getSize() {
		return this.size;
	}
	
	@Override 
	public String toString() {
		return (
			"Asteroid: loc=" + Math.round(this.getX()*10.0)/10.0 + "," + Math.round(this.getY()*10.0)/10.0 + 
			" color=" + this.getColorToString() +
			" speed=" + this.getSpeed() +
			" dir=" + this.getDirection() +
			" size=" + this.getSize() 
		);				
	}

	@Override
	public void draw(Graphics g, Point offsetRelToParent) {
		// TODO Auto-generated method stub
		System.out.println("TODO Draw Asteroid");
	}


	
}
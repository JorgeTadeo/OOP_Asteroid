package com.mycompany.a2.gameobjects.moveableobject;

import com.codename1.charts.models.Point;
import com.codename1.ui.Graphics;

public class NonSteerableMissileLauncher extends MoveableObject implements IMoveable{

	private NonPlayerShip owner;
	
	public NonSteerableMissileLauncher(NonPlayerShip owner , int objectColor, int speed, int direction, double x, double y) {
		super(objectColor, speed, direction, x, y);
		this.owner = owner;
	}

	@Override
	public void move() {
		this.setLocation(owner.getX(), owner.getY());	
	}

	@Override
	public void draw(Graphics g, Point offsetRelToParent) {
		// TODO Auto-generated method stub
		System.out.println("TODO draw NPS ML");
	}



	

}

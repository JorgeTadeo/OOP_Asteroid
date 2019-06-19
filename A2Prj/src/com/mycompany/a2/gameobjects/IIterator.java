package com.mycompany.a2.gameobjects;

public interface IIterator {
	public boolean hasNext();
	public GameObject getNext();
	public void remove(int index);
	public GameObject peekNext();
	public int getCurrIndex();
	public void setEmpty(int index);
}

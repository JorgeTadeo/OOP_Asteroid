package com.mycompany.a2.gameobjects;

public interface ICollection {
	void add(GameObject newObject);
	IIterator getIterator();
}

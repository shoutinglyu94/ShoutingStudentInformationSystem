/**
 * 
 */
package com.csye6225.fall2019.model;

/**
 * @author shoutinglyu
 *
 */
public abstract class Person {
	public String name;
	public String id;
	public String imageUrl;

	public Person(String id, String name) {
		this.name = name;
		this.id = id;
		this.imageUrl = null;
	}
	
}

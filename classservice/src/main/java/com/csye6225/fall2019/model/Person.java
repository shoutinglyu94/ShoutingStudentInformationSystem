/**
 * 
 */
package com.csye6225.fall2019.model;

/**
 * @author shoutinglyu
 *
 */
public abstract class Person {
	private String name;
	private String id;
	private String imageUrl;

	public Person() {
		this.name = "";
		this.id = "";
		this.imageUrl = "";
	}
	
	public Person(String id, String name) {
		this.name = name;
		this.id = id;
		this.imageUrl = "";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	
	
}

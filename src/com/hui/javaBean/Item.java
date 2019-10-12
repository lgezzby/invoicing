package com.hui.javaBean;

public class Item {

	private String id ;

	private String name ;

	@Override
	public String toString() {
		return  getName();
	}

	public Item(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public Item() {
		// TODO Auto-generated constructor stub
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}

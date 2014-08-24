package com.example.search.util;

public class MyUrl {
	private String path;
	private int type;
	
	public String getPath() {
		return path;
	}

	public int getType() {
		return type;
	}

	public MyUrl(String path, int type) {
		this.path = path;
		this.type = type;
	}
}

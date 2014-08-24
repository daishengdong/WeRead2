package com.example.search.util;

public class MyUrl {
	private String path;
	private Type.UrlType type;
	
	public String getPath() {
		return path;
	}

	public Type.UrlType getType() {
		return type;
	}

	public MyUrl(String path, Type.UrlType type) {
		this.path = path;
		this.type = type;
	}
}

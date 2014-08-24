package com.example.search.util;

import java.util.ArrayList;
import java.util.HashMap;

public class RetMessage {
	public static enum Type {Class, Individual};
	public Type type;
	public ArrayList<String> subClasses;
	public HashMap<String, String> properties;

	public ArrayList<String> toArrayList() {
		if (type == Type.Class) {
			return subClasses;
		} else {
			ArrayList<String> retList = new ArrayList<String>();
			for (String key : properties.keySet()) {
				retList.add(key + ": " + properties.get(key));
			}
			return retList;
		}
	}
}

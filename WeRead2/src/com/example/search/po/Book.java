package com.example.search.po;

import com.example.search.util.Type;

public class Book {
	private String name;
	private String content;
	private String url;
	private Type.UrlType urlType;

    public Book() {  
        super();  
    }  

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Type.UrlType getUrlType() {
		return urlType;
	}

	public void setUrlType(Type.UrlType urlType) {
		this.urlType = urlType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}

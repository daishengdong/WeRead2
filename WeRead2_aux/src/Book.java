

public class Book {
	private String name;
	private String content;
	private String url;

    public Book() {
        super();  
    }  
    public Book(String name, String content, String url) {  
        this.name = name;
        this.content = content;
        this.url = url;
    }  

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
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

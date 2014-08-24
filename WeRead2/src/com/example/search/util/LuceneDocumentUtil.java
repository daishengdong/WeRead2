package com.example.search.util;

import org.apache.lucene.document.Document;

import com.example.search.po.Book;

public class LuceneDocumentUtil {

	/**
	 * 
	 * @function:将document对象转成book对象
	 * @author:Jerry
	 * @date:2013-9-24
	 * @param doc
	 * @return Book
	 */
	public static Book DocumentToBook(Document doc) {
		Book book = new Book();
		// book.setId(Integer.parseInt(doc.get("id")));
		book.setName(doc.get("name"));
		book.setContent(doc.get("content"));
		book.setUrl(doc.get("url"));
		book.setUrlType(Type.UrlType.html_file_path);
		return book;
	}

}

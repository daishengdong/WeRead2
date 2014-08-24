package com.example.search.action;

import com.example.search.service.SearchService;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ListView;

public class SearchAction implements OnClickListener {
	private String cur_book_dir_path;
	private Context context = null;
	private EditText value_et = null;
	private ListView bookListView = null;
	SearchService searchService = null;

	public SearchAction(Context context, EditText value_et, ListView bookListView, String cur_book_dir_path) {
		super();
		this.context = context;
		this.value_et = value_et;
		this.bookListView = bookListView;
		this.cur_book_dir_path = cur_book_dir_path;

		// 新建业务处理类
		this.searchService = new SearchService(this.context, this.bookListView, this.cur_book_dir_path);
	}

	@Override
	public void onClick(View v) {
		// 获取查询内容
		String value = this.value_et.getText().toString().trim();

		// 调用业务层方法进行查询
		this.searchService.queryBook(value, 1);
	}

}

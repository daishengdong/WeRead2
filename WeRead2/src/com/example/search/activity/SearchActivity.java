package com.example.search.activity;

import com.example.search.action.SearchAction;
import com.example.weread.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

public class SearchActivity extends Activity {
	private String cur_book_dir_path;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.law_layout);

		Intent intent = getIntent();
		this.cur_book_dir_path = intent.getStringExtra("cur_book_dir_path");
		this.initView();
	}

	/**
	 * 
	 * @function:初始化Activity界面
	 * @author:Jerry
	 * @date:2013-9-24
	 */
	private void initView() {
		Spinner indexSearch_sp = (Spinner) super
				.findViewById(R.id.index_search);
		indexSearch_sp.setPrompt("搜索模式");
		ArrayAdapter<CharSequence> item = ArrayAdapter.createFromResource(this,
				R.array.indexOfSearch, android.R.layout.simple_spinner_item);
		item.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		indexSearch_sp.setAdapter(item);


		// 获取各个控件
		EditText value_et = (EditText) super.findViewById(R.id.value);
		Button search_bt = (Button) super.findViewById(R.id.query_bt);
		ListView bookListView = (ListView) super.findViewById(R.id.content_lv);

		// 新建事件处理类，该类实现View.OnClickListener接口
		SearchAction searchAction = new SearchAction(
				this.getApplicationContext(), indexSearch_sp, value_et,
				bookListView, this.cur_book_dir_path);

		// 给搜索按钮注册点击事件
		search_bt.setOnClickListener(searchAction);
	}
}
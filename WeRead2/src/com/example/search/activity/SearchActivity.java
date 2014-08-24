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
	 * @function:��ʼ��Activity����
	 * @author:Jerry
	 * @date:2013-9-24
	 */
	private void initView() {
		Spinner indexSearch_sp = (Spinner) super
				.findViewById(R.id.index_search);
		indexSearch_sp.setPrompt("����ģʽ");
		ArrayAdapter<CharSequence> item = ArrayAdapter.createFromResource(this,
				R.array.indexOfSearch, android.R.layout.simple_spinner_item);
		item.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		indexSearch_sp.setAdapter(item);


		// ��ȡ�����ؼ�
		EditText value_et = (EditText) super.findViewById(R.id.value);
		Button search_bt = (Button) super.findViewById(R.id.query_bt);
		ListView bookListView = (ListView) super.findViewById(R.id.content_lv);

		// �½��¼������࣬����ʵ��View.OnClickListener�ӿ�
		SearchAction searchAction = new SearchAction(
				this.getApplicationContext(), indexSearch_sp, value_et,
				bookListView, this.cur_book_dir_path);

		// ��������ťע�����¼�
		search_bt.setOnClickListener(searchAction);
	}
}
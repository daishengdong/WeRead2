package com.example.search.activity;

import java.util.ArrayList;
import java.util.HashMap;

import com.example.weread.R;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class ResultActivity extends ListActivity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.result_layout);
		ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> map1 = new HashMap<String, String>();
		HashMap<String, String> map2 = new HashMap<String, String>();
		map1.put("name", "zhangsan");
		map1.put("content", "1 content");
		map2.put("name", "lisi");
		map2.put("content", "2 content");
		list.add(map1);
		list.add(map2);
		SimpleAdapter listAdapter = new SimpleAdapter(this, list, R.layout.books, 
				new String[] {"name", "content"},
				new int[] {R.id.name, R.id.content});
		setListAdapter(listAdapter);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		System.out.println("id           " + id);
		System.out.println("position           " + id);
	}


}

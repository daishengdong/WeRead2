package com.example.search.adapter;

import java.util.ArrayList;
import java.util.HashMap;

import com.example.weread.R;

import android.content.Context;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SearchResultListViewAdapter extends BaseAdapter {

	private Context context = null;
	private ArrayList<HashMap<String, Object>> listItem = null;
	private TextView nameTv = null;
	private TextView contentTv = null;

	public SearchResultListViewAdapter(Context context,
			ArrayList<HashMap<String, Object>> listItem) {
		super();
		this.context = context;
		this.listItem = listItem;
	}

	@Override
	public int getCount() {
		return listItem.size();

	}

	@Override
	public Object getItem(int arg0) {
		return listItem.get(arg0);

	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// 取出数据
		String name = listItem.get(position).get("name").toString();
		String content = listItem.get(position).get("content").toString();
		View view;

		// 获取四个Textview控件
		if (convertView == null) {
			RelativeLayout relativeLayout = (RelativeLayout) View.inflate(
					context, R.layout.law_item, null);
			this.nameTv = (TextView) relativeLayout.findViewById(R.id.name);
			this.contentTv = (TextView) relativeLayout
					.findViewById(R.id.content);

			view = relativeLayout;
		} else {
			view = convertView;

			this.nameTv = (TextView) view.findViewById(R.id.name);
			this.contentTv = (TextView) view.findViewById(R.id.content);
		}

		// 解析在LuceneConfigurationUtil设置的html格式代码
		this.nameTv.setText(Html.fromHtml(name));
		this.contentTv.setText(Html.fromHtml(content));
		
		return view;
	}

}

package com.example.listadapter;

import java.util.List;

import com.example.weread.Info;
import com.example.weread.R;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

public class ListViewAdapter extends BaseAdapter {  
    View[] itemViews;
    
    //View itemView;

    public ListViewAdapter(List<Info> mlistInfo, Context con) {
    	//this.itemView = itemView;
		// TODO Auto-generated constructor stub
        itemViews = new View[mlistInfo.size()];            
        for(int i=0;i<mlistInfo.size();i++){
        	Info getInfo=(Info)mlistInfo.get(i);	//获取第i个对象
        	//调用makeItemView，实例化一个Item
        	itemViews[i]=makeItemView(
        			getInfo.getFileName(),con
        			);
        }
	}

	public int getCount() {
        return itemViews.length;  
    }

    public View getItem(int position) {  
        return itemViews[position];  
    }  

    public long getItemId(int position) {  
    
        return position;  
    }
    
    //绘制Item的函数
    private View makeItemView(String strFileName, Context con) {  
        LayoutInflater inflater = (LayoutInflater) con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);  

        // 使用View的对象itemView与R.layout.item关联
        View itemView = inflater.inflate(R.layout.book_item, null);
        
        // 通过findViewById()方法实例R.layout.item内各组件
        TextView fileNametv = (TextView) itemView.findViewById(R.id.fileName);  
        fileNametv.setText(strFileName);	//填入相应的值
        fileNametv.setTextColor(R.drawable.bookLocalTextColor);
        
        return itemView;  
    }

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {  
        if (convertView == null)  
            return itemViews[position];  
        return convertView; 
    }
}
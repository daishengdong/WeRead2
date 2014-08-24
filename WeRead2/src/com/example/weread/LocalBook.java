package com.example.weread;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.example.file.FileOperate;
import com.example.listadapter.ListViewAdapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnCreateContextMenuListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class LocalBook extends Activity {

	private ListView listview;
	private String filepath;
	private List<Info> items = new ArrayList<Info>();//用于保存扫描出的.zip文件
	private ImageButton btn2bookStore;
	private ImageButton btn_local2first;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.localbook);
		btn2bookStore = (ImageButton)findViewById(R.id.imgbt_tobookstore);
		btn_local2first = (ImageButton)findViewById(R.id.imgbt_local2first);
		BtnListener l = new BtnListener();
		btn2bookStore.setOnClickListener(l);
		btn_local2first.setOnClickListener(l);
		
		listview = (ListView)findViewById(R.id.listView);
		//System.out.println("ok");
		filepath = getSDPath() + "/books";   //"file://" + 
		//System.out.println(filepath);
		
		File file = new File(filepath);
		FileOperate operate = new FileOperate();
		operate.scan(file, items);
 		
 		
 		LayoutInflater inflater = (LayoutInflater) LocalBook.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE); 
 		View itemView = inflater.inflate(R.layout.book_item, null);
 		listview.setAdapter(new ListViewAdapter(items, LocalBook.this)); 
        //out(items);
 		
 		listview.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				
				
        		Info getObject = items.get(position);	//通过position获取所点击的对象
        		
        		String fileNameStr = getObject.getFileName();	//获取信息标题
        		Intent intent = new Intent();
        		intent.setClass(LocalBook.this, ReadBook.class);
        		intent.putExtra("fileName", fileNameStr);
        		startActivity(intent);
        		
        		//Toast显示测试
        		//Toast.makeText(LocalBook.this, "小说名：" + fileNameStr,Toast.LENGTH_SHORT).show();
        	}
        });
 		
 		listview.setOnCreateContextMenuListener(myOnCreateContextMenuListener);
 		
	}
	
	OnCreateContextMenuListener myOnCreateContextMenuListener = new OnCreateContextMenuListener()
	 {

	  @Override
	  public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo)
	  {
	   menu.setHeaderTitle("书籍操作");//标题
	   menu.add(0, 1, 0, "删除书籍");//下拉菜单
	   menu.add(0, 2, 0, "取消");
	  }
	 };
	  
	 
	private Handler handler = new Handler()
	{
		 @Override
	        public void handleMessage(Message msg){
	            switch(msg.what){
	            case 1:
	            	Bundle bundle = msg.getData();
	            	int position = bundle.getInt("deleteFilePosition");
	            	items.remove(position);
	            	listview.setAdapter(new ListViewAdapter(items, LocalBook.this));
	            	break;
	            }
		 }
	};
	class BtnListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
			switch(v.getId())
			{
			case R.id.imgbt_tobookstore:
				
				intent.setClass(LocalBook.this, BookStoreActivity.class);
				startActivity(intent);
				break;
			case R.id.imgbt_local2first:
				intent.setClass(LocalBook.this, MainActivity.class);
				startActivity(intent);
				break;
			}
		}
		
	}
	void out(List<Info> items)
	{
		System.out.println("total: " + items.size());
		for(int i = 0; i < items.size(); i++)
		{
			System.out.println(items.get(i).getFileName());
		}
	}
	/*
	public void scan(File file)
	{
		
		if(file.isDirectory())
		{
			File[] files =file.listFiles();
			
			for(int i = 0; i < files.length; i++)
			{
				scan(files[i]);
			}
		}
		else{
				if(isZip(file))
		    	{
					String filenameStr = file.toString();
					filenameStr = filenameStr.substring(filenameStr.lastIndexOf("/") + 1, filenameStr.indexOf(".zip"));
					Info information = new Info();
					
					information.setFileName(filenameStr);
					items.add(information);
		    	}
			}
			
	}
	
	public boolean isZip(File file)
	{
		if(file.getName().endsWith(".zip"))
			return true;
		else
			return false;
	}

	public void filesDelete(File file)
	{
		Message message = new Message();
		if(file.exists() == false)
		{
			message.what = 0;    //文件不存在
		}
		else{
			if(file.isFile() == true)
			{
				file.delete();
				return;
			}
			if(file.isDirectory())
			{
				File[] fileChild = file.listFiles();
				if(fileChild == null || fileChild.length == 0)
				{
					file.delete();
					return;
				}
				for(File f:fileChild)
				{
					filesDelete(f);
				}
				
				file.delete();
			}
		}
	}
	
	*/
	public String getSDPath() {
		File sdDir = null;
		boolean sdCardExist = Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED); // 判断sd卡是否存在
		if (sdCardExist) {
			sdDir = Environment.getExternalStorageDirectory();// 获取跟目录
		}
		return sdDir.toString();

	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterView.AdapterContextMenuInfo contextMenuInfo = (AdapterContextMenuInfo) item.getMenuInfo();
		int position = contextMenuInfo.position;
		int id;
		Message message = new Message();
		switch (item.getItemId()) {
		case 1:
			String name = items.get(position).getFileName();
			String path = getSDPath() + "/books/" + name + ".zip";
			File file = new File(path);
			file.delete();
			message.what = 1;           //删除zip文件
			Bundle bundle = new Bundle();
			bundle.putInt("deleteFilePosition", position);
			message.setData(bundle);
			handler.sendMessage(message);
			//System.out.println(name);
			break;
		case 2:
			break;
		default:
			break;
		}
		return super.onContextItemSelected(item);
	}

}
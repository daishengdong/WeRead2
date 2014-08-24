package com.example.weread;

import java.io.IOException;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import com.example.downloader.HttpDownloader;
import com.example.listadapter.ListViewAdapter;
import com.example.xml.BookListContentHandler;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class BookStoreActivity extends ListActivity {
	private ImageButton btn_refresh;
	private ImageButton btn_back;
	private List<Info> infos;
	private ListView listview;
	private ProgressDialog progressDialog;
	private AlertDialog alertDialog;
	private String pathUrl;
	private String fileNameStr;
	 @Override
	 public void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
	     setContentView(R.layout.bookstore);
	     
	     ItemListener l = new ItemListener();
	     
	     System.out.println("yes");
	     btn_refresh = (ImageButton)findViewById(R.id.imgbt_refresh);
	     btn_back = (ImageButton)findViewById(R.id.imgbt_store2local);
	     listview = (ListView)findViewById(R.id.listview2);
	     listview.setOnItemClickListener(l);
	     btn_refresh.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				updateListView();
			}
	    	 
	     });
	     
	     btn_back.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					Intent intent = new Intent();
	        		intent.setClass(BookStoreActivity.this, LocalBook.class);
	        		
	        		startActivity(intent);
					
				}
		    	 
		     });
	     
	     
	     updateListView();
	 }
	 
	 class ItemListener implements OnItemClickListener
	 {

		 @Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
			 
     		Info getObject = infos.get(position);	//通过position获取所点击的对象
     		
     		fileNameStr = getObject.getFileName();	//获取信息标题
     		System.out.println("fileNameStr  " + fileNameStr);
     		
     		//download zip file
     		
     		new AlertDialog.Builder(BookStoreActivity.this).setTitle("下载提示")  
     		.setMessage("确定下载吗？")  
     		.setPositiveButton("是", new DialogInterface.OnClickListener() {
     	        public void onClick(DialogInterface dialog, int whichButton) {
     	        	progressDialog = ProgressDialog.show(BookStoreActivity.this, "", 
      	                   "下载数据，请稍等 …", true, true); 
     	        	Thread thread = new Thread(runnable2);
     	        	thread.start();
     	        	
     	        	
     	            }
     	            })  
     		.setNegativeButton("否", null)  
     		.show();  
     		
     		
     	}
     }
	 
	 /*
	 public class ListViewAdapter extends BaseAdapter {  
	        View[] itemViews;
	  
	        public ListViewAdapter(List<Info> mlistInfo) {
				// TODO Auto-generated constructor stub
	            itemViews = new View[mlistInfo.size()];            
	            for(int i=0;i<mlistInfo.size();i++){
	            	Info getInfo=(Info)mlistInfo.get(i);	//获取第i个对象
	            	//调用makeItemView，实例化一个Item
	            	itemViews[i]=makeItemView(
	            			getInfo.getFileName()
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
	        private View makeItemView(String strFileName) {  
	            LayoutInflater inflater = (LayoutInflater) BookStoreActivity.this  
	                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);  
	  
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
	 */
	 private Handler handler=new Handler(){
	        @Override
	        public void handleMessage(Message msg){
	            switch(msg.what){
	            case 1:
	                //关闭
	            	
      			out(infos);
      			LayoutInflater inflater = (LayoutInflater) BookStoreActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE); 
      			View itemView = inflater.inflate(R.layout.book_item, null);
      			listview = (ListView)findViewById(R.id.listview2);
      			listview.setAdapter(new ListViewAdapter(infos, BookStoreActivity.this));
      			progressDialog.dismiss();
      			break;
	            case 2:
	            	
	            	Toast.makeText(BookStoreActivity.this, "文件已下载，请勿重复下载", Toast.LENGTH_SHORT).show();
	            	progressDialog.dismiss();
	            	break;
	            case 3:
	            	
	            	Toast.makeText(BookStoreActivity.this, "下载失败", Toast.LENGTH_SHORT).show();
	            	progressDialog.dismiss();
	            	break;
	            case 4:
	            	progressDialog.dismiss();
	            	Toast.makeText(BookStoreActivity.this, "下载成功，正在打开。。。", Toast.LENGTH_SHORT).show();
	            	Intent intent = new Intent();
	        		intent.setClass(BookStoreActivity.this, ReadBook.class);
	        		intent.putExtra("fileName", fileNameStr);
	        		startActivity(intent);
	            }
	            
	        }
	    };
	 private void updateListView()
	 {
		 infos = new ArrayList<Info>();
		 progressDialog = ProgressDialog.show(this, "", 
                 "正在更新列表，请稍等 …", true, true); 
		 pathUrl = new String("http://192.168.183.59:8080/books/resources.xml");
		 Thread thread = new Thread(runnable);
		 thread.start();
		 
		
	 }
	 
	 Runnable runnable = new Runnable(){
		
             @Override
             public void run() { 
                  //注意：1、tomcat服务器中文件放置：在webapps下新建Mp3文件,在Mp3文件中新建配置文件WEB-INF
				//在Mp3文件中放置工程中需要下载和用到的文件
				//2、Android的本地地址不是127.0.0.1   而是10.0.2.2
				String xml=downloadXML(pathUrl);
				infos = new ArrayList<Info>();
				infos=parse(xml);
				
				Message msg=new Message();
                msg.what=1;
                handler.sendMessage(msg);
				/*
				out(infos);
				listview = (ListView)findViewById(R.id.listview2);
				listview.setAdapter(new ListViewAdapter(infos));
				*/  
             }
         
	 };
	 
	 Runnable runnable2 = new Runnable(){
			
         @Override
         public void run() { 
              //注意：1、tomcat服务器中文件放置：在webapps下新建Mp3文件,在Mp3文件中新建配置文件WEB-INF
			//在Mp3文件中放置工程中需要下载和用到的文件
			//2、Android的本地地址不是127.0.0.1   而是10.0.2.2
        	 
        	//Looper.prepare();
        	
            Message msg=new Message();
			HttpDownloader httpdownloader = new HttpDownloader();
			pathUrl = new String("http://192.168.183.59:8080/books/" + fileNameStr + ".zip" );
			String dirName = "books/";		
			int state = httpdownloader.fileDownload(pathUrl, dirName, fileNameStr + ".zip");
			if(state == 1)
			{
				msg.what = 2;
			}
			else if(state == -1)
			{
				msg.what = 3;
			}
			else
				msg.what = 4;
            handler.sendMessage(msg);
			/*
			out(infos);
			listview = (ListView)findViewById(R.id.listview2);
			listview.setAdapter(new ListViewAdapter(infos));
			*/  
         }
     
 };
	 
	 private String downloadXML(String urlStr){
			HttpDownloader httpdownloader=new HttpDownloader();
			String result=httpdownloader.textDownload(urlStr);
			System.out.println("result: " + result);
			return result;		
		}
	 
	 void out(List<Info> items)
		{
			System.out.println("total: " + items.size());
			for(int i = 0; i < items.size(); i++)
			{
				System.out.println(items.get(i).getFileName());
			}
		}

	 private List<Info> parse(String xmlStr){
			SAXParserFactory factory = SAXParserFactory.newInstance();
			XMLReader reader;
			List<Info> infos = null;
			try {
				reader = factory.newSAXParser().getXMLReader();
				infos=new ArrayList<Info>();
				BookListContentHandler booklisthandler=new BookListContentHandler(infos);
				reader.setContentHandler(booklisthandler);
				reader.parse(new InputSource(new StringReader(xmlStr)));
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
			return infos;		
		}

}

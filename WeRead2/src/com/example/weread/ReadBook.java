package com.example.weread;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Enumeration;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;

import com.example.file.FileOperate;
import com.example.search.activity.SearchActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

public class ReadBook extends Activity {
	private String cur_book_dir_path;

	private String filePath;
	private String zipFilename;
	private String fileName;
	//private String sdDir;
	private WebView webview;
	
	private Button btnBookShelf;
	private Button btnSearch;
	private Button btnSetting;
	
	private static String filename_const = new String("file://") ;
   
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bookread);
		
		bookReadListener listener = new bookReadListener();
		
		btnBookShelf = (Button)findViewById(R.id.btn_bookshelf);
		btnBookShelf.setOnClickListener(listener);
		btnSearch = (Button)findViewById(R.id.btn_search);
		btnSearch.setOnClickListener(listener);
		btnSetting = (Button)findViewById(R.id.btn_setting);
		btnSetting.setOnClickListener(listener);
		
		
		Intent intent = getIntent();
		
		//zipFilename = intent.getStringExtra("filePath");
		fileName = intent.getStringExtra("fileName");
		//filePath = zipFilename.substring(0, zipFilename.indexOf(fileName + ".zip")) + "books_read";
		filePath = Environment.getExternalStorageDirectory() + "/books/books_read";

		cur_book_dir_path = filePath + "/" + fileName;

		zipFilename = Environment.getExternalStorageDirectory() + "/books/" + fileName + ".zip";
		WifiManager wifiMgr = (WifiManager)getSystemService(ReadBook.this.WIFI_SERVICE);
		WifiInfo info = (null == wifiMgr ? null : wifiMgr.getConnectionInfo());
		String password = info.getMacAddress();
		
		try {
			// ʹ�����Ĳ�������İ汾
			unZipFile(zipFilename, filePath);

			
			//FileOperate decrypt = new FileOperate();
			//File file = new File(filePath + "/" + fileName);
			
			
			//decrypt.filesDecrypt(file, ReadBook.this, fileName, password);
			

		} catch (IOException e1) {
			e1.printStackTrace();
		}
        
		
		 webview = (WebView) findViewById(R.id.webview);
		  
		 //filename = "file:///storage/sdcard/books/books_read/book_a/index.html";
		 webview.getSettings().setJavaScriptEnabled(true); // ������Ҫ��ʾ����ҳ
		 webview.loadUrl(filename_const + filePath + "/" + fileName + "/index.html"); // ����Web��ͼ
		 //System.out.println(filename_const + filePath + "/" + fileName + "/index.html");
		 //webview.loadUrl(filename); // ����Web��ͼ
		 webview.setWebViewClient(new HelloWebViewClient());
		 
	}

	public String getSDPath() {
		File sdDir = null;
		boolean sdCardExist = Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED); // �ж�sd���Ƿ����
		if (sdCardExist) {
			sdDir = Environment.getExternalStorageDirectory();// ��ȡ��Ŀ¼
		}
		return sdDir.toString();

	}

	// �޸Ĺ������Խ��������������
	public static void unZipFile(String archive, String decompressDir)
			throws IOException, FileNotFoundException {

		BufferedInputStream bi;
		File file = new File(archive);
		ZipFile zf = null;
		try {

			// zf = new ZipFile(file);
			zf = new ZipFile(archive, "GBK");//Ҫָ��GBK���룬����Ϊ����
			System.out.println("yes");
			// Enumeration<ZipEntry> e = (Enumeration<ZipEntry>) zf.entries();
			Enumeration<ZipEntry> e = (Enumeration<ZipEntry>) zf.getEntries();
			System.out.println(e.hasMoreElements());
			while (e.hasMoreElements()) {
				ZipEntry ze2 = (ZipEntry) e.nextElement();
				String entryName = ze2.getName();

				System.out.println("=*= " + entryName + " =*=");

				String path = decompressDir + "/" + entryName;
				if (ze2.isDirectory()) {
					System.out.println("���ڴ�����ѹĿ¼ - " + entryName);
					File decompressDirFile = new File(path);
					if (!decompressDirFile.exists()) {
						decompressDirFile.mkdirs();
					}
				} else {
					System.out.println("���ڴ�����ѹ�ļ� - " + entryName);
					String fileDir = path.substring(0, path.lastIndexOf("/"));
					File fileDirFile = new File(fileDir);
					if (!fileDirFile.exists()) {
						fileDirFile.mkdirs();
					}
					BufferedOutputStream bos = new BufferedOutputStream(
							new FileOutputStream(decompressDir + "/"
									+ entryName));
					bi = new BufferedInputStream(zf.getInputStream(ze2));
					byte[] readContent = new byte[1024];
					int readCount = bi.read(readContent);
					while (readCount != -1) {
						bos.write(readContent, 0, readCount);
						readCount = bi.read(readContent);
					}
					bos.close();
				}
			}
			zf.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// bIsUnzipFinsh = true;
	}

	@Override
	// ���û���
	// ����Activity���onKeyDown(int keyCoder,KeyEvent event)����
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK) && webview.canGoBack()) {
			webview.goBack(); // goBack()��ʾ����WebView����һҳ��
			return true;
		}
		return false;
	}

	// Web��ͼ
	private class HelloWebViewClient extends WebViewClient {
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			view.loadUrl(url);
			return true;
		}
	}

	public class bookReadListener implements OnClickListener
	{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch(v.getId())
			{
			case R.id.btn_bookshelf:
				File file = new File(filePath);
				FileOperate operate = new FileOperate();
				operate.filesDelete(file, ReadBook.this);   //�˳�ʱɾ����ѹ������
				Intent intent = new Intent();
				intent.setClass(ReadBook.this, LocalBook.class);
				startActivity(intent);
				ReadBook.this.finish(); 
				break;
			case R.id.btn_search:
				Intent intent2 = new Intent();
				intent2.putExtra("cur_book_dir_path", cur_book_dir_path);
				intent2.setClass(ReadBook.this, SearchActivity.class);
				startActivity(intent2);
				break;
			case R.id.btn_setting:
				break;
			}
		}

	}


}
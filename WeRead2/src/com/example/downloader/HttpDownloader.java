package com.example.downloader;

import java.net.*;
import java.io.*;

import org.apache.http.HttpConnection;

import android.app.ProgressDialog;
import android.content.Context;

import com.example.weread.BookStoreActivity;

public class HttpDownloader {	
	
	public InputStream getInput(URL url){		
		HttpURLConnection httpconn=null;
		InputStream input = null;
		try {
			httpconn=(HttpURLConnection)url.openConnection();
			httpconn.setDoInput(true);
			httpconn.connect();
			input=httpconn.getInputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return input;		
	}
	
	public String textDownload(String urlStr) {
		URL url;
		InputStream input = null;
		StringBuffer sb = new StringBuffer();
		String line = null;
		BufferedReader buffer = null;
		try {
			// 创建一个URL对象
			url = new URL(urlStr);
			// 创建一个Http连接	
			input=getInput(url);
			// 使用IO流读取数据
			buffer = new BufferedReader(new InputStreamReader(input,"gb2312"));
			while ((line = buffer.readLine()) != null) {
				sb.append(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				buffer.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}
	
	/*
	 * 返回1：代表文件已经存在 ; 
	 * 返回0：代表下载成功；
	 * 返回-1：代表下载失败
	 */
	
	public int fileDownload(String strUrl,String dirName,String fileName){
		InputStream input = null;
		SdcardWrite cardwrite=new SdcardWrite();
		URL url;		
		if(cardwrite.isFileExist(dirName,fileName)){
			return 1;
		}
		else{
			try {
				
				url=new URL(strUrl);
				input=getInput(url);
				File file=cardwrite.wirtetoSdcard(dirName, fileName, input);
				if(file==null){
					return -1;
				}
			} catch (MalformedURLException e) {
				e.printStackTrace();
				return -1;
			}
		}
		return 0;		
	}	
}

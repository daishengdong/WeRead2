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
			// ����һ��URL����
			url = new URL(urlStr);
			// ����һ��Http����	
			input=getInput(url);
			// ʹ��IO����ȡ����
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
	 * ����1�������ļ��Ѿ����� ; 
	 * ����0���������سɹ���
	 * ����-1����������ʧ��
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

package com.example.downloader;

import java.io.*;
import java.util.*;

import com.example.weread.Info;

import android.annotation.SuppressLint;
import android.os.Environment;


public class SdcardWrite {
	private String SDPATH;
	
	public SdcardWrite(){
		SDPATH=Environment.getExternalStorageDirectory()+"/";
	}
	
	public File createDir(String dirName){	
		File dir=new File(SDPATH+dirName+File.separator);
		dir.mkdirs();
		return dir;
	}
	
	public boolean isFileExist(String dirName,String fileName){
		File file=new File(SDPATH+dirName+fileName+File.separator);
		return file.exists();		
	}
	
	public File createFile(String dir,String fileName){
		File file=new File(SDPATH+dir+fileName+File.separator);
		System.out.println(SDPATH+dir+fileName+File.separator);
		try {
			file.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return file;
		
	}
	
	public File wirtetoSdcard(String dirName,String fileName,InputStream input){
		//createDir(dirName);
		File file=createFile(dirName,fileName);
		byte b[]=new byte[4*1024];
		OutputStream output = null;
		try {
			output=new FileOutputStream(file);
			try {
				int line;
				while((line=input.read(b)) != -1){
					output.write(b,0,line);
					
				}
				output.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			try{
				output.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}		
		return file;		
	}
	
	/*
	 * 读取sdcard上制定路径下的文件名和大小
	 

	public List<Info> getInfo(String dirName){
		List<Info> infos=new ArrayList<Info>();
		File file=new File(SDPATH+File.separator+dirName);
		File[] files=file.listFiles();
		for (int i = 0; i < files.length; i=i+2) {
			Info info=new Info();
			info.setFileName(files[i].getName());
			
				
			
			infos.add(info);
		}
		return infos;		
	}
	*/
}

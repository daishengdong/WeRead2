package com.example.file;

import java.io.File;
import java.util.List;

import android.content.Context;
import android.os.Message;
import android.widget.Toast;

import com.example.weread.Info;

public class FileOperate {
	
	
	public void scan(File file, List<Info> items)
	{
		
		if(file.isDirectory())
		{
			File[] files =file.listFiles();
			
			for(int i = 0; i < files.length; i++)
			{
				scan(files[i], items);
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

	public void filesDelete(File file, Context con)
	{
		//Message message = new Message();
		if(file.exists() == false)
		{
			Toast.makeText(con, file.getName() + "is not exist", Toast.LENGTH_SHORT).show();
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
					filesDelete(f, con);
				}
				
				file.delete();
			}
		}
	}
	
	public void filesDecrypt(File file, Context con, String book_name, String password)
	{
		//Message message = new Message();
		if(file.exists() == false)
		{
			Toast.makeText(con, file.getName() + "is not exist", Toast.LENGTH_SHORT).show();
		}
		else{
			if(file.isFile() == true && file.getName().endsWith(".html"))
			{
				decryptFilePBE(file, book_name, password);
				System.out.println("srcFile:  " + file.getPath());
				//file.delete();
				return;
			}
			if(file.isDirectory())
			{
				File[] fileChild = file.listFiles();
				if(fileChild == null || fileChild.length == 0)
				{
					//encodeFilePBE(file, book_name );
					//file.delete();
					return;
				}
				for(File f:fileChild)
				{
					filesDecrypt(f, con, book_name, password);
				}
				
				//file.delete();
			}
		}
	}
	
	private void decryptFilePBE(File file, String book_name, String password)
	{
		FileEncodePBE filePBE = new FileEncodePBE();
		try{
		    filePBE.fileDecryptPBE(file, book_name, password);
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}

}

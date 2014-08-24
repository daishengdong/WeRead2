package com.example.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.security.Key;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

public class FileEncodePBE {

	public static void  fileDecryptPBE (File file, String book_name, String password)throws Exception
	{
		String htmlInfo = "";
		try{
			FileInputStream ism = new FileInputStream(file);
			InputStreamReader isr = new InputStreamReader(ism);
			BufferedReader bs = new BufferedReader(isr);
			String info=bs.readLine();
			
			while(info != null){
			    htmlInfo = htmlInfo.concat(info); 
			    info = bs.readLine();   
			}
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}	
		
		byte[] encodeHtml = decryptPBE(htmlInfo, book_name, password);          //得到的加密后的文字
		
		//String filePath = file.getName();
		
		//System.out.println("filePath:  " + filePath);
		System.out.println("????????????????????????????????????????");
		String outFilePath = file.getPath();
		File outfile = new File(outFilePath);
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		System.out.println("outFilePath:  " + outFilePath);
		
		 FileOutputStream outSTr = new FileOutputStream(outfile);   

		 //BufferedOutputStream Buff=new BufferedOutputStream(outSTr); 
		 
		 outSTr.write(encodeHtml);
		 outSTr.flush();
		 outSTr.close();
		
	}
	
	private  static byte[]  decryptPBE(String data, String book_name, String password)throws Exception
	{
		String saltStr = dealSalt(book_name);
		System.out.println("Mac:  " + password);
		password = MDEncode.encodeMD5(password.getBytes());
		System.out.println("password:   " + password);
		System.out.println("salt:  " + saltStr);
		Key key = toKey(password);

		byte[] iv = {1,2,3,4,5,6,7,8};
		SecureRandom zeroIv = new SecureRandom(iv);
		PBEParameterSpec paramSpec = new PBEParameterSpec(saltStr.getBytes(),100);
		Cipher cipher = Cipher.getInstance("PBEWITHMD5andDES");
		cipher.init(Cipher.DECRYPT_MODE, key, paramSpec, zeroIv);
		return cipher.doFinal(data.getBytes());
		
	}
	
	private static String dealSalt(String book_name)
	{
		String saltStr = null;
		if(book_name.length() > 8)
		{
			saltStr = new String(book_name.substring(0,7));
		}
		else{
			saltStr = new String(book_name);
			while(saltStr.length() < 8)
			{
				saltStr = saltStr.concat("t");
			}
		}
		return saltStr;
	}
	
	private static Key toKey(String password)throws Exception
	{
		SecretKeyFactory keyFactory = null;
		PBEKeySpec keySpec = new PBEKeySpec(password.toCharArray());
		try{
			keyFactory = SecretKeyFactory.getInstance("PBEWITHMD5andDES");
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
		SecretKey secretKey = keyFactory.generateSecret(keySpec);
		
		return secretKey;
		
				
	}
}

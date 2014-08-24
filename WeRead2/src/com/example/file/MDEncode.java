package com.example.file;
import java.security.MessageDigest;


public class MDEncode {
	
	public static String encodeMD2(byte[] data)throws Exception{
		
		MessageDigest md = MessageDigest.getInstance("MD2");
		md.update(data);
		byte []encode = md.digest();
		return byteArrayToHex(encode);
	}
	
	public static String encodeMD5(byte[] data)throws Exception{
		
		MessageDigest md = MessageDigest.getInstance("MD5");
		return byteArrayToHex(md.digest(data));
	}
	

	public static String byteArrayToHex(byte[] byteArray) {

	      // ���ȳ�ʼ��һ���ַ����飬�������ÿ��16�����ַ�

	      char[] hexDigits = {'0','1','2','3','4','5','6','7','8','9', 'A','B','C','D','E','F' };

	 

	      // newһ���ַ����飬�������������ɽ���ַ����ģ�����һ�£�һ��byte�ǰ�λ�����ƣ�Ҳ����2λʮ�������ַ���2��8�η�����16��2�η�����

	      char[] resultCharArray =new char[byteArray.length * 2];

	 

	      // �����ֽ����飬ͨ��λ���㣨λ����Ч�ʸߣ���ת�����ַ��ŵ��ַ�������ȥ

	      int index = 0;

	      for (byte b : byteArray) {

	         resultCharArray[index++] = hexDigits[b>>> 4 & 0xf];

	         resultCharArray[index++] = hexDigits[b& 0xf];

	      }

	 

	      // �ַ�������ϳ��ַ�������

	      return new String(resultCharArray);

	}

}

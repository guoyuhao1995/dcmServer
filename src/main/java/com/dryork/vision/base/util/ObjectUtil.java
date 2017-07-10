package com.dryork.vision.base.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


/**
 * @author wenghongbo
 *
 * 2015年6月9日下午6:23:50
*/
public class ObjectUtil {
	
	/**对象转byte[]*/
	public static byte[] ObjectToByte(Object obj){
		byte[] bytes=null;
		try{
			ByteArrayOutputStream out=new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(out);
	        oos.writeObject(obj);
	        bytes=out.toByteArray();
	        oos.close();
	        out.close();
		}catch(Exception e){e.printStackTrace();}
		return bytes;
	}
	
	
	/**byte[]转对象*/
	public static Object ByteToObject(byte[] bytes){
		Object obj=null;
		try{
			ByteArrayInputStream in=new ByteArrayInputStream(bytes);
			ObjectInputStream ois=new ObjectInputStream(in);
			obj=ois.readObject();
			in.close();
			ois.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return obj;
	}
}

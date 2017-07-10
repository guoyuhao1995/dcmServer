package com.dryork.vision.base.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;


public class ToolUtil {

	private static final Random random = new Random();
	private static final String[] arr={"A","B","C","D","E","F","G","H","I","J","K","L","M","N","P","Q","R","S","T","U","V","W","X","Y","Z","1","2","3","4","5","6","7","8","9"};
	
	
	/**生成N位随机数*/
	public static  String getRandom(int n){
		String sRand="";
		for (int i=0;i<n;i++){
		    String rand=String.valueOf(random.nextInt(10));
		    sRand+=rand;
		}
		return sRand;
	}
	
	
	/**生成邀请码**/
	public static String getCode(int max){
		String mill=String.valueOf(System.currentTimeMillis()>>random.nextInt(max))+String.valueOf(System.currentTimeMillis()>>random.nextInt(max));
		int milen=mill.length();
		StringBuilder retstr=new StringBuilder(max);
		int n=0;int i=0;
		char[] cmil=mill.toCharArray();
		while(n<max){
			if(i>=milen-1){
				i=0;
				cmil=new StringBuilder(mill).reverse().toString().toCharArray();
			}
			int nn=Integer.parseInt(String.valueOf(cmil[i])+String.valueOf(cmil[i+1]));
			if(nn<34){
				retstr.append(arr[nn]);
				n++;
			}
			if(n!=max)
			retstr.append(arr[random.nextInt(34)]);
			i+=2;n++;
		}
		return retstr.toString();
	}
	
	/**
	 * 获取真实Ip地址
	 * */
	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("X-Real-IP");
		if (!org.apache.commons.lang.StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
			return ip;
		}
		ip = request.getHeader("X-Forwarded-For");
		if (!org.apache.commons.lang.StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
			// 多次反向代理后会有多个IP值，第一个为真实IP
			int index = ip.indexOf(',');
			if (index != -1) {
				return ip.substring(0, index);
			} else {
				return ip;
			}
		} else {
			return request.getRemoteAddr();
		}
	}
	
	public static boolean checkMobile(String str){
		String regExp = "^((13[0-9])|(14[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$";
		Pattern p = Pattern.compile(regExp);
		Matcher m = p.matcher(str);
		return m.matches();
	}
	
	
	/**解码**/
	public static String decode(String content){
		try{
			return URLDecoder.decode(content, "UTF-8");
		}catch(Exception e){
			return content;
		}
	}
	
	
	/**编码*/
	public static String encode(String content){
		try {
			return URLEncoder.encode(content, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return content;
		}
	}
	
	
	/**验证email**/
	public  static boolean isEmail(String mail){
		return Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*").matcher(mail).find();
    }
	
	public static String createToken(int max){
		String mill=String.valueOf(System.currentTimeMillis()>>random.nextInt(max))+String.valueOf(System.currentTimeMillis()>>random.nextInt(max));
		int milen=mill.length();
		StringBuilder retstr=new StringBuilder(max);
		int n=0;int i=0;
		char[] cmil=mill.toCharArray();
		while(n<max){
			if(i>=milen-1){
				i=0;
				cmil=new StringBuilder(mill).reverse().toString().toCharArray();
			}
			int nn=Integer.parseInt(String.valueOf(cmil[i])+String.valueOf(cmil[i+1]));
			if(nn<34){
				retstr.append(arr[nn]);
				n++;
			}
			if(n!=max)
			retstr.append(arr[random.nextInt(34)]);
			i+=2;n++;
		}
		return retstr.toString();
	}
	
/*		public static void main(String[] agr) throws Exception{
			File file=new File("D:\\hq\\WebRoot\\images\\btn12.png");
			   FileInputStream in = new FileInputStream(file);
			   ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
			   byte ch[]=new byte[1024];
			   int i=-1;
			   while ((i=in.read(ch))!= -1) {
				   bytestream.write(ch, 0, i); 
			   }
			   in.close();
			Map<String,String> map=new HashMap<String,String>();
			map.put("nickname", "redwave");
			httpPostImg("http://127.0.0.1/file/uploadImg.do",map,bytestream.toByteArray());
		}*/
}

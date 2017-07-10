package com.dryork.vision.base.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

public class MyStringUtils {

	public static String subString(int start, int end, String src) {
		if (src == null) {
			return src;
		}
		if (src.length() < end) {
			return src.substring(start);
		}

		return src.substring(start, end);
	}

	public static String subString(int end, String src) {
		return subString(0, end, src);
	}

	public static String getStreamString(InputStream input, int content_length) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		if (content_length <= 0)
			return null;
		int needread = content_length;
		while (true) {
			byte[] body = null;
			if (needread < 2048) {
				body = new byte[needread];
			} else {
				body = new byte[2048];
			}

			int haveread = input.read(body);
			if (haveread == -1) {
				break; //可能为流断开
			}
			baos.write(body, 0, haveread);
			baos.flush();

			needread = needread - haveread;
			if (needread <= 0) {
				break;
			}
		}
		baos.flush();
		return baos.toString("utf-8");

	}

	public static InputStream getStringStream(String sInputString) {
		ByteArrayInputStream tInputStringStream = null;
		if (sInputString != null && !sInputString.trim().equals("")) {
			tInputStringStream = new ByteArrayInputStream(sInputString.getBytes());
		}
		return tInputStringStream;
	}

	public static String string2Json(String s) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			switch (c) {
			//	        case '\"':     
			//	            sb.append("oxbf'");
			//	            break;     
			//	        case '\\':     
			//	            sb.append("\\\\");
			//	            break;     
			case '/':
				sb.append("oxbf/");
				break;
			//	        case '\b':     
			//	            sb.append("\\b");     
			//	            break;     
			//	        case '\f':     
			//	            sb.append("\\f");     
			//	            break;     
			//	        case '\n':     
			//	            sb.append("\\n");     
			//	            break;     
			//	        case '\r':     
			//	            sb.append("\\r");     
			//	            break;     
			//	        case '\t':     
			//	            sb.append("\\t");     
			//	            break;     
			default:
				sb.append(c);
			}
		}
		return sb.toString();
	}

	public static String json2String(String s) {
		String str = s;
		//	    str = str.replaceAll("oxbf'", String.valueOf('\"'));
		//	    str = str.replaceAll("\\\\", String.valueOf('\\'));
		str = str.replaceAll("oxbf/", String.valueOf('/'));
		//	    str = str.replaceAll("\\b", String.valueOf('\b'));
		//	    str = str.replaceAll("\\f", String.valueOf('\f'));
		//	    str = str.replaceAll("\\n", String.valueOf('\n'));
		//	    str = str.replaceAll("\\r", String.valueOf('\r'));
		//	    str = str.replaceAll("\\t", String.valueOf('\t'));
		return str;
	}

	public static String htmlEscape(String s) {
		String str = s;
		str = str.replace("&", "&amp;");
		str = str.replace("<", "&lt;");
		str = str.replace(">", "&gt;");
		//str = str.replace("\\", "&quot;");
		return str;
	}

	public static String backTOHtml(String s) {
		//		s = s.replace("&amp;", "&");
		s = s.replace("&lt;", "<");
		s = s.replace("&gt;", ">");
		//s = s.replace("&quot;", "\\");
		return s;
	}

	public static String aa() {
		String str = "asdf<strong>asdf</strong>sdf<a href='http://hao123.com'>asdf</a>gsdg<span style='color:#666666'>k\\\\a<lsj>d&f</span>sadfasdfaf<span style='color:#666666'>s\\d>f<g&sdg</span>";
		//		String[] subs = StringUtils.substringsBetween(str, "<span", "</span>");
		//		for(String s:subs){
		//			int n = StringUtils.indexOf(s, ">");
		//			String a = "<span"+s.substring(0, n+1);
		//			String b = s.substring(n+1);
		//			String c = htmlEscape(b);
		//			String d = "</span>";
		//			str = StringUtils.replace(str, "<span"+s+"</span>", a+c+d);
		//		}
		str = processStr(str);
		return str;
	}

	public static String processStr(String str) {
		if (StringUtils.isNotBlank(str)) {
			str = htmlEscape(str);
			str = processStr(str, "&lt;span", "&lt;/span&gt;", "&gt;");
			str = processStr(str, "&lt;strong&gt;", "&lt;/strong&gt;");
			str = processStr(str, "&lt;b&gt;", "&lt;/b&gt;");
			str = processStr(str, "&lt;a", "&lt;/a&gt;", "&gt;");
		}
		return str;
	}

	public static String processStr(String str, String begin, String end, String and) {
		String[] subs = StringUtils.substringsBetween(str, begin, end);
		try {
			if (subs != null && subs.length > 0) {
				String a, b, c, d;
				for (String s : subs) {
					int n = StringUtils.indexOf(s, and);
					a = begin + s.substring(0, n + and.length());
					b = backTOHtml(a);
					c = s.substring(n + and.length());
					d = backTOHtml(end);
					str = StringUtils.replace(str, new StringBuffer().append(begin).append(s).append(end).toString(), new StringBuffer().append(b).append(c).append(d).toString());
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return str;
	}

	public static String processStr(String str, String begin, String end) {
		String[] subs = StringUtils.substringsBetween(str, begin, end);
		try {
			if (subs != null && subs.length > 0) {
				String a, b;
				for (String s : subs) {
					a = backTOHtml(begin);
					b = backTOHtml(end);
					str = StringUtils.replace(str, new StringBuffer().append(begin).append(s).append(end).toString(), new StringBuffer().append(a).append(s).append(b).toString());
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return str;
	}

	public static String removeStr(String str) {
		if (StringUtils.isNotBlank(str)) {
			str = removeStr(str, "<span", "</span>", ">");
			str = removeStr(str, "<strong>", "</strong>");
			str = removeStr(str, "<b>", "</b>");
			str = removeStr(str, "<a", "</a>", ">");
		}
		return str;
	}

	public static String removeStr(String str, String begin, String end, String and) {
		String[] subs = StringUtils.substringsBetween(str, begin, end);
		try {
			if (subs != null && subs.length > 0) {
				String c;
				for (String s : subs) {
					int n = StringUtils.indexOf(s, and);
					c = s.substring(n + and.length());
					str = StringUtils.replace(str, new StringBuffer().append(begin).append(s).append(end).toString(), c);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return str;
	}

	public static String removeStr(String str, String begin, String end) {
		String[] subs = StringUtils.substringsBetween(str, begin, end);
		try {
			if (subs != null && subs.length > 0) {
				for (String s : subs) {
					str = StringUtils.replace(str, new StringBuffer().append(begin).append(s).append(end).toString(), s);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return str;
	}

	public static String null2empty(String s) {
		return s == null ? "" : s;
	}

	private static final int SESSION_ID_BYTES = 16;

	public static synchronized String generateSessionId() {

		Random random = new SecureRandom();
		byte bytes[] = new byte[SESSION_ID_BYTES];
		random.nextBytes(bytes);
		bytes = getDigest().digest(bytes);
		// Render the result as a String of hexadecimal digits
		StringBuffer result = new StringBuffer();
		for (int i = 0; i < bytes.length; i++) {
			byte b1 = (byte) ((bytes[i] & 0xf0) >> 4);
			byte b2 = (byte) (bytes[i] & 0x0f);
			if (b1 < 10)
				result.append((char) ('0' + b1));
			else
				result.append((char) ('A' + (b1 - 10)));
			if (b2 < 10)
				result.append((char) ('0' + b2));
			else
				result.append((char) ('A' + (b2 - 10)));
		}
		return (result.toString());
	}

	private static MessageDigest getDigest() {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			return md;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static boolean checkEmail(String str) {
		String regExp = "^([a-z0-9A-Z-_]+[\\.]?)+[a-z0-9A-Z-_]@([a-z0-9A-Z-_]+(-[a-z0-9A-Z-_]+)?\\.)+[a-zA-Z]{2,}$";
		Pattern p = Pattern.compile(regExp);
		Matcher m = p.matcher(str);
		return m.matches();
	}

	public static boolean checkMobile(String str) {
		String regExp = "^((13[0-9])|(14[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$";
		Pattern p = Pattern.compile(regExp);
		Matcher m = p.matcher(str);
		return m.matches();
	}

	/*
	 * public static boolean checkNickName(String str){ String s = IOSEmojiUtil.transToAndroidemojiNull(str); String regExp = "[_A-Za-z\\d\\u4E00-\\u9FA5]+"; Pattern p = Pattern.compile(regExp); Matcher m = p.matcher(s); return m.matches(); }
	 */

	public static boolean checkPassword(String str) {
		String regExp = "[^ ]+";
		Pattern p = Pattern.compile(regExp);
		Matcher m = p.matcher(str);
		return m.matches();
	}

	/*
	 * public static String replaceNickNameFromWeibo(String str){ String s = IOSEmojiUtil.transToAndroidemojiNull(str); String regExp = "[^_A-Za-z\\d\\u4E00-\\u9FA5]+"; Pattern p = Pattern.compile(regExp); Matcher m = p.matcher(s); return m.replaceAll(""); }
	 */

	public static boolean checkNickName1(String str) {
		//String regExp = "[^#@ ,:%'\"()+-/=<>]";
		String regExp = "[^#@ ,:：%'\"()\\+\\-\\*\\/=<>]{1,}";
		Pattern p = Pattern.compile(regExp);
		Matcher m = p.matcher(str);
		return m.matches();
	}

	public static String getLetterNumCharacter(String str) {
		String regExp = "[^_A-Za-z\\d\\u4E00-\\u9FA5]+";
		Pattern p = Pattern.compile(regExp);
		Matcher m = p.matcher(str);
		return m.replaceAll("");
	}

	/** 生成N位随机数 */
	private static final Random random = new Random();

	public static String getRandom(int n) {
		String sRand = "";
		for (int i = 0; i < n; i++) {
			String rand = String.valueOf(random.nextInt(10));
			sRand += rand;
		}
		return sRand;
	}

	/*
	 * //判断标签是否合法 public static boolean checkLabel(String content){ try { Pattern pattern = Pattern.compile("#([^|#]+)#"); Matcher m = pattern.matcher(content); while (m.find()) { String str1 = m.group(1); String str2 = IOSEmojiUtil.transToAndroidemojiNull(str1); if(!StringUtils.equals(str2, str1)){ return false; } String regExp = "[ ,，_A-Za-z\\d\\u4E00-\\u9FA5]+"; Pattern p2 = Pattern.compile(regExp); Matcher m2 = p2.matcher(str1); boolean k = m2.matches(); if(!k) { return false; } } }catch(Exception ex) { ex.printStackTrace(); } return true; }
	 */
	public static void main(String[] args) {
		System.out.println(generateSessionId());
	}

	public static boolean isNotEmpty(String string) {
		if (string != null && !string.equals("") && !string.equals("-1")) {
			return true;
		} else {
			return false;
		}
	}

}

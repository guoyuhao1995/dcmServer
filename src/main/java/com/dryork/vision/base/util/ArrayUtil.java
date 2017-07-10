package com.dryork.vision.base.util;

import java.util.ArrayList;
import java.util.List;

public class ArrayUtil {
	public static void main(String[] args) {
		String[] str = { "Java", "C++", "Php", "C#", "Python", "C++", "Java" };
		System.out.println("===========================" + removeStr(str));
	}

	/**
	 * 去除数组中重读的数据并以逗号分隔拼接字符串
	 * 
	 * @param str
	 * @return
	 */
	public static String removeStr(String[] str) {
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < str.length; i++) {
			if (!list.contains(str[i])) {
				list.add(str[i]);
			}
		}
		String[] newStr = list.toArray(new String[1]); // 返回一个包含所有对象的指定类型的数组
		StringBuilder sb = new StringBuilder();
		for (String elementB : newStr) {
			// System.out.print(elementB + " ");
			if (elementB != null) {
				if (sb.length() > 0) {
					sb.append(",").append(elementB);
				} else {
					sb.append(elementB);
				}
			}
		}
		return sb.toString();
	}

	// 删除数组中其中一个元素
	public static void deleteStr() {
		String[] str = { "Java", "C++", "Php", "C#", "Python" };
		for (String elementA : str) {
			System.out.print(elementA + " ");
		}
		// 删除php
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < str.length; i++) {
			list.add(str[i]);
		}
		list.remove(2); // list.remove("Php")
		System.out.println();
		String[] newStr = list.toArray(new String[1]); // 返回一个包含所有对象的指定类型的数组
		for (String elementB : newStr) {
			System.out.print(elementB + " ");
		}
		System.out.println();
	}

	// 在数组中增加一个元素
	public static void addStr() {
		String[] str = { "Java", "C++", "Php", "C#", "Python" };
		for (String elementA : str) {
			System.out.print(elementA + " ");
		}
		// 增加ruby
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < str.length; i++) {
			list.add(str[i]);
		}
		list.add(2, "ruby"); // list.add("ruby")
		System.out.println();
		String[] newStr = list.toArray(new String[1]); // 返回一个包含所有对象的指定类型的数组
		for (String elementB : newStr) {
			System.out.print(elementB + " ");
		}
		System.out.println();
	}
}

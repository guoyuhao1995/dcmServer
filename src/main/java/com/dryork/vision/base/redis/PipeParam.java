package com.dryork.vision.base.redis;

/**
 * 此类是pipelined参数的包装类，区分命令的类型
 * 
 **/
public class PipeParam {

	public final static int STRING = 1;
	public final static int LPUSH = 2;
	public final static int HASH = 3;
	public final static int SET = 4;
	public final static int SORTSET = 5;
	public final static int KEY = 6;
	public final static int ZINCRBY=7;

	private int rtype;
	private String key1;
	private String key2;
	private String value;
	private double score;
	
	
	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public int getRtype() {
		return rtype;
	}

	public void setRtype(int rtype) {
		this.rtype = rtype;
	}

	public String getKey1() {
		return key1;
	}

	public void setKey1(String key1) {
		this.key1 = key1;
	}

	public String getKey2() {
		return key2;
	}

	public void setKey2(String key2) {
		this.key2 = key2;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}

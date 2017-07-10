/**
 *Copyright: Copyright (c) 2015
 */
package com.dryork.vision.base.util;

/**
 * @author wenghongbo
 *
 * 2015年6月16日下午6:08:30
 */
public class GlobalResource {

	
	private static final String groupMessageFormat="{\"mtype\":1,\"message\":{\"groupid\":\"%s\",\"contentid\":\"%s\"}}";
	/**群组消息格式*/
	public static String groupMessageFormat(String groupId,String contentid){
		return String.format(groupMessageFormat, groupId,contentid);
	}
	
	public static String getAddFriendContent(String nickName){
		return nickName+"已通过您的好友请求";
	}
	
	public static String getDelFriendContent(String nickName){
		return nickName+"已不再是您的好友";
	}
	
	public static String getInviteUserGroup(String nickName,String groupName){
		return "您的好友"+nickName+"邀请您加入"+groupName;
	}
	
	public static String getQuitGroup(String nickName,String groupName){
		return nickName+"已退出"+groupName+"群组";
	}
	
	public static String getKickGroup(String nickName,String groupName){
		return nickName+"已被管理员踢出了"+groupName;
	}
	
	public static String getAddUserGroup(String nickName){
		return "欢迎"+nickName+"加入群组";
	}
	
	public static String getDestroyGroup(String groupName){
		return groupName+"被管理员解散";
	}
	
	public static String getGroupPreJoinGroup(String groupName,String groupName2){
		return groupName+"请求与"+groupName2;
	}
	
	public static String getGroupJoinGroup(String groupName,String groupName2){
		return groupName+"与"+groupName2+"合并啦";
	}
}

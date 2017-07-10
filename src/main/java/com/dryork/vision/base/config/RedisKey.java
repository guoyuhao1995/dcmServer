package com.dryork.vision.base.config;

/**
 * 在redis存储的数据的key的前缀，缓存类和持久的分开
 * key的变量部分要能在数据库中找到
 * 
 * @author lilianzhi
 *
 */
public class RedisKey {
	
	
	/**用户信息在redis中的保存时间(3)*/
	public static final int USER_EXPIRE_TIME=60*60*24*3;
	
	/**群组信息在redis中的保存时间(2)*/
	public static final int GROUPINFO_EXPIRE_TIME=60*60*24*2;
	
	
	/**用户信息缓存key :user_uid*/
	public static String getUserInfoKey(String uid){
		return "user_".concat(uid);
	}
	
	
	/**群组信息缓存key :group_groupid*/
	public static String getGroupInfoKey(String groupId){
		return "group_".concat(groupId);
	}
	
	/**群组与用户key :group_user_groupid*/
	public static String getGroupKey(String groupId){
		return "group_user_".concat(groupId);
	}

	/**用户与群组key :user_group_uid*/
	public static String getUserGroupKey(String uid){
		return "user_group_".concat(uid);
	}
	
	/**用户群组消息key :user_group_message_uid*/
	public static String getUserGroupMessageKey(String uid){
		return "user_group_message_".concat(uid);
	}
	
	/**群组消息key :group_message_groupid*/
	public static String getGroupMessageKey(String groupId){
		return "group_message_".concat(groupId);
	}
	
	/**用户好友关系key*/
	public static String getUserFriendKey(String uid){
		return "user_friend_key_".concat(uid);
	}
	
	/**趴场主题key*/
	public static String getSubjecVoKey(){
		return "group_subjectVo";
	}

	
	/**ktv房间列表key*/
	public static String getKtvRoomKey(String shopid){
		return "ktv_room_".concat(shopid);
	}}

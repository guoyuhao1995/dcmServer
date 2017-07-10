package com.dryork.vision.base.redis;


import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import redis.clients.jedis.BinaryClient.LIST_POSITION;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.SortingParams;
import redis.clients.jedis.Tuple;
import redis.clients.util.SafeEncoder;

public class RedisClient{
	
	 private JedisPool jedisPool;
	 
	 /**操作Key的方法*/
	 public  Keys KEYS=new Keys();
	 /**对存储结构为String类型的操作*/
	 public  Strings STRINGS=new Strings();
	 /**对存储结构为List类型的操作*/
	 public  Lists LISTS=new Lists();
	 /**对存储结构为Set类型的操作*/
	 public  Sets SETS=new Sets();
	 /**对存储结构为HashMap类型的操作*/
	 public  Hash HASH=new Hash();
	 /**对存储结构为Set(排序的)类型的操作*/
	 public  SortSet SORTSET=new SortSet();
	 
	 
	 public RedisClient(int maxIdle,int minIdle,int maxTotal,String host,int port){
		 JedisPoolConfig pool = new JedisPoolConfig();
		 pool.setMaxIdle(maxIdle);
		 pool.setMinIdle(minIdle);
		 pool.setMaxTotal(maxTotal);
		 jedisPool = new JedisPool(pool,host,port);//正式环境
		 //jedisPool = new JedisPool(pool,host,port,30000,"Wsndy1919!");//测试环境
		 
		 System.out.println("redis client conn--------------");
	 }
	 
	 public RedisClient(int maxIdle,int minIdle,int maxTotal,String host,int port,String password){
		 JedisPoolConfig pool = new JedisPoolConfig();
		 pool.setMaxIdle(maxIdle);
		 pool.setMinIdle(minIdle);
		 pool.setMaxTotal(maxTotal);
//		 jedisPool = new JedisPool(pool,host,port);//正式环境
		 jedisPool = new JedisPool(pool,host,port,30000,password);//测试环境
		 
		 System.out.println("redis client conn--------------");
	 }


	 
	public class Keys{
		 
		 /**
		  * 设置key的过期时间，以秒为单位
		  * @param String key
		  * @param 时间,已秒为单位
		  * @return 影响的记录数
		  * */
		 public long expire(String key,int seconds){
			 Jedis jedis = null;
			 try{
				 jedis = jedisPool.getResource();
				 return jedis.expire(key, seconds);
			 }finally{
				 jedisPool.returnResourceObject(jedis);
			 }
		 }
		 
		 /**
		  * 设置key的过期时间，以秒为单位
		  * @param byte[] key
		  * @param 时间,已秒为单位
		  * @return 影响的记录数
		  * */
		 public long expire(byte[] key,int seconds){
			 Jedis jedis = null;
			 try{
				 jedis = jedisPool.getResource();
				 return jedis.expire(key, seconds);
			 }finally{
				 jedisPool.returnResourceObject(jedis);
			 }
		 }
		 
		 /**
		  * 设置key的过期时间,它是距历元（即格林威治标准时间 1970 年 1 月 1 日的 00:00:00，格里高利历）的偏移量。
		  * @param String key
		  * @param 时间,已秒为单位
		  * @return 影响的记录数
		  * */
		 public long expireAt(String key,long timestamp){
			 Jedis jedis = null;
			 try{
				 jedis = jedisPool.getResource();
				 return jedis.expireAt(key, timestamp);
			 }finally{
				 jedisPool.returnResourceObject(jedis);
			 }
		 }
		 
		 /**
		  * 查询key的过期时间
		  * @param String key
		  * @return 以秒为单位的时间表示
		  * */
		 public Long ttl(String key){
			 Jedis jedis = null;
			 try{
				 jedis = jedisPool.getResource();
				 return jedis.ttl(key);
			 }finally{
				 jedisPool.returnResourceObject(jedis);
			 }
		 }
		 

		 /**
		  * 删除key对应的记录
		  * @param String key
		  * @return 删除的记录数
		  * */
		 public long del(String key){
			 Jedis jedis = null;
			 try{
				 jedis = jedisPool.getResource();
				 return jedis.del(key);
			 }finally{
				 jedisPool.returnResourceObject(jedis);
			 }
		 }
		 
		 /**
		  * 删除key对应的记录
		  * @param String key
		  * @return 删除的记录数
		  * */
		 public long del(byte key[]){
			 Jedis jedis = null;
			 try{
				 jedis = jedisPool.getResource();
				 return jedis.del(key);
			 }finally{
				 jedisPool.returnResourceObject(jedis);
			 }
		 }
		 
		 /**
		  * 判断key是否存在
		  * @param String key
		  * @return boolean
		  * */
		 public boolean exists(String key){
			 Jedis jedis = null;
			 try{
				 jedis = jedisPool.getResource();
				 return jedis.exists(key);
			 }finally{
				 jedisPool.returnResourceObject(jedis);
			 }
		 }
		 
		 
		 
		 /**
		  * 对List,Set,SortSet进行排序,如果集合数据较大应避免使用这个方法
		  * @param String key
		  * @return List<String> 集合的全部记录
		  * **/
		 public List<String> sort(String key){
			 Jedis jedis = null;
			 try{
				 jedis = jedisPool.getResource();
				 return jedis.sort(key);
			 }finally{
				 jedisPool.returnResourceObject(jedis);
			 }
		 }
		 
		 /**
		  * 对List,Set,SortSet进行排序或limit
		  * @param String key
		  * @param SortingParams parame 定义排序类型或limit的起止位置.
		  * @return List<String> 全部或部分记录
		  * **/
		 public List<String> sort(String key,SortingParams parame){
			 Jedis jedis = null;
			 try{
				 jedis = jedisPool.getResource();
				 return jedis.sort(key, parame);
			 }finally{
				 jedisPool.returnResourceObject(jedis);
			 }
		 }
		 
		 /**
		  * 返回指定key存储的类型
		  * @param String key
		  * @return String  string|list|set|zset|hash
		  * **/
		 public String type(String key){
			 Jedis jedis = null;
			 try{
				 jedis = jedisPool.getResource();
				 return jedis.type(key);
			 }finally{
				 jedisPool.returnResourceObject(jedis);
			 }
		 }
		 
		 
		 
		/**通过pipeline的方式查询数据**/
		public  List<Object> pipeGet(List<PipeParam> params){
			if(params==null || params.size()==0){
				return Collections.emptyList();
			}else{
				 Jedis jedis = null;
				 try{
					 jedis = jedisPool.getResource();
					 Pipeline pipeline = jedis.pipelined();
						for(PipeParam param:params){
							switch (param.getRtype()) {
								case PipeParam.STRING:
									pipeline.get(param.getKey1());break;
								case PipeParam.SORTSET:
									pipeline.zscore(param.getKey1(),param.getKey2());break;
								case PipeParam.LPUSH:
									pipeline.lpush(param.getKey1(), param.getValue());break;
								case PipeParam.KEY:
									pipeline.del(param.getKey1());break;
								case PipeParam.HASH:
									pipeline.hdel(param.getKey1(), param.getKey2());break;
								case PipeParam.ZINCRBY:
									pipeline.zincrby(param.getKey1(), param.getScore(), param.getValue());break;
								default:new Throwable("Type "+param.getRtype()+" is Not define in PipeParam");
							}
						}
						return pipeline.syncAndReturnAll();
				 }finally{
					 jedisPool.returnResourceObject(jedis);
				 }
			}
		}
	 }
	 
	 //TODO
	 public class Sets{
		 
		 /**
		  * 向Set添加一条记录，如果member已存在返回0,否则返回1
		  * @param String key
		  * @param String member
		  * @return 操作码,0或1
		  * */
		 public long sadd(String key,String member){
			 Jedis jedis = null;
			 try{
				 jedis = jedisPool.getResource();
				 return jedis.sadd(key, member);
			 }finally{
				 jedisPool.returnResourceObject(jedis);
			 }
		 }
		 
		 /**
		  * 获取给定key中元素个数
		  * @param String key
		  * @return 元素个数
		  * */
		 public long scard(String key){
			 Jedis jedis = null;
			 try{
				 jedis = jedisPool.getResource();
				 return jedis.scard(key);
			 }finally{
				 jedisPool.returnResourceObject(jedis);
			 }
		 }
		 

		 

		 
		
		 
		
		 /**
		  * 确定一个给定的值是否存在
		  * @param String key
		  * @param String member 要判断的值
		  * @return 存在返回1，不存在返回0
		  * **/
		 public boolean sismember(String key,String member){
			 Jedis jedis = null;
			 try{
				 jedis = jedisPool.getResource();
				 return jedis.sismember(key, member);
			 }finally{
				 jedisPool.returnResourceObject(jedis);
			 }
		 }
		 
		 
		 /**
		  * 返回集合中的所有成员
		  * @param String key
		  * @return 成员集合
		  * */
		 public Set<String> smembers(String key){
			 Jedis jedis = null;
			 try{
				 jedis = jedisPool.getResource();
				 return jedis.smembers(key);
			 }finally{
				 jedisPool.returnResourceObject(jedis);
			 }
		 }
		 
		 

		 
		 /**
		  * 从集合中删除成员
		  * @param String key
		  * @return 被删除的成员
		  * */
		 public String spop(String key){
			 Jedis jedis = null;
			 try{
				 jedis = jedisPool.getResource();
				 return jedis.spop(key);
			 }finally{
				 jedisPool.returnResourceObject(jedis);
			 }
		 }
		 
		 /**
		  * 从集合中删除指定成员
		  * @param String key
		  * @param String member 要删除的成员
		  * @return 状态码，成功返回1，成员不存在返回0
		  * */
		 public long srem(String key,String member){
			 Jedis jedis = null;
			 try{
				 jedis = jedisPool.getResource();
				 return jedis.srem(key,member);
			 }finally{
				 jedisPool.returnResourceObject(jedis);
			 }
		 }
	 }
	 
	 
	 //TODO
	 public class SortSet{
		 
		 /**
		  * 向集合中增加一条记录,如果这个值已存在，这个值对应的权重将被置为新的权重
		  * @param String key
		  * @param double score 权重
		  * @param String member 要加入的值，
		  * @return 状态码 1成功，0已存在member的值
		  * */
		 public long zadd(byte[] key,double score,byte[] member){
			 Jedis jedis = null;
			 try{
				 jedis = jedisPool.getResource();
				 return jedis.zadd(key, score, member);
			 }finally{
				 jedisPool.returnResourceObject(jedis);
			 }
		 }
		 
		 /**
		  * 向集合中增加一条记录,如果这个值已存在，这个值对应的权重将被置为新的权重
		  * @param String key
		  * @param double score 权重
		  * @param String member 要加入的值，
		  * @return 状态码 1成功，0已存在member的值
		  * */
		 public long zadd(String key,double score,String member){
			 Jedis jedis = null;
			 try{
				 jedis = jedisPool.getResource();
				 return jedis.zadd(key, score, member);
			 }finally{
				 jedisPool.returnResourceObject(jedis);
			 }
		 }
		 
		 /**
		  * 获取集合中元素的数量
		  * @param String key
		  * @return 如果返回0则集合不存在
		  * */
		 public long zcard(String key){
			 Jedis jedis = null;
			 try{
				 jedis = jedisPool.getResource();
				 return jedis.zcard(key);
			 }finally{
				 jedisPool.returnResourceObject(jedis);
			 }
		 }
		 
		 /**
		  * 获取指定权重区间内集合的数量
		  * @param String key
		  * @param double min 最小排序位置
		  * @param double max 最大排序位置
		  * */
		 public long zcount(String key,double min,double max){
			 Jedis jedis = null;
			 try{
				 jedis = jedisPool.getResource();
				 return jedis.zcount(key, min, max);
			 }finally{
				 jedisPool.returnResourceObject(jedis);
			 }
		 }
		 
		 /**
		  * 获得set的长度
		  * @param key
		  * @return
		  */
		 public long zlength(String key){
			 Jedis jedis = null;
			 try{
				 long len = 0;
				 Set<String> set = zrange(key,0,-1);
				 len = set.size();
				 return len;
			 }finally{
				 jedisPool.returnResourceObject(jedis);
			 }
		 }
		 
		 /**
		  * 权重增加给定值，如果给定的member已存在
		  * @param String key
		  * @param double score 要增的权重
		  * @param String member 要插入的值
		  * @return 增后的权重
		  * */
		 public double zincrby(String key,double score,String member){
			 Jedis jedis = null;
			 try{
				 jedis = jedisPool.getResource();
				 return jedis.zincrby(key, score, member);
			 }finally{
				 jedisPool.returnResourceObject(jedis);
			 }
		 }
		 
		 /**
		  * 返回指定位置的集合元素,0为第一个元素，-1为最后一个元素
		  * @param String key
		  * @param int start 开始位置(包含)
		  * @param int end	结束位置(包含)
		  * @return Set<String>
		  * */
		 public Set<String> zrange(String key,int start,int end){
			 Jedis jedis = null;
			 try{
				 jedis = jedisPool.getResource();
				 return jedis.zrange(key, start, end);
			 }finally{
				 jedisPool.returnResourceObject(jedis);
			 }
		 }
		 
		 /**
		  * 返回指定权重区间的元素集合
		  * @param String key
		  * @param double min 上限权重
		  * @param double max 下限权重
		  * @return Set<String>
		  * */
		 public Set<String> zrangeByScore(String key,double min,double max){
			 Jedis jedis = null;
			 try{
				 jedis = jedisPool.getResource();
				 return jedis.zrangeByScore(key, min, max);
			 }finally{
				 jedisPool.returnResourceObject(jedis);
			 }
		 }
		 
		 /**
		  * 返回指定权重区间的元素集合
		  * @param String key
		  * @param double min 上限权重
		  * @param double max 下限权重
		  * @return Set<String>
		  * */
		 public Set<byte[]> zrangeByScore(byte[] key,double min,double max){
			 Jedis jedis = null;
			 try{
				 jedis = jedisPool.getResource();
				 return jedis.zrangeByScore(key, min, max);
			 }finally{
				 jedisPool.returnResourceObject(jedis);
			 }
		 }
		 
		 
		 /**
		  * 获取指定值在集合中的位置，集合排序从低到高
		  * @see zrevrank
		  * @param String key
		  * @param String member
		  * @return long 位置
		  * */
		 public long zrank(String key,String member){
			 Jedis jedis = null;
			 try{
				 jedis = jedisPool.getResource();
				 return jedis.zrank(key,member);
			 }finally{
				 jedisPool.returnResourceObject(jedis);
			 }
		 }
		 
		 /**
		  * 获取指定值在集合中的位置，集合排序从低到高
		  * @see zrank
		  * @param String key
		  * @param String member
		  * @return long 位置
		  * */
		 public long zrevrank(String key,String member){
			 Jedis jedis = null;
			 try{
				 jedis = jedisPool.getResource();
				 return jedis.zrevrank(key, member);
			 }finally{
				 jedisPool.returnResourceObject(jedis);
			 }
		 }
		 
		 /**
		  * 从集合中删除成员
		  * @param String key
		  * @param String member
		  * @return 返回1成功
		  * */
		 public long zrem(String key,String ... member){
			 Jedis jedis = null;
			 try{
				 jedis = jedisPool.getResource();
				 return jedis.zrem(key, member);
			 }finally{
				 jedisPool.returnResourceObject(jedis);
			 }
		 }
		 
		 /**
		  * 删除
		  * @param key
		  * @return
		  */
		 public long zrem(String key){
			 Jedis jedis = null;
			 try{
				 jedis = jedisPool.getResource();
				 return jedis.del(key);
			 }finally{
				 jedisPool.returnResourceObject(jedis);
			 }
		 }
		 
		 
		 /**
		  * 删除给定位置区间的元素
		  * @param String key
		  * @param int start 开始区间，从0开始(包含)
		  * @param int end 结束区间,-1为最后一个元素(包含)
		  * @return 删除的数量
		  * */
		 public long zremrangeByRank(String key,int start,int end){
			 Jedis jedis = null;
			 try{
				 jedis = jedisPool.getResource();
				 return jedis.zremrangeByRank(key, start, end);
			 }finally{
				 jedisPool.returnResourceObject(jedis);
			 }
		 }
		 
		 /**
		  * 删除给定权重区间的元素
		  * @param String key
		  * @param double min 下限权重(包含)
		  * @param double max 上限权重(包含)
		  * @return 删除的数量
		  * */
		 public Long zremrangeByScore(String key,double min,double max){	 
			 Jedis jedis = null;
			 try{
				 jedis = jedisPool.getResource();
				 return jedis.zremrangeByScore(key, min, max);
			 }finally{
				 jedisPool.returnResourceObject(jedis);
			 }
		 }
		 
		 /**
		  * 删除给定权重区间的元素
		  * @param String key
		  * @param double min 下限权重(包含)
		  * @param double max 上限权重(包含)
		  * @return 删除的数量
		  * */
		 public Long zremrangeByScore(byte[] key,double min,double max){	 
			 Jedis jedis = null;
			 try{
				 jedis = jedisPool.getResource();
				 return jedis.zremrangeByScore(key, min, max);
			 }finally{
				 jedisPool.returnResourceObject(jedis);
			 }
		 }
		 
		 public  Set<Tuple> zrevrangeWithScores(String key, long start,long end) {
			 Jedis jedis = null;
			 try{
				 jedis = jedisPool.getResource();
				 return jedis.zrevrangeWithScores(key, start, end);
			 }finally{
				 jedisPool.returnResourceObject(jedis);
			 }
		 }
		 
		 
		 /**
		  * 获取给定区间的元素，原始按照权重由高到低排序
		  * @param String key
		  * @param int start
		  * @param int end
		  * @return Set<String>
		  * */
		 public Set<String> zrevrange(String key,long start,long end){
			 Jedis jedis = null;
			 try{
				 jedis = jedisPool.getResource();
				 return jedis.zrevrange(key,start,end);
			 }finally{
				 jedisPool.returnResourceObject(jedis);
			 }
		 }
		 
		 /**
		  * 获取给定值在集合中的权重
		  * @param String key
		  * @param memeber
		  * @return double 权重
		  * */
		 public Double zscore(String key,String memebr){
			 Jedis jedis = null;
			 try{
				 jedis = jedisPool.getResource();
				 return jedis.zscore(key,memebr);
			 }finally{
				 jedisPool.returnResourceObject(jedis);
			 }
		 }
	 }
	 
	 //TODO
	 public class Hash{
		 
		 /**
		  * 从hash中删除指定的存储
		  * @param String key
		  * @param String fieid 存储的名字
		  * @return 状态码，1成功，0失败
		  * */
		 public long hdel(String key,String fieid){
			 Jedis jedis = null;
			 try{
				 jedis = jedisPool.getResource();
				 return jedis.hdel(key, fieid);
			 }finally{
				 jedisPool.returnResourceObject(jedis);
			 }
		 }
		 
		 
		 public long hdel(String key){
			 Jedis jedis = null;
			 try{
				 jedis = jedisPool.getResource();
				 return jedis.hdel(key);
			 }finally{
				 jedisPool.returnResourceObject(jedis);
			 }
		 }
		 
		 public long hdel(byte[] key){
			 Jedis jedis = null;
			 try{
				 jedis = jedisPool.getResource();
				 return jedis.hdel(key);
			 }finally{
				 jedisPool.returnResourceObject(jedis);
			 }
		 }
		 
		 /**
		  * 测试hash中指定的存储是否存在
		  * @param String key
		  * @param String fieid 存储的名字
		  * @return 1存在，0不存在
		  * */
		 public boolean hexists(String key,String fieid){
			 Jedis jedis = null;
			 try{
				 jedis = jedisPool.getResource();
				 return jedis.hexists(key, fieid);
			 }finally{
				 jedisPool.returnResourceObject(jedis);
			 }
		 }
		 
		 /**
		  * 返回hash中指定存储位置的值
		  * @param String key
		  * @param String fieid 存储的名字
		  * @return 存储对应的值
		  * */
		 public String hget(String key,String fieid){
			 Jedis jedis = null;
			 try{
				 jedis = jedisPool.getResource();
				 return jedis.hget(key, fieid);
			 }finally{
				 jedisPool.returnResourceObject(jedis);
			 }
		 }
		 
		 /**
		  * 以Map的形式返回hash中的存储和值
		  * @param String key
		  * @return Map<Strinig,String>
		  * */
		 public Map<String,String> hgetall(String key){
			 Jedis jedis = null;
			 try{
				 jedis = jedisPool.getResource();
				 return jedis.hgetAll(key);
			 }finally{
				 jedisPool.returnResourceObject(jedis);
			 }
		 }
		 
		 /**
		  * 在指定的存储位置加上指定的数字，存储位置的值必须可转为数字类型
		  * @param String key
		  * @param String fieid 存储位置
		  * @param String long value 要增加的值,可以是负数
		  * @return 增加指定数字后，存储位置的值
		  * */
		 public Long hincrby(String key,String fieid,long value){
			 Jedis jedis = null;
			 try{
				 jedis = jedisPool.getResource();
				 return jedis.hincrBy(key, fieid, value);
			 }finally{
				 jedisPool.returnResourceObject(jedis);
			 }
		 }
		 
		 /**
		  * 返回指定hash中的所有存储名字,类似Map中的keySet方法
		  * @param String key
		  * @return Set<String> 存储名称的集合
		  * */
		 public Set<String> hkeys(String key){
			 Jedis jedis = null;
			 try{
				 jedis = jedisPool.getResource();
				 return jedis.hkeys(key);
			 }finally{
				 jedisPool.returnResourceObject(jedis);
			 }
		 }
		 
		 /**
		  * 获取hash中存储的个数，类似Map中size方法
		  * @param String key
		  * @return long 存储的个数
		  * */
		 public long hlen(String key){
			 Jedis jedis = null;
			 try{
				 jedis = jedisPool.getResource();
				 return jedis.hlen(key);
			 }finally{
				 jedisPool.returnResourceObject(jedis);
			 }
		 }
		 
		 /**
		  * 根据多个key，获取对应的value，返回List,如果指定的key不存在,List对应位置为null
		  * @param String key
		  * @param String... fieids  存储位置
		  * @return List<String>
		  * */
		 public List<String> hmget(String key,String...fieids){
			 Jedis jedis = null;
			 try{
				 jedis = jedisPool.getResource();
				 return jedis.hmget(key, fieids);
			 }finally{
				 jedisPool.returnResourceObject(jedis);
			 }
		 }
		 
		 /**
		  * 添加对应关系，如果对应关系已存在，则覆盖
		  * @param Strin key
		  * @param Map<String,String> 对应关系
		  * @return 状态，成功返回OK
		  * */
		 public String hmset(String key,Map<String,String> map){
			 Jedis jedis = null;
			 try{
				 jedis = jedisPool.getResource();
				 return jedis.hmset(key, map);
			 }finally{
				 jedisPool.returnResourceObject(jedis);
			 }
		 }
		 
		 /**
		  * 添加一个对应关系
		  * @param String key
		  * @param String fieid
		  * @param String value
		  * @return 状态码 1成功，0失败，fieid已存在将更新，也返回0
		  * **/
		 public long hset(String key,String fieid,String value){
			 Jedis jedis = null;
			 try{
				 jedis = jedisPool.getResource();
				 return jedis.hset(key, fieid,value);
			 }finally{
				 jedisPool.returnResourceObject(jedis);
			 }
		 }
		 
		 /**
		  * 添加对应关系，只有在fieid不存在时才执行
		  * @param String key
		  * @param String fieid
		  * @param String value
		  * @return 状态码 1成功，0失败fieid已存
		  * **/
		 public long hsetnx(String key,String fieid,String value){
			 Jedis jedis = null;
			 try{
				 jedis = jedisPool.getResource();
				 return jedis.hsetnx(key, fieid,value);
			 }finally{
				 jedisPool.returnResourceObject(jedis);
			 }
		 }
		 
		 /**
		  * 获取hash中value的集合
		  * @param String key
		  * @return List<String>
		  * */
		 public List<String> hvals(String key){
			 Jedis jedis = null;
			 try{
				 jedis = jedisPool.getResource();
				 return jedis.hvals(key);
			 }finally{
				 jedisPool.returnResourceObject(jedis);
			 }
		 }
	 }
	 
	 //TODO
	public class Strings{
		 /**
		  * 根据key获取记录
		  * @param String key
		  * @return 值
		  * */
		 public String get(String key){
			 Jedis jedis = null;
			 try{
				 jedis = jedisPool.getResource();
				 return jedis.get(key);
			 }finally{
				 jedisPool.returnResourceObject(jedis);
			 }
		 }
		 /**
		  * 根据key获取记录
		  * @param byte[] key
		  * @return 值
		  * */
		 public byte[] get(byte[] key){			 
			 Jedis jedis = null;
			 try{
				 jedis = jedisPool.getResource();
				 return jedis.get(key);
			 }finally{
				 jedisPool.returnResourceObject(jedis);
			 }
		 }
		 
		 /**
		  * 添加有过期时间的记录
		  * @param String key
		  * @param int seconds  过期时间，以秒为单位
		  * @param String value
		  * @return String 操作状态
		  * */
		 public String setEx(String key,int seconds,String value){
			 Jedis jedis = null;
			 try{
				 jedis = jedisPool.getResource();
				 return jedis.setex(key, seconds, value);
			 }finally{
				 jedisPool.returnResourceObject(jedis);
			 }
		 } 
		 
		 
		 /**
		  * 添加有过期时间的记录
		  * @param String key
		  * @param int seconds  过期时间，以秒为单位
		  * @param String value
		  * @return String 操作状态
		  * */
		 public String setEx(byte[] key,int seconds,byte[] value){
			 Jedis jedis = null;
			 try{
				 jedis = jedisPool.getResource();
				 return jedis.setex(key, seconds, value);	
			 }finally{
				 jedisPool.returnResourceObject(jedis);
			 }
		 } 
		 
		 /**
		  * 添加一条记录，仅当给定的key不存在时才插入
		  * @param String key
		  * @param String value
		  * @return long 状态码，1插入成功且key不存在，0未插入，key存在
		  * */
		 public long setnx(String key,String value){
			 Jedis jedis = null;
			 try{
				 jedis = jedisPool.getResource();
				 return jedis.setnx(key, value);
			 }finally{
				 jedisPool.returnResourceObject(jedis);
			 }
		 }
		 
		 /**
		  * 添加记录,如果记录已存在将覆盖原有的value
		  * @param String key
		  * @param String value
		  * @return 状态码
		  * */
		 public String set(String key,String value){
			 return set(SafeEncoder.encode(key),SafeEncoder.encode(value));
		 }
		 
		 /**
		  * 添加记录,如果记录已存在将覆盖原有的value
		  * @param byte[] key
		  * @param byte[] value
		  * @return 状态码
		  * */
		 public String set(byte[] key,byte[] value){		 
			 Jedis jedis = null;
			 try{
				 jedis = jedisPool.getResource();
				 return jedis.set(key, value);
			 }finally{
				 jedisPool.returnResourceObject(jedis);
			 }
		 }
		 
		 /**
		  * 从指定位置开始插入数据，插入的数据会覆盖指定位置以后的数据<br/>
		  * 例:String str1="123456789";<br/>
		  * 对str1操作后setRange(key,4,0000)，str1="123400009";
		  * @param String key
		  * @param long offset
		  * @param String value
		  * @return long value的长度
		  * */
		 public long setRange(String key,long offset,String value){
			 Jedis jedis = null;
			 try{
				 jedis = jedisPool.getResource();
				 return jedis.setrange(key, offset, value);
			 }finally{
				 jedisPool.returnResourceObject(jedis);
			 }
		 }
		 /**
		  * 在指定的key中追加value
		  * @param String key
		  * @param String value
		  * @return long 追加后value的长度
		  * **/
		 public long append(String key,String value){
			 Jedis jedis = null;
			 try{
				 jedis = jedisPool.getResource();
				 return jedis.append(key, value);
			 }finally{
				 jedisPool.returnResourceObject(jedis);
			 }
		 }
		 
		 /**
		  * 将key对应的value减去指定的值，只有value可以转为数字时该方法才可用
		  * @param String key
		  * @param long number 要减去的值
		  * @return long 减指定值后的值
		  * */
		 public long decrBy(String key,long number){
			 Jedis jedis = null;
			 try{
				 jedis = jedisPool.getResource();
				 return jedis.decrBy(key,number);
			 }finally{
				 jedisPool.returnResourceObject(jedis);
			 }
		 }
		 
		 /**
		  * <b>可以作为获取唯一id的方法</b><br/>
		  * 将key对应的value加上指定的值，只有value可以转为数字时该方法才可用
		  * @param String key
		  * @param long number 要增加的值
		  * @return long 相加后的值
		  * */
		 public long incrBy(String key,long number){
			 Jedis jedis = null;
			 try{
				 jedis = jedisPool.getResource();
				 return jedis.incrBy(key,number);
			 }finally{
				 jedisPool.returnResourceObject(jedis);
			 }
		 }
		 
		 /**
		  * 对指定key对应的value进行截取
		  * @param String key
		  * @param long startOffset 开始位置(包含)
		  * @param long endOffset 结束位置(包含)
		  * @return String 截取的值
		  * */
		 public String getrange(String key,long startOffset,long endOffset){
			 Jedis jedis = null;
			 try{
				 jedis = jedisPool.getResource();
				 return jedis.getrange(key, startOffset, endOffset);
			 }finally{
				 jedisPool.returnResourceObject(jedis);
			 }
		 }
		 
		 /**
		  * 获取并设置指定key对应的value<br/>
		  * 如果key存在返回之前的value,否则返回null
		  * @param String key
		  * @param String value
		  * @return String 原始value或null
		  * */
		 public String getSet(String key,String value){
			 Jedis jedis = null;
			 try{
				 jedis = jedisPool.getResource();
				 return jedis.getSet(key, value);
			 }finally{
				 jedisPool.returnResourceObject(jedis);
			 }
		 }
	 }
	 
	 //TODO
	public  class Lists{
		 /**
		  * List长度
		  * @param String key
		  * @return 长度
		  * */
		 public long llen(String key){
			 return llen(SafeEncoder.encode(key));
		 }
		 
		 /**
		  * List长度
		  * @param byte[] key
		  * @return 长度
		  * */
		 public long llen(byte[] key){
			 Jedis jedis = null;
			 try{
				 jedis = jedisPool.getResource();
				 return jedis.llen(key);
			 }finally{
				 jedisPool.returnResourceObject(jedis);
			 }
		 }
		 
		 /**
		  * 覆盖操作,将覆盖List中指定位置的值
		  * @param byte[] key
		  * @param int index 位置
		  * @param byte[] value 值
		  * @return 状态码
		  * */
		 public String lset(byte[] key,int index,byte[] value){
			 Jedis jedis = null;
			 try{
				 jedis = jedisPool.getResource();
				 return jedis.lset(key, index, value);
			 }finally{
				 jedisPool.returnResourceObject(jedis);
			 }
		 }
		 
		 /**
		  * 覆盖操作,将覆盖List中指定位置的值
		  * @param key
		  * @param int index 位置
		  * @param String value 值
		  * @return 状态码
		  * */
		 public String lset(String key,int index,String value){
			 return lset(SafeEncoder.encode(key),index,SafeEncoder.encode(value));
		 }
		 
		 /**
		  * 在value的相对位置插入记录
		  * @param key
		  * @param LIST_POSITION  前面插入或后面插入
		  * @param String pivot 相对位置的内容
		  * @param String value 插入的内容
		  * @return 记录总数
		  * */
		 public long linsert(String key,LIST_POSITION where,String pivot,String value){
			 return linsert(SafeEncoder.encode(key),where,SafeEncoder.encode(pivot),SafeEncoder.encode(value));
		 }
		 
		 /**
		  * 在指定位置插入记录
		  * @param String key
		  * @param LIST_POSITION  前面插入或后面插入
		  * @param byte[] pivot 相对位置的内容
		  * @param byte[] value 插入的内容
		  * @return 记录总数
		  * */
		 public long linsert(byte[] key,LIST_POSITION where,byte[] pivot,byte[] value){
			 Jedis jedis = null;
			 try{
				 jedis = jedisPool.getResource();
				 return jedis.linsert(key, where, pivot, value);
			 }finally{
				 jedisPool.returnResourceObject(jedis);
			 }
		 }
		 
		 /**
		  * 获取List中指定位置的值
		  * @param String key
		  * @param int index 位置
		  * @return 值
		  * **/
		 public String lindex(String key,int index){
			 return SafeEncoder.encode(lindex(SafeEncoder.encode(key),index));
		 }
		 
		 /**
		  * 获取List中指定位置的值
		  * @param byte[] key
		  * @param int index 位置
		  * @return 值
		  * **/
		 public byte[] lindex(byte[] key,int index){
			 Jedis jedis = null;
			 try{
				 jedis = jedisPool.getResource();
				 return jedis.lindex(key, index);
			 }finally{
				 jedisPool.returnResourceObject(jedis);
			 }
		 }
		 
		 /**
		  * 将List中的第一条记录移出List
		  * @param String key
		  * @return 移出的记录
		  * */
		 public String lpop(String key){
//			 System.out.println(key);
			 byte[] b = lpop(SafeEncoder.encode(key));
			 if(b != null)
				 return SafeEncoder.encode(b);
			 else
				 return null;
		 }
		 /**
		  * 将List中的第一条记录移出List
		  * @param byte[] key
		  * @return 移出的记录
		  * */
		 public byte[] lpop(byte[] key){
			 Jedis jedis = null;
			 try{
				 jedis = jedisPool.getResource();
				 return jedis.lpop(key);
			 }finally{
				 jedisPool.returnResourceObject(jedis);
			 }
		 }
		 /**
		  * 将List中最后第一条记录移出List
		  * @param String key
		  * @return 移出的记录
		  * */
		 public String rpop(String key){
			 Jedis jedis = null;
			 try{
				 jedis = jedisPool.getResource();
				 return jedis.rpop(key);
			 }finally{
				 jedisPool.returnResourceObject(jedis);
			 }
		 }
		 /**
		  * 将List中最后第一条记录移出List
		  * @param byte[] key
		  * @return 移出的记录
		  * */
		 public byte[] rpop(byte[] key){
			 Jedis jedis = null;
			 try{
				 jedis = jedisPool.getResource();
				 return jedis.rpop(key);
			 }finally{
				 jedisPool.returnResourceObject(jedis);
			 }
		 }
		 
		 
		 /**
		  * 向List尾部追加记录
		  * @param String key
		  * @param String value
		  * @return 记录总数
		  * */
		 public long lpush(String key,String value){
			 return lpush(SafeEncoder.encode(key),SafeEncoder.encode(value));
		 }
		 
		 /**
		  * 向List头部追加记录
		  * @param String key
		  * @param String value
		  * @return 记录总数
		  * */
		 public long rpush(byte[] key,byte[] value){
			 Jedis jedis = null;
			 try{
				 jedis = jedisPool.getResource();
				 return jedis.rpush(key, value);
			 }finally{
				 jedisPool.returnResourceObject(jedis);
			 }
		 }
		 
		 /**
		  * 向List头部追加记录
		  * @param byte[] key
		  * @param byte[] value
		  * @return 记录总数
		  * */
		 public long rpush(String key,String value){
			 Jedis jedis = null;
			 try{
				 jedis = jedisPool.getResource();
				 return jedis.rpush(key, value);
			 }finally{
				 jedisPool.returnResourceObject(jedis);
			 }
		 }
		 /**
		  * 向List中追加记录
		  * @param byte[] key
		  * @param byte[] value
		  * @return 记录总数
		  * */
		 public long lpush(byte[] key,byte[] value){
			 Jedis jedis = null;
			 try{
				 jedis = jedisPool.getResource();
				 return jedis.lpush(key, value);
			 }finally{
				 jedisPool.returnResourceObject(jedis);
			 }
		 }
		 
		 /**
		  * 获取指定范围的记录，可以做为分页使用
		  * @param String key
		  * @param long start
		  * @param long end
		  * @return List
		  * */
		 public List<String> lrange(String key,long start,long end){
			 Jedis jedis = null;
			 try{
				 jedis = jedisPool.getResource();
				 return jedis.lrange(key, start,end);
			 }finally{
				 jedisPool.returnResourceObject(jedis);
			 }
		 }
		 /**
		  * 获取指定范围的记录，可以做为分页使用
		  * @param byte[] key
		  * @param int start
		  * @param int end 如果为负数，则尾部开始计算
		  * @return List
		  * */
		 public List<byte[]> lrange(byte[] key,int start,int end){			 
			 Jedis jedis = null;
			 try{
				 jedis = jedisPool.getResource();
				 return jedis.lrange(key, start,end);
			 }finally{
				 jedisPool.returnResourceObject(jedis);
			 }
		 }
		 
		 /**
		  * 删除List中c条记录，被删除的记录值为value
		  * @param byte[] key
		  * @param int c 要删除的数量，如果为负数则从List的尾部检查并删除符合的记录
		  * @param byte[] value 要匹配的值
		  * @return 删除后的List中的记录数
		  * */
		 public long lrem(byte[] key,int c,byte[] value){
			 Jedis jedis = null;
			 try{
				 jedis = jedisPool.getResource();
				 return jedis.lrem(key, c,value);
			 }finally{
				 jedisPool.returnResourceObject(jedis);
			 }
		 }
		 
		 /**
		  * 删除List中c条记录，被删除的记录值为value
		  * @param String key
		  * @param int c 要删除的数量，如果为负数则从List的尾部检查并删除符合的记录
		  * @param String value 要匹配的值
		  * @return 删除后的List中的记录数
		  * */
		 public long lrem(String key,int c,String value){
			 return lrem(SafeEncoder.encode(key),c,SafeEncoder.encode(value));
		 }
		 
		 /**
		  * 算是删除吧，只保留start与end之间的记录
		  * @param byte[] key
		  * @param int start 记录的开始位置(0表示第一条记录)
		  * @param int end 记录的结束位置（如果为-1则表示最后一个，-2，-3以此类推）
		  * @return 执行状态码
		  * */
		 public String ltrim(byte[] key,int start,int end){
			 Jedis jedis = null;
			 try{
				 jedis = jedisPool.getResource();
				 return jedis.ltrim(key, start, end);
			 }finally{
				 jedisPool.returnResourceObject(jedis);
			 }
		 }
		 /**
		  * 算是删除吧，只保留start与end之间的记录
		  * @param String key
		  * @param int start 记录的开始位置(0表示第一条记录)
		  * @param int end 记录的结束位置（如果为-1则表示最后一个，-2，-3以此类推）
		  * @return 执行状态码
		  * */
		 public String ltrim(String key,int start,int end){
			 return ltrim(SafeEncoder.encode(key),start,end);
		 }
	  }
}

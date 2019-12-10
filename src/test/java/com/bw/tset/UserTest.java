package com.bw.tset;

import java.util.Random;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.bw.entity.User;
import com.bw.utils.TimeRandomUtil;
import com.bw.utils.UserRandomUtils;

@SuppressWarnings("all")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:applicationContext-redis.xml")
public class UserTest {
	
	@Resource
	private RedisTemplate redisTemplate;
	
	@Test
	public void userTest() {
		//获取当前时间
		long time1 = System.currentTimeMillis();
		//String类型
//		ValueOperations ops = redisTemplate.opsForValue();
		//hash类型
		BoundHashOperations hashOps = redisTemplate.boundHashOps("hash_user");
		for (int i = 1; i <=100000; i++) {
			//打印user对象里的属性
			User user = new User();
			user.setId(i);
			user.setName(UserRandomUtils.getChineseName());
			user.setSex(getSex());
			user.setPhone(getPhone());
			user.setEmail(UserRandomUtils.getEmail());
			user.setBiethday(TimeRandomUtil.randomDate("1996-05-06 00:00:00", "2001-01-01 00:00:00"));
//			System.out.println(user);
			//存入redis中
//			ops.set(i+"", user);
			hashOps.put(i+"", user.toString());
			
		}
		long time2 = System.currentTimeMillis();
		System.out.println("序列化方式:hash,耗时:"+(time2-time1));
	}

	public static String getSex() {
		// TODO Auto-generated method stub
		
		return new Random().nextBoolean()?"男":"女";
	}
	public static String getPhone() {
		// TODO Auto-generated method stub
		String phone="";
		for (int i = 0; i < 9; i++) {
			phone+=new Random().nextInt(10);
		}
		return "13"+phone;
	}
	
	
}

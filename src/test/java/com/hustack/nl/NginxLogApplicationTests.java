package com.hustack.nl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.hustack.nl.util.RedisUtils;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NginxLogApplicationTests {
	
	@Autowired
	private RedisUtils redisUtils;
	
	@Test
	public void redis() {
		// redisUtils.set("webhook", "https://oapi.dingtalk.com/robot/send?access_token=b271dcd03cceddca8509a3d0efd29b7a88bf86e05f63daa06dd35feb44a24b07");
	}

}

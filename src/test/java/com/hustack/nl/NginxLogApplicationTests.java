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
		redisUtils.set("test", new com.hustack.nl.configure.Test("a钉钉b"));
	}

}

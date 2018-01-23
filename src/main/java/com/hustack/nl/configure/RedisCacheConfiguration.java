package com.hustack.nl.configure;

import java.time.Duration;
import java.util.Collections;
import java.util.Map;

import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
	import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
@EnableCaching
@PropertySource("classpath:redis.properties")
@SuppressWarnings("rawtypes")
public class RedisCacheConfiguration extends CachingConfigurerSupport {

	private final long DEFAULT_EXPIRATION = 60 * 60 * 24 * 32;

	@Bean
	public KeyGenerator keyGenerator() {
		return (target, method, objects) -> {
			StringBuilder sb = new StringBuilder();
			sb.append(target.getClass().getName());
			sb.append(":" + method.getName());
			for (Object obj : objects) {
				sb.append("_").append(obj.toString());
			}
			return sb.toString();
		};
	}

	private org.springframework.data.redis.cache.RedisCacheConfiguration defaultCacheConfig() {
		return org.springframework.data.redis.cache.RedisCacheConfiguration.defaultCacheConfig()
				.entryTtl(Duration.ofSeconds(DEFAULT_EXPIRATION)).disableCachingNullValues();
	}

	@Bean
	public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory) {
		Map<String, org.springframework.data.redis.cache.RedisCacheConfiguration> cacheConfigurations = Collections
				.singletonMap("predefined", defaultCacheConfig().disableCachingNullValues());
		
		return RedisCacheManager.builder(connectionFactory).cacheDefaults(defaultCacheConfig())
				.withInitialCacheConfigurations(cacheConfigurations).transactionAware().build();
	}

	@Bean
	public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory factory) {
		StringRedisTemplate template = new StringRedisTemplate(factory);

		Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
		ObjectMapper om = new ObjectMapper();
		om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
		om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
		om.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		jackson2JsonRedisSerializer.setObjectMapper(om);

		template.setValueSerializer(jackson2JsonRedisSerializer);
		template.afterPropertiesSet();
		return template;
	}
}
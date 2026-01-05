package agentmanager.common.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisService {

	private final Logger logger = LogManager.getLogger(RedisService.class);

	private final RedisTemplate<String, Object> redisTemplate;

	public RedisService(RedisTemplate<String, Object> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	public void setValue(String key, Object value) {
		redisTemplate.opsForValue().set(key, value);
		logger.info("Value: {} set for key: {}", value, key);
	}

	public Object getValue(String key) {
		Object object = redisTemplate.opsForValue().get(key);
		logger.info("Retrieved object: {} for key: {}", object, key);
		return object;
	}

}

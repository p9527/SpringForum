package jqhk.ssm.service;

import com.alibaba.fastjson.JSON;
import jqhk.ssm.Utility;
import jqhk.ssm.model.TopicModel;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.listener.Topic;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TopicServiceTest {
	@Autowired()
	private TopicService topicService;

	@Autowired()
	private RedisTemplate<String, String> redisTemplate;

	private final static String topicHashKey = "topics";

	@Test
	public void test() {
		Utility.log("%s", topicService.allNotTop("all", 1));
	}

	@Test
	public void testRedisList() {
		String test = "redisListTest";
		redisTemplate.opsForList().rightPush(test, "right1");
		redisTemplate.opsForList().leftPush(test, "left1");
		redisTemplate.opsForList().remove(test, 2, "right1");
		Utility.log("list size %s", redisTemplate.opsForList().size(test));
		Utility.log("list e %s", redisTemplate.opsForList().range(test, 0, -1));
	}

	@Test
	public void testRedisSet() {
		String test = "redisSetTest";
		SetOperations<String, String> setOperations = redisTemplate.opsForSet();
		setOperations.add(test, "java");
		setOperations.add(test, "python");
		setOperations.add(test, "c++");
		setOperations.add(test, "php");
		setOperations.add(test, "java", "java");
		Utility.log("set e %s", setOperations.members(test));
	}

	@Test
	public void testRedisZSet() {
		String test = "redisZSetTest";
		ZSetOperations<String, String> zSetOperations = redisTemplate.opsForZSet();
		zSetOperations.add(test, "java", 0.01);
		zSetOperations.add(test, "c++", 0.02);
		zSetOperations.add(test, "php", 0.03);
		zSetOperations.incrementScore(test, "java", 0.02);
		Utility.log("java source %s", zSetOperations.score(test, "java"));
		zSetOperations.incrementScore(test, "test", 0.02);
		Utility.log("zset e %s", zSetOperations.range(test, 0, -1));
		Utility.log("zset e %s", zSetOperations.reverseRange(test, 0, -1));
	}

	@Test
	public void testRedisHah2() {
		TopicModel t = topicService.selectOneWithComment(1);
		String v = JSON.toJSONString(t);
		redisTemplate.opsForHash().put(topicHashKey, t.getId().toString(), v);
		String vj = (String) redisTemplate.opsForHash().get(topicHashKey, "1");
		TopicModel tj = JSON.parseObject(vj, TopicModel.class);
		Utility.log("%s", tj.getComments().get(1));
	}
}

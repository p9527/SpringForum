package jqhk.ssm.service;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import jqhk.ssm.Utility;
import jqhk.ssm.mapper.TopicMapper;
import jqhk.ssm.model.CommentModel;
import jqhk.ssm.model.TopicModel;
import jqhk.ssm.mapper.CommentMapper;
import jqhk.ssm.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class TopicService {
    private TopicMapper mapper;
    private UserMapper userMapper;
    private CommentMapper commentMapper;
    private RedisTemplate<String, String> redisTemplate;
    private final String topTopicsRedisName = "topTopics";
    private final String goodTopicsRedisName = "goodTopics";

    public TopicService(TopicMapper topicMapper, UserMapper userMapper,
                        CommentMapper commentMapper,
                        RedisTemplate<String, String> redisTemplate) {
        this.mapper = topicMapper;
        this.userMapper = userMapper;
        this.commentMapper = commentMapper;
        this.redisTemplate = redisTemplate;
    }

    public TopicModel add(String title, String content, Integer userId, String tab) {
        TopicModel m = new TopicModel();
        m.setTitle(title);
        m.setContent(content);
        m.setUserId(userId);
        Long time = System.currentTimeMillis() / 1000L;
        m.setCreatedTime(time);
        m.setUpdatedTime(time);
        m.setTab(tab);

        // Utility.log("topic add user: %s", m);
        mapper.insertTopic(m);
        return m;
    }

    // select
    public TopicModel selectOneWithComment(Integer id) {
        TopicModel topic = mapper.selectOneWithComment(id);
        return topic;
    }

    public List<TopicModel> topicsByUserId(Integer userId, Integer count) {
        return mapper.selectAllTopicByUserId(userId, count);
    }

    public List<TopicModel> allGood(Integer first) {
        List<TopicModel> goodTopics;
        if (first.equals(0)) {
            var goodTopicsValue = redisTemplate.opsForValue().get(goodTopicsRedisName);
            // String value = localredis.get("testkey");
            if (goodTopicsValue == null || goodTopicsValue.equals("")) {
                goodTopics = mapper.selectGoodTopic(first, 10);
                String topTopicsString = JSON.toJSONString(goodTopics);
                redisTemplate.opsForValue().set(goodTopicsRedisName, topTopicsString);
            } else {
                JSONArray jsonString = JSON.parseArray(goodTopicsValue);
                goodTopics = JSONObject.parseArray(jsonString.toJSONString(), TopicModel.class);
                Utility.log("使用 Redis 缓存得到 goodTopics %s", goodTopics);
            }
        } else {
            goodTopics =  mapper.selectGoodTopic(first, 10);
        }
        Utility.log("allGood goodTopics %s", goodTopics);
        return goodTopics;
    }

    public List<TopicModel> allNotTop(String tab, Integer page) {
        Integer first = (page - 1) * 10;
        if (tab.equals("all")) {
            return mapper.selectAllNotTopTopic(first, 10);
        } else if (tab.equals("good")) {
            return allGood(first);
        } else {
            return mapper.selectNotTopTopicByTab(tab, first, 10);
        }
    }

    public List<TopicModel> allTopNotTab() {
        // redis 缓存
        var topTopicsValue = redisTemplate.opsForValue().get(topTopicsRedisName);
        // Utility.log("redis value topTopicsValue %s", topTopicsValue);
        // String value = localredis.get("testkey");
        if (topTopicsValue == null || topTopicsValue.equals("")) {
            List<TopicModel> topTopics = mapper.selectTopTopic();
            String topTopicsString = JSON.toJSONString(topTopics);
            redisTemplate.opsForValue().set(topTopicsRedisName, topTopicsString);
            return topTopics;
        } else {
            JSONArray jsonString = JSON.parseArray(topTopicsValue);
            List<TopicModel> topTopics = JSONObject.parseArray(jsonString.toJSONString(), TopicModel.class);
            Utility.log("使用 Redis 缓存得到 topTopics %s", topTopics);
            return topTopics;
        }
    }

    public List<TopicModel> allTop(String tab) {
        if (tab.equals("all")) {
            return allTopNotTab();
        } else if (tab.equals("good")) {
            List<TopicModel> t = new ArrayList<>();
            return t;
        } else {
            return mapper.selectTopTopicByTab(tab);
        }
    }

    public List<TopicModel> collectionTopics(Integer userId) {
        return mapper.selectCollectionTopic(userId);
    }

    public List<TopicModel> noReplyTopics() {
        return mapper.selectNoReplyTopic();
    }

    public List<TopicModel> currentAttendTopics(Integer userId, Integer count) {
        List<TopicModel> topics = this.mapper.selectTopicInCommentByUserId(userId, count);
        return topics;
    }

    // update
    public void clearTopRedis() {
        redisTemplate.opsForValue().set(topTopicsRedisName, "");
    }

    public void clearGoodRedis() {
        redisTemplate.opsForValue().set(goodTopicsRedisName, "");
    }

    public void good(Integer id) {
        TopicModel topic = this.mapper.selectOne(id);
        boolean good = topic.getGood();
        topic.setGood(!good);
        clearGoodRedis();
        this.mapper.setGood(topic);
    }

    public void top(Integer id) {
        TopicModel topic = this.mapper.selectOne(id);
        boolean top = topic.getTop();
        topic.setTop(!top);

        clearTopRedis();
        this.mapper.setTop(topic);
    }

    public void collection(Integer userId, Integer topicId) {
        Long time = System.currentTimeMillis() / 1000L;
        this.mapper.collection(userId, topicId, time);
    }

    public boolean collected(Integer userId, Integer topicId) {
        Integer id = this.mapper.collected(userId, topicId);
        Utility.log("topicService collected topicId %s", id);
        return id != null;
    }

    public void cancelCollection(Integer userId, Integer topicId) {
        this.mapper.cancelCollection(userId, topicId);
    }

    public void updateRelyCount(Integer topicId) {
        this.mapper.updateReplyCount(topicId);
        clearTopRedis();
        clearGoodRedis();
    }

    public void updateViewCount(Integer topicId) {
        this.mapper.updateViewCount(topicId);
        clearTopRedis();
        clearGoodRedis();
    }

    public void update(Integer id, String title, String content, String tab) {
        TopicModel m = new TopicModel();
        m.setId(id);
        m.setContent(content);
        m.setTitle(title);
        m.setTab(tab);
        Long time = System.currentTimeMillis() / 1000L;
        m.setUpdatedTime(time);
        mapper.updateTopic(m);
    }

    public void deleteById(Integer id) {
        mapper.deleteTopic(id);
        clearTopRedis();
        clearGoodRedis();
    }

    public static void main(String[] args) {
    }
}

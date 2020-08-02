package jqhk.ssm.service;


import jqhk.ssm.mapper.TopicMapper;
import jqhk.ssm.model.CommentModel;
import jqhk.ssm.mapper.CommentMapper;
import jqhk.ssm.mapper.UserMapper;
import jqhk.ssm.model.TopicModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
    private CommentMapper mapper;
    private UserMapper userMapper;
    private TopicMapper topicMapper;

    public CommentService(CommentMapper commentMapper, UserMapper userMapper, TopicMapper topicMapper) {
        this.mapper = commentMapper;
        this.userMapper = userMapper;
        this.topicMapper = topicMapper;
    }

    public CommentModel add(String content, Integer topicId, Integer userId) {

        CommentModel m = new CommentModel();
        m.setTopicId(topicId);
        m.setContent(content);
        m.setUserId(userId);
        Long time = System.currentTimeMillis() / 1000L;
        m.setCreatedTime(time);
        m.setUpdatedTime(time);
        TopicModel t = this.topicMapper.selectOneWithComment(topicId);
        Integer floor = t.getReplyCount() + 1;
        m.setFloor(floor);

        // Utility.log("topic add user: %s", m);
        mapper.insertComment(m);
        return m;
    }


    public void update(Integer id, String content) {
        CommentModel m = new CommentModel();
        m.setId(id);
        m.setContent(content);
        mapper.updateComment(m);
    }


    public void deleteById(Integer id) {
        mapper.deleteComment(id);
    }


    public  CommentModel findById(Integer id) {
        return mapper.selectComment(id);
    }


    public  List<CommentModel> all(Integer topicId) {
        List<CommentModel> comments = mapper.selectAllComment(topicId);
        return comments;
    }
}

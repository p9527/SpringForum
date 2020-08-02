package jqhk.ssm.mapper;

import jqhk.ssm.model.TopicModel;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
interface TopicSelectMapper {
    List<TopicModel> selectAllTopic();

    List<TopicModel> selectAllNotTopTopic(Integer first, Integer count);

    List<TopicModel> selectTopicByTab(String tab);

    List<TopicModel> selectNotTopTopicByTab(String tab, Integer first, Integer count);

    List<TopicModel> selectGoodTopic(Integer first, Integer count);

    List<TopicModel> selectTopTopic();

    List<TopicModel> selectNoReplyTopic();

    List<TopicModel> selectTopTopicByTab(String tab);

    List<TopicModel> selectAllTopicByUserId(Integer userId, Integer count);

    List<TopicModel> selectCollectionTopic(Integer userId);

    List<TopicModel> selectTopicInCommentByUserId(Integer userId, Integer count);

    TopicModel selectOneWithComment(int id);

    TopicModel selectOne(int id);

    Integer collected(Integer userId, Integer topicId);
}
// 这个是 spring 用来在 controller 进行依赖注入的。
@Repository
// 这个是 mybaits spring boot 用来自动跟 xml 联系起来，并注入到 spring 的 session 里面。
@Mapper
public interface TopicMapper extends TopicSelectMapper {
    void insertTopic(TopicModel topic);

    void collection(Integer userId, Integer topicId, Long time);

    void cancelCollection(Integer userId, Integer topicId);

    void updateTopic(TopicModel topic);

    void updateReplyCount(Integer topicId);

    void updateViewCount(Integer topicId);

    void setTop(TopicModel topic);

    void setGood(TopicModel topic);

    void deleteTopic(int id);
}

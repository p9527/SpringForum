package jqhk.ssm.mapper;

import jqhk.ssm.model.CommentModel;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

// 这个是 spring 用来在 controller 进行依赖注入的。
@Repository
// 这个是 mybaits spring boot 用来自动跟 xml 联系起来，并注入到 spring 的 session 里面。
@Mapper
public interface CommentMapper {
    void insertComment(CommentModel Comment);

    List<CommentModel> selectAllComment(int topicId);

    CommentModel selectComment(int id);

    void updateComment(CommentModel Comment);

    void deleteComment(int id);
}

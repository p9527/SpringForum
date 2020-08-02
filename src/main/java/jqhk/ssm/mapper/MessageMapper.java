package jqhk.ssm.mapper;

import jqhk.ssm.model.MessageModel;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

// 这个是 spring 用来在 controller 进行依赖注入的。
@Repository
// 这个是 mybaits spring boot 用来自动跟 xml 联系起来，并注入到 spring 的 session 里面。
@Mapper
public interface MessageMapper {
    void insertMessage(MessageModel message);

    List<MessageModel> selectReadMessage(Integer readerId);

    List<MessageModel> selectNotReadMessage(Integer readerId);

    void readMessage(MessageModel message);
}

package jqhk.ssm.mapper;

import jqhk.ssm.model.UserModel;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

// 这个是 spring 用来在 controller 进行依赖注入的。
@Repository
// 这个是 mybaits spring boot 用来自动跟 xml 联系起来，并注入到 spring 的 session 里面。
@Mapper
public interface UserMapper {
    void insertUser(UserModel user);

    List<UserModel> selectAllUser();

    List<UserModel> sourceOrderList(Integer count);

    List<UserModel> selectSourceTop100();

    UserModel selectUser(int id);

    UserModel selectOnerByUsername(String username);

    void updateUser(UserModel user);

    void deleteUser(int id);

    void sourceAdd(String userName, Integer numOfSource);

    void updateSetting(UserModel user);

    void changeAvatar(String avatar, int userId);
}

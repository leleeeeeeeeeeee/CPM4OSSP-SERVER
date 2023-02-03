package mpms.test.dao;

import mpms.test.pojo.MyUser;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserMapper {

	int addUser(MyUser user);

	int deleteUserById(int id);

	int updateUser(MyUser user);

	MyUser queryUserById(int id);

	List<MyUser> queryAllUser();

}

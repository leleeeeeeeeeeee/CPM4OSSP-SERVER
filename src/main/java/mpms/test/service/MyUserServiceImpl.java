package io.jpom.test.service;


import io.jpom.test.dao.UserMapper;
import io.jpom.test.pojo.MyUser;

import java.util.List;

public class MyUserServiceImpl implements MyUserService {

    private UserMapper userMapper;
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }


    public int addUser(MyUser User) {
        return userMapper.addUser(User);
    }


    public int deleteUserById(int id) {
        return userMapper.deleteUserById(id);
    }


    public int updateUser(MyUser User) {
        return userMapper.updateUser(User);
    }


    public MyUser queryUserById(int id) {
        return userMapper.queryUserById(id);
    }


    public List<MyUser> queryAllUser() {
        return userMapper.queryAllUser();
    }



}

package io.jpom.test.service;

import io.jpom.test.pojo.MyUser;

import java.util.List;

public interface MyUserService {
    int addUser(MyUser user);

    int deleteUserById(int id);

    int updateUser(MyUser user);

    MyUser queryUserById(int id);

    List<MyUser> queryAllUser();
}

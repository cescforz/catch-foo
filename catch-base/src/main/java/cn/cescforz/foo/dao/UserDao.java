package cn.cescforz.foo.dao;

import cn.cescforz.foo.bean.domain.User;

import java.util.List;

public interface UserDao {

    int insert(User user);

    List<User> selectUsers();
}

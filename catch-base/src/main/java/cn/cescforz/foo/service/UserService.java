package cn.cescforz.foo.service;

import cn.cescforz.foo.bean.domain.User;
import cn.cescforz.foo.exception.ServerTransException;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;


public interface UserService {

    int addUser(User user);

    List<User> findAllUser();

    int test()throws ServerTransException;


    @Cacheable(key="'user_'+#id",value="'user'+#id")
    User getUser(Integer id);

    @CacheEvict(key="'user_'+#id", value="users", condition="#id!=1")
    void deleteUser(Integer id);
}

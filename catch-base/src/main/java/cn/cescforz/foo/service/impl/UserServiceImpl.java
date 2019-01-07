package cn.cescforz.foo.service.impl;

import cn.cescforz.foo.bean.domain.User;
import cn.cescforz.foo.dao.UserDao;
import cn.cescforz.foo.enumeration.ResponseCode;
import cn.cescforz.foo.exception.ServerTransException;
import cn.cescforz.foo.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service(value = "userService")
public class UserServiceImpl implements UserService {

    @Resource
    private UserDao userDao;

    @Override
    public int addUser(User user) {
        return userDao.insert(user);
    }

    @Override
    public List<User> findAllUser() {
        return userDao.selectUsers();
    }

    @Override
    public int test() throws ServerTransException {
        throw new ServerTransException(ResponseCode.ACCOUNT_DISABLED);
    }

    @Override
    public User getUser(Integer id) {
        System.out.println(id+"进入实现类获取数据！");
        User user = new User();
        user.setUserId(id);
        user.setUserName("香菇,难受");
        user.setPhone("18010**9382");
        return user;
    }

    @Override
    public void deleteUser(Integer id) {
        System.out.println(id+"进入实现类删除数据！");
    }
}

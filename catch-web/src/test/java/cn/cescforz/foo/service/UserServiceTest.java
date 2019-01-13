package cn.cescforz.foo.service;

import cn.cescforz.foo.ApplicationTest;
import cn.cescforz.foo.bean.domain.Order;
import cn.cescforz.foo.bean.domain.User;
import cn.cescforz.foo.bean.model.BaseUUIDGenModel;
import cn.cescforz.foo.component.redis.RedisHanlder;
import cn.cescforz.foo.dao.OrderDao;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.annotation.Rollback;

import java.util.Date;
import java.util.List;


/**
 * <p>©2019 Cesc. All Rights Reserved.</p>
 * <p>Description: </p>
 *
 * @author cesc
 * @version v1.0
 * @date Create in 2019-01-08 15:44
 */
@Slf4j
@Rollback(value = false)
public class UserServiceTest extends ApplicationTest {


    @Autowired
    private UserService userService;

    @Autowired
    private RedisHanlder<String, User> redisHanlder;



    @Autowired
    private OrderDao orderDao;


    @Test
    public void test(){

    }
}

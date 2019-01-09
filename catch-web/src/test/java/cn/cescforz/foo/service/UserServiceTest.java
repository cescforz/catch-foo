package cn.cescforz.foo.service;

import cn.cescforz.foo.ApplicationTest;
import cn.cescforz.foo.bean.domain.User;
import cn.cescforz.foo.component.redis.RedisHanlder;
import cn.cescforz.foo.util.RedisKeyUtils;
import com.alibaba.fastjson.parser.ParserConfig;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Var;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.concurrent.TimeUnit;


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
    private KeyGenerator KeyGenerator;

    @Autowired
    private UserService userService;

    @Autowired
    private RedisHanlder<String, User> redisHanlder;

    @Test
    public void test(){



    }
}

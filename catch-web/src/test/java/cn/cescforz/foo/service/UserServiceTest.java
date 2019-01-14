package cn.cescforz.foo.service;

import cn.cescforz.foo.ApplicationTest;
import cn.cescforz.foo.bean.domain.User;
import cn.cescforz.foo.component.mail.MailService;
import cn.cescforz.foo.component.redis.RedisHanlder;
import cn.cescforz.foo.dao.ErrorLogDao;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;


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
    private MailService mailService;

    @Autowired
    private TemplateEngine templateEngine;//注入模板引擎

    @Autowired
    private ErrorLogDao errorLogDao;


    @Test
    public void test() throws Exception{
        //mailService.sendSimpleMail("412410295@qq.com","注册验证","这是一封普通的SpringBoot测试邮件");
        Context context = new Context();
        context.setVariable("project", "catch-foo");
        context.setVariable("author", "cescforz");
        context.setVariable("url", "https://github.com/cescforz");
        String emailContent = templateEngine.process("mail", context);

        mailService.sendHtmlMail("cescforz@foxmail.com", "工作进度", emailContent);
    }
}

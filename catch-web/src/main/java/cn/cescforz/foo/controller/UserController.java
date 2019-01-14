package cn.cescforz.foo.controller;

import cn.cescforz.foo.annotation.ApiVersion;
import cn.cescforz.foo.annotation.security.encrypt.EncryptBody;
import cn.cescforz.foo.bean.domain.User;
import cn.cescforz.foo.dao.ErrorLogDao;
import cn.cescforz.foo.enumeration.EncryptBodyMethod;
import cn.cescforz.foo.service.UserService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Slf4j
@Controller
@RequestMapping(value = "/user")
@Api(value = "UserController|一个用来测试swagger注解的控制器")
public class UserController {


    /**
     * Java变量的初始化顺序为：静态变量或静态语句块–>实例变量或初始化语句块–>构造方法–>@Autowired
     * 构造器注入更适合强制性的注入旨在不变性，Setter注入更适合可变性的注入
     */

    private UserService userService;

    private ErrorLogDao errorLogDao;

    @ResponseBody
    @GetMapping("/all")
    public Object findAllUser(){
        List<User> list = userService.findAllUserName();
        return list;
    }

    @ResponseBody
    @GetMapping("/add")
    public Object addUser(){
        User user = new User();
        user.setUserName("詹姆斯");
        user.setPassword("123");
        user.setPhone("18010689***");
        userService.save(user);
        Page<User> page = new Page<>(0,2);
        return userService.selectPageVo(page,"詹姆斯");
    }

    @RequestMapping("/{id}")
    @ResponseBody
    public Object test(@PathVariable Integer id){
        return 1/id;
    }


    @GetMapping("/{version}/foo")
    @ApiVersion(2)
    @ResponseBody
    @EncryptBody(value = EncryptBodyMethod.AES)
    public Object test(@PathVariable(value = "version") String version){
        return "hello world";
    }

    @ResponseBody
    @GetMapping("/foo")
    public Object test(){
        Query query = Query.query(Criteria.where("requestParam").is("0"));
        return errorLogDao.find(query);
    }

    @Autowired
    public UserController(UserService userService, ErrorLogDao errorLogDao) {
        this.userService = userService;
        this.errorLogDao = errorLogDao;
    }


}

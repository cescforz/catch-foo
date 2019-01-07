package cn.cescforz.foo.controller;

import cn.cescforz.foo.annotation.ApiVersion;
import cn.cescforz.foo.annotation.security.encrypt.EncryptBody;
import cn.cescforz.foo.bean.domain.User;
import cn.cescforz.foo.enumeration.EncryptBodyMethod;
import cn.cescforz.foo.service.UserService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/user")
@Api(value = "UserController|一个用来测试swagger注解的控制器")
public class UserController {


    /**
     * Java变量的初始化顺序为：静态变量或静态语句块–>实例变量或初始化语句块–>构造方法–>@Autowired
     * 构造器注入更适合强制性的注入旨在不变性，Setter注入更适合可变性的注入
     */

    private final UserService userService;



    //｛@GetMapping、@PostMapping、@PutMapping、@DeleteMapping、@PatchMapping｝

    @ResponseBody
    @PostMapping("/add")
    @ApiOperation(value = "添加客户信息value位置",notes = "添加客户信息notes位置")
    @ApiImplicitParam(paramType="query", name = "user", value = "用户信息", required = true, dataType = "User")
    public int addUser(@RequestParam User user){
        return userService.addUser(user);
    }


    @ResponseBody
    @ApiResponses(value = {@ApiResponse(code=0,message = "success")})
    @ApiVersion(1)
    @GetMapping("/{version}/all") // @GetMapping是一个组合注解 是@RequestMapping(method = RequestMethod.GET)的缩写
    @ApiOperation(value = "获取全部客户信息value位置",notes = "获取全部客户信息notes位置")
    public Object findAllUser(@PathVariable(value = "version") String version){
        return userService.findAllUser();
    }

    @ApiVersion(2)
    @ResponseBody
    @GetMapping("/{version}/all") // @GetMapping是一个组合注解 是@RequestMapping(method = RequestMethod.GET)的缩写
    @ApiOperation(value = "获取全部客户信息value2位置",notes = "获取全部客户信息notes2位置")
    public Object findAllUser2(@PathVariable(value = "version") String version){
        return "hello v2";
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
        return "hello world";
    }

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
}

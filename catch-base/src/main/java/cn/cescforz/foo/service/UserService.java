package cn.cescforz.foo.service;

import cn.cescforz.foo.bean.domain.User;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


public interface UserService extends IService<User> {

    List<User> findAllUserName();

    IPage<User> selectPageVo(Page page,String userName);
}

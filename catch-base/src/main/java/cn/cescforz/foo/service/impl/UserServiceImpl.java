package cn.cescforz.foo.service.impl;

import cn.cescforz.foo.bean.domain.User;
import cn.cescforz.foo.mapper.UserMapper;
import cn.cescforz.foo.enumeration.ResponseCode;
import cn.cescforz.foo.exception.ServerTransException;
import cn.cescforz.foo.service.UserService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {



    @Override
    public List<User> findAllUserName() {
        List<User> list = baseMapper.findAllUserName();
        return list;
    }

    @Override
    @Transactional
    public IPage<User> selectPageVo(Page page, String userName) {
        return baseMapper.selectPageVo(page, userName);
    }
}

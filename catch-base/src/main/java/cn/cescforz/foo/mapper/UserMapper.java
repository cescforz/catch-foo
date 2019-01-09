package cn.cescforz.foo.mapper;

import cn.cescforz.foo.bean.domain.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper extends BaseMapper<User> {

    List<User> findAllUserName();

    IPage<User> selectPageVo(Page page, @Param("userName") String userName);

}

package cn.cescforz.foo.service.impl;

import cn.cescforz.foo.bean.domain.ErrorLog;
import cn.cescforz.foo.bean.domain.User;
import cn.cescforz.foo.mapper.ErrorLogMapper;
import cn.cescforz.foo.exception.ServerTransException;
import cn.cescforz.foo.mapper.UserMapper;
import cn.cescforz.foo.service.ErrorLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>©2018 Cesc. All Rights Reserved.</p>
 * <p>Description: </p>
 *
 * @author cesc
 * @version v1.0
 * @date Create in 2018-12-26 17:55
 */
@Service
public class ErrorLogServiceImpl extends ServiceImpl<ErrorLogMapper, ErrorLog> implements ErrorLogService {


}

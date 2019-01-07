package cn.cescforz.foo.service;

import cn.cescforz.foo.bean.domain.ErrorLog;
import cn.cescforz.foo.exception.ServerTransException;

/**
 * <p>©2018 Cesc. All Rights Reserved.</p>
 * <p>Description: </p>
 *
 * @author cesc
 * @version v1.0
 * @date Create in 2018-12-26 17:54
 */
public interface ErrorLogService {

    int insert(ErrorLog errorLog) throws ServerTransException;
}

package cn.cescforz.foo.dao.impl;

import cn.cescforz.foo.bean.domain.ErrorLog;
import cn.cescforz.foo.dao.ErrorLogDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

/**
 * <p>©2019 Cesc. All Rights Reserved.</p>
 * <p>Description: </p>
 *
 * @author cesc
 * @version v1.0
 * @date Create in 2019-01-14 00:36
 */
@Repository
public class ErrorLogDaoImpl extends BaseDaoImpl<ErrorLog> implements ErrorLogDao {

    @Autowired
    public ErrorLogDaoImpl(MongoTemplate mongoTemplate) {
        super(mongoTemplate);
    }
}

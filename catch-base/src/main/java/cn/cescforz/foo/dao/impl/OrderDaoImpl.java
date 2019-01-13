package cn.cescforz.foo.dao.impl;

import cn.cescforz.foo.bean.domain.Order;
import cn.cescforz.foo.dao.OrderDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

/**
 * <p>©2019 Cesc. All Rights Reserved.</p>
 * <p>Description: </p>
 *
 * @author cesc
 * @version v1.0
 * @date Create in 2019-01-10 00:32
 */
@Repository
public class OrderDaoImpl extends BaseDaoImpl<Order> implements OrderDao {

    @Autowired
    public OrderDaoImpl(MongoTemplate mongoTemplate) {
        super(mongoTemplate);
    }
}

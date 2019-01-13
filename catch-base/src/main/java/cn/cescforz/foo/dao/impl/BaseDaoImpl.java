package cn.cescforz.foo.dao.impl;

import cn.cescforz.foo.bean.model.BaseUUIDGenModel;
import cn.cescforz.foo.dao.BaseDao;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * <p>©2019 Cesc. All Rights Reserved.</p>
 * <p>Description: </p>
 *
 * @author cesc
 * @version v1.0
 * @date Create in 2019-01-13 23:40
 */
public class BaseDaoImpl<T extends BaseUUIDGenModel<T>> implements BaseDao<T> {

    protected MongoTemplate mongoTemplate;
    @SuppressWarnings("unchecked")
    private Class<T> entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    private String collectionName;

    public BaseDaoImpl(MongoTemplate mongoTemplate) {
        Document doc = entityClass.getAnnotation(Document.class);
        if (null != doc) {
            this.collectionName = doc.collection();
        } else {
            this.collectionName = entityClass.getSimpleName().toLowerCase();
        }
        this.mongoTemplate = mongoTemplate;
    }


    @Override
    public T findOne(Query query) {
        return mongoTemplate.findOne(query, entityClass, collectionName);
    }

    @Override
    public List<T> find(Query query) {
        return mongoTemplate.find(query, entityClass, collectionName);
    }

    @Override
    public List<T> find(Query query, Integer page, Integer size) {
        return null;
    }

    @Override
    public int updateAll(T t) {
        return 0;
    }

    @Override
    public int update(Query query, Update update) {
        return 0;
    }

    @Override
    public int updateMulti(Query query, Update update) {
        return 0;
    }

    @Override
    public T findAndModify(Query query, Update update, boolean isNew) {
        return null;
    }

    @Override
    public void insert(T t) {
        mongoTemplate.insert(t,collectionName);
    }

    @Override
    public int delete(Query query) {
        return 0;
    }

    @Override
    public void insert(List<T> ts) {
        mongoTemplate.insertAll(ts);
    }

    @Override
    public long count(Query query) {
        return 0;
    }

    @Override
    public boolean isExist(Query query) {
        return false;
    }
}

package cn.cescforz.foo.dao;

import cn.cescforz.foo.bean.domain.User;
import cn.cescforz.foo.bean.model.BaseUUIDGenModel;
import cn.cescforz.foo.util.StringCustomUtils;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.List;

/**
 * <p>©2019 Cesc. All Rights Reserved.</p>
 * <p>Description: </p>
 *
 * @author cesc
 * @version v1.0
 * @date Create in 2019-01-13 22:45
 */
public interface BaseDao<T extends BaseUUIDGenModel<T>> {

    /**
     * 通过指定条件查找一个
     */
    T findOne(Query query);

    /**
     * 通过指定条件查找列表, 不做分页
     * @param query
     * @return
     */
    List<T> find(Query query);
    /**
     * 通过指定条件查找列表, 分页
     * @param query
     * @param pageable
     * @return
     */
    List<T> find(Query query,Integer page, Integer size);
    /**
     * 更新所有的字段，除了指定字段
     * @param t
     */
    int updateAll(T t);

    /**
     * 更新制定字段
     * @param query 查询条件
     * @param update 更新字段集合
     * @return 是否成功
     */
    int update(Query query, Update update);


    /**
     * 更新制定字段
     * @param query 查询条件
     * @param update 更新字段集合
     * @return 是否成功
     */
    int updateMulti(Query query, Update update);
    /**
     * 查找更新
     * @param query 查询条件
     * @param update 更新字段集合
     * @param isNew 是否返回更新后的记录
     * @return
     */
    T findAndModify(Query query, Update update, boolean isNew);
    /**
     * 插入
     * @param t 要插入的记录
     */
    void insert(T t);
    /**
     * 删除
     * @param query 查询条件
     */
    int delete(Query query);
    /**
     * 批量插入
     * @param tList
     */
    void insert(List<T> tList);

    /**
     * 查询数量
     * @param query 查询条件
     * @return
     */
    long count(Query query);

    /**
     *  是否存在
     * @param query 查询条件
     * @return
     */
    boolean isExist(Query query);

}

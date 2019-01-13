package cn.cescforz.foo.bean.domain;

import cn.cescforz.foo.bean.model.BaseUUIDGenModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <p>©2019 Cesc. All Rights Reserved.</p>
 * <p>Description: </p>
 *
 * @author cesc
 * @version v1.0
 * @date Create in 2019-01-10 00:31
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class Order extends BaseUUIDGenModel<Order> {

    private String name;
}

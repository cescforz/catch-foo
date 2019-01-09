package cn.cescforz.foo.bean.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>©2019 Cesc. All Rights Reserved.</p>
 * <p>Description: </p>
 *
 * @author cesc
 * @version v1.0
 * @date Create in 2019-01-10 00:31
 */
@Data
@NoArgsConstructor
public class Order implements Serializable {

    private Long id;
    private String name;
}

package cn.cescforz.foo.bean.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <p>©2018 Cesc. All Rights Reserved.</p>
 * <p>Description: </p>
 *
 * @author cesc
 * @version v1.0
 * @date Create in 2018-12-26 15:23
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class RequestDTO<T> extends BaseApiRequest {

    private static final long serialVersionUID = -2396868198757572959L;
    /**请求数据*/
    private T data;
}

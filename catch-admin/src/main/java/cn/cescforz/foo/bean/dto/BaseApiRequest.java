package cn.cescforz.foo.bean.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>©2018 Cesc. All Rights Reserved.</p>
 * <p>Description: </p>
 *
 * @author cesc
 * @version v1.0
 * @date Create in 2018-12-26 15:22
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseApiRequest implements Serializable {
    private static final long serialVersionUID = -950123656046197016L;

    private String appKey;
    private String appSecret;


}

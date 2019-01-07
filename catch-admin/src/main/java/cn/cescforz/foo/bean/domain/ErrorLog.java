package cn.cescforz.foo.bean.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>©2018 Cesc. All Rights Reserved.</p>
 * <p>Description: 异常记录类</p>
 * @author cesc
 * @version v1.0
 * @date Create in 2018-12-26 16:18
 */
@NoArgsConstructor
@Data
public class ErrorLog implements Serializable {
    private static final long serialVersionUID = 3197615759173252840L;

    private String id;
    private String interfaceName;
    private String requestParam;
    private Long consumeTime;
    private String logInfo;
    private Integer moduleType;
    private Date logTime;

}

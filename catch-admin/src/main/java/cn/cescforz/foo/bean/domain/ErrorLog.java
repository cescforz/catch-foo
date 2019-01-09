package cn.cescforz.foo.bean.domain;

import cn.cescforz.foo.bean.model.BaseModel;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <p>©2018 Cesc. All Rights Reserved.</p>
 * <p>Description: 异常记录类</p>
 * @author cesc
 * @version v1.0
 * @date Create in 2018-12-26 16:18
 */
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
@TableName("t_error_logs")
public class ErrorLog extends BaseModel<ErrorLog> {

    @TableField("interface_name")
    private String interfaceName;
    @TableField("request_param")
    private String requestParam;
    @TableField("consume_time")
    private Long consumeTime;
    @TableField("log_info")
    private String logInfo;
    @TableField("module_type")
    private Integer moduleType;

}

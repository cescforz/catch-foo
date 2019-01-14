package cn.cescforz.foo.bean.domain;

import cn.cescforz.foo.bean.model.BaseModel;
import cn.cescforz.foo.bean.model.BaseUUIDGenModel;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

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
@Document(collection = "errorLog")
public class ErrorLog extends BaseUUIDGenModel<ErrorLog> {

    private String interfaceName;
    private String requestParam;
    private Long consumeTime;
    private String logInfo;
    private Integer moduleType;

}

package cn.cescforz.foo.bean.model;

import cn.cescforz.foo.constant.SystemConstants;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>©2019 Cesc. All Rights Reserved.</p>
 * <p>Description: </p>
 *
 * @author cesc
 * @version v1.0
 * @date Create in 2019-01-09 15:23
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BaseModel<T extends Model> extends Model<T> {

    @TableId(value = "id", type = IdType.AUTO) //阿里规约:其中id必为主键，类型为unsigned bigint、单表时自增、步长为1
    private Long id;

    @TableField(value = "gmt_create")
    @JsonProperty(value = "createDate")
    @DateTimeFormat(pattern = SystemConstants.DEFAULT_DATE_PATTERN)
    @JsonFormat(pattern = SystemConstants.DEFAULT_DATE_PATTERN,timezone = SystemConstants.DEFAULT_TIME_ZONE)
    private Date createDate;

    @TableField(value = "gmt_modified")
    @JsonProperty(value = "updateDate")
    @DateTimeFormat(pattern = SystemConstants.DEFAULT_DATE_PATTERN)
    @JsonFormat(pattern = SystemConstants.DEFAULT_DATE_PATTERN,timezone = SystemConstants.DEFAULT_TIME_ZONE)
    private Date updateDate;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    /*
        AUTO(0, "数据库ID自增"),
        INPUT(1, "用户输入ID"),
        ID_WORKER(2, "全局唯一ID"),
        UUID(3, "全局唯一ID"),
        NONE(4, "该类型为未设置主键类型"),
        ID_WORKER_STR(5, "字符串全局唯一ID");
     */

}


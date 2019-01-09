package cn.cescforz.foo.bean.domain;

import cn.cescforz.foo.bean.model.BaseModel;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


/**
 * <p>©2018 Cesc. All Rights Reserved.</p>
 * <p>Description: 用户</p>
 * @author cesc
 * @version v1.0
 * @date Create in 2018-12-26 16:18
 */
@Data
@NoArgsConstructor
@TableName("t_user")
@EqualsAndHashCode(callSuper = true)
public class User extends BaseModel<ErrorLog> {

    @TableField("user_name")
    private String userName;
    @TableField("password")
    private String password;
    @TableField("phone")
    private String phone;

}

package cn.cescforz.foo.bean.model;

import cn.cescforz.foo.enumeration.EncryptBodyMethod;
import cn.cescforz.foo.enumeration.SHAEncryptType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>©2018 Cesc. All Rights Reserved.</p>
 * <p>Description: 加密注解信息</p>
 * @author cesc
 * @version v1.0
 * @date Create in 2018-12-23 21:32
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EncryptAnnotationInfoBean {

    private EncryptBodyMethod encryptBodyMethod;

    private String key;

    private SHAEncryptType shaEncryptType;
}

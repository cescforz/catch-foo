package cn.cescforz.foo.bean.model;

import cn.cescforz.foo.enumeration.DecryptBodyMethod;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>©2018 Cesc. All Rights Reserved.</p>
 * <p>Description: 解密注解信息</p>
 * @author cesc
 * @version v1.0
 * @date Create in 2018-12-23 21:31
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DecryptAnnotationInfoBean {

    private DecryptBodyMethod decryptBodyMethod;
    private String key;
}

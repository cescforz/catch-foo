package cn.cescforz.foo.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p>©2018 Cesc. All Rights Reserved.</p>
 * <p>Description: SHA加密类型</p>
 *
 * @author cesc
 * @version v1.0
 * @date Create in 2018-12-23 20:04
 */
@AllArgsConstructor
@Getter
public enum SHAEncryptType {

    SHA224("sha-224"),
    SHA256("sha-256"),
    SHA384("sha-384"),
    SHA512("sha-512");

    private String value;
}

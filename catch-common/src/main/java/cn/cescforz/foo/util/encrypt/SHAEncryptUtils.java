package cn.cescforz.foo.util.encrypt;

import cn.cescforz.foo.enumeration.SHAEncryptType;
import cn.cescforz.foo.util.StringCustomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * <p>©2018 Cesc. All Rights Reserved.</p>
 * <p>Description: SHA加密工具类</p>
 *
 * @author cesc
 * @version v1.0
 * @date Create in 2018-12-23 20:15
 */
public final class SHAEncryptUtils {


    private SHAEncryptUtils(){throw new AssertionError();}

    private static final Logger LOG = LoggerFactory.getLogger(SHAEncryptUtils.class);

    /**
     * <p>Description: SHA加密公共方法</p>
     * @param source 源字符串
     * @param type 加密类型 {@link SHAEncryptType}
     * @return java.lang.String
     */
    public static String encrypt(String source, SHAEncryptType type) {
        if(StringCustomUtils.isBlank(source)){
            return "";
        }
        if (type==null) {
            type = SHAEncryptType.SHA256;
        }
        try {
            MessageDigest md5 = MessageDigest.getInstance(type.getValue());
            byte[] bytes = md5.digest((source).getBytes());
            StringBuilder result = new StringBuilder();
            for (byte b : bytes) {
                String temp = Integer.toHexString(b & 0xff);
                if (temp.length() == 1) {
                    temp = "0" + temp;
                }
                result.append(temp);
            }
            return result.toString();
        } catch (NoSuchAlgorithmException e) {
            LOG.error(String.format("SHA type %s",type.getValue()),e);
        }
        return "";
    }

    /**
     * MD5 vs SHA
     * 对强行攻击的安全性：最显著和最重要的区别是SHA-1摘要比MD5摘要长32 位。使用强行技术，
     *                  产生任何一个报文使其摘要等于给定报摘要的难度对MD5是2^128数量级的操作，而对SHA-1则是2^160数量级的操作。
     *                  这样，SHA-1对强行攻击有更大的强度。
     * 对密码分析的安全性：由于MD5的设计，易受密码分析的攻击，SHA-1显得不易受这样的攻击。
     * 速度：在相同的硬件上，SHA-1的运行速度比MD5慢。
     */
}

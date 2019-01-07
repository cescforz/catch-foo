package cn.cescforz.foo.util.encrypt;

import cn.cescforz.foo.constant.SystemConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.security.SecureRandom;

/**
 * <p>©2018 Cesc. All Rights Reserved.</p>
 * <p>Description: DES加密工具类(对称的加密算法)</p>
 *
 * @author cesc
 * @version v1.0
 * @date Create in 2018-12-23 03:01
 */
public final class DESEncryptUtils {

    private DESEncryptUtils(){ throw new AssertionError();}


    /**
     *  DES全称为Data Encryption Standard，即数据加密标准，是一种使用密钥加密的块算法
     *  DES算法的入口参数有三个：Key、Data、Mode。
     *  (其中Key为7个字节共56位，是DES算法的工作密钥；Data为8个字节64位，是要被加密或被解密的数据；Mode为DES的工作方式,有两种:加密或解密。)
     *  注意：DES加密和解密过程中，密钥长度都必须是8的倍数,且不能是8
     *  DES 是对称的加密算法（PS：对称加密就是加密用的密码和解密用的密码是一样的，非对称就是加密和解密用的密钥不一样）
     *
     * DES基本原则
     *
     *      DES设计中使用了分组密码设计的两个原则：混淆（confusion）和扩散(diffusion)，其目的是抗击敌手对密码系统的统计分析。
     *      混淆是使密文的统计特性与密钥的取值之间的关系尽可能复杂化，以使密钥和明文以及密文之间的依赖性对密码分析者来说是无法利用的。
     *      扩散的作用就是将每一位明文的影响尽可能迅速地作用到较多的输出密文位中，以便在大量的密文中消除明文的统计结构，
     *      并且使每一位密钥的影响尽可能迅速地扩展到较多的密文位中，以防对密钥进行逐段破译。
     *
     */

    private static final Logger LOG = LoggerFactory.getLogger(DESEncryptUtils.class);

    /**
     * <p>Description: DES加密</p>
     * @param source  源字符串
     * @param secretKey 密钥
     * @return java.lang.String
     */
    public static String encrypt(String source, String secretKey){
        return des(source,secretKey,Cipher.ENCRYPT_MODE);
    }

    /**
     * <p>Description: DES解密</p>
     * @param target 目标字符串
     * @param secretKey 密钥
     * @return java.lang.String
     */
    public static String decrypt(String target, String secretKey){
        return des(target,secretKey,Cipher.DECRYPT_MODE);
    }

    /**
     * <p>Description: DES加密/解密公共方法</p>
     * @param content 字符串内容
     * @param password 密钥
     * @param type 加密：{@link Cipher#ENCRYPT_MODE}，解密：{@link Cipher#DECRYPT_MODE}
     * @return java.lang.String
     */
    private static String des(String content, String password, int type) {
        try {
            SecureRandom random = new SecureRandom();
            DESKeySpec desKey = new DESKeySpec(password.getBytes());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(SystemConstants.ALGORITHM_DES);
            Cipher cipher = Cipher.getInstance(SystemConstants.ALGORITHM_DES);
            cipher.init(type, keyFactory.generateSecret(desKey), random);
            if (type == Cipher.ENCRYPT_MODE) {
                byte[] byteContent = content.getBytes(SystemConstants.CHARSET_UTF_8);
                return Hex2Utils.parseByte2HexStr(cipher.doFinal(byteContent));
            } else {
                byte[] byteContent = Hex2Utils.parseHexStr2Byte(content);
                assert byteContent != null;
                return new String(cipher.doFinal(byteContent));
            }
        } catch (Exception e) {
            LOG.error(String.format("DES type %d",type),e);
        }
        return null;
    }
}

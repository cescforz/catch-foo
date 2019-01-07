package cn.cescforz.foo.util.encrypt;

import cn.cescforz.foo.constant.SystemConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;

/**
 * <p>©2018 Cesc. All Rights Reserved.</p>
 * <p>Description: AES加密工具类(对称的加密算法)</p>
 * @author cesc
 * @version v1.0
 * @date Create in 2018-12-23 19:13
 */
public final class AESEncryptUtils {

    private AESEncryptUtils(){throw new AssertionError();}

    private static final Logger LOG = LoggerFactory.getLogger(AESEncryptUtils.class);

    /**
     * 高级加密标准（英语：Advanced Encryption Standard，缩写：AES），在密码学中又称Rijndael加密法
     * AES加密数据块分组长度必须为128比特，密钥长度可以是128比特、192比特、256比特中的任意一个（如果数据块及密钥长度不足时，会补齐）。
     * AES加密有很多轮的重复和变换。
     * 大致步骤如下：1、密钥扩展（KeyExpansion），2、初始轮（Initial Round），3、重复轮（Rounds）
     * 每一轮又包括：SubBytes、ShiftRows、MixColumns、AddRoundKey，4、最终轮（Final Round），最终轮没有MixColumns。
     *
     * AES 是对称的加密算法（PS：对称加密就是加密用的密码和解密用的密码是一样的，非对称就是加密和解密用的密钥不一样）
     * AES是替代DES的新的加密标准，不能说是最安全的，比如采用非对称加密时，安全系数会更高的。
     * 非对称加密的加密因子与解密因子是不同的，可以实现更高强度的加密，比如现在的数字证书就会采用这个模型，但是它的运算速度相对较慢。
     */

    /**
     * <p>Description: AES加密</p>
     * @param source 源字符串
     * @param secretKey 密钥
     * @return java.lang.String
     */
    public static String encrypt(String source, String secretKey){
        return aes(source,secretKey,Cipher.ENCRYPT_MODE);
    }

    /**
     * <p>Description: AES解密</p>
     * @param target 目标字符串
     * @param secretKey 密钥
     * @return java.lang.String
     */
    public static String decrypt(String target, String secretKey){
        return aes(target,secretKey,Cipher.DECRYPT_MODE);
    }


    /**
     * <p>Description: AES加密/解密 公共方法</p>
     * @param content 字符串
     * @param password 密钥
     * @param type 加密：{@link Cipher#ENCRYPT_MODE}，解密：{@link Cipher#DECRYPT_MODE}
     * @return java.lang.String
     */
    private static String aes(String content, String password, int type) {
        try {
            KeyGenerator generator = KeyGenerator.getInstance(SystemConstants.ALGORITHM_AES);
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            random.setSeed(password.getBytes());
            generator.init(128, random);
            SecretKey secretKey = generator.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, SystemConstants.ALGORITHM_AES);
            Cipher cipher = Cipher.getInstance(SystemConstants.ALGORITHM_AES);
            cipher.init(type, key);
            if (type == Cipher.ENCRYPT_MODE) {
                byte[] byteContent = content.getBytes(SystemConstants.CHARSET_UTF_8);
                return Hex2Utils.parseByte2HexStr(cipher.doFinal(byteContent));
            } else {
                byte[] byteContent = Hex2Utils.parseHexStr2Byte(content);
                return new String(cipher.doFinal(byteContent));
            }
        } catch (Exception e) {
            LOG.error(String.format("AES type %d", type),e);
        }
        return null;
    }

}

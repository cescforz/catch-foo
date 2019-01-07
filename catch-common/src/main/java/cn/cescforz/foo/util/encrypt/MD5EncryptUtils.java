package cn.cescforz.foo.util.encrypt;


import cn.cescforz.foo.util.StringCustomUtils;
import org.apache.commons.codec.binary.Hex;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

/**
 * <p>©2018 Cesc. All Rights Reserved.</p>
 * <p>Description: MD5散列工具类</p>
 *
 * @author cesc
 * @version v1.0
 * @date Create in 2018-12-22 17:48
 */
public final class MD5EncryptUtils {

    /**
     * Message Digest Algorithm MD5EncryptUtils（中文名为消息摘要算法第五版）为计算机安全领域广泛使用的一种散列函数，用以提供消息的完整性保护
     * MD5算法具有以下特点：
     * 1、压缩性：任意长度的数据，算出的MD5值长度都是固定的。
     * 2、容易计算：从原数据计算出MD5值很容易。
     * 3、抗修改性：对原数据进行任何改动，哪怕只修改1个字节，所得到的MD5值都有很大区别。
     * 4、强抗碰撞：已知原数据和其MD5值，想找到一个具有相同MD5值的数据（即伪造数据）是非常困难的。
     *
     * MD5的作用是让大容量信息在用数字签名软件签署私人密钥前被"压缩"成一种保密的格式（就是把一个任意长度的字节串变换成一定长的十六进制数字串）。
     * 除了MD5以外，其中比较有名的还有sha-1、RIPEMD以及Haval等。
     * MD5EncryptUtils 是非对称的加密算法（PS：对称加密就是加密用的密码和解密用的密码是一样的，非对称就是加密和解密用的密钥不一样）
     */

    /**工具类中的方法都是静态方式访问的因此将构造器私有不允许创建对象*/
    private MD5EncryptUtils(){ throw new AssertionError();}



    /**
     * <p>Description: 普通MD5</p>
     * @param source 源字符串
     * @return java.lang.String
     */
    public static String encrypt(String source){
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            return "check jdk";
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        char[] charArray = source.toCharArray();
        byte[] byteArray = new byte[charArray.length];

        for (int i = 0; i < charArray.length; i++) {
            byteArray[i] = (byte) charArray[i];
        }
        byte[] md5Bytes = md5.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16) {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }

    /**
     * <p>Description: 加盐MD5:每次保存密码到数据库时，都生成一个随机16位数字，将这16位数字和密码相加再求MD5摘要，然后在摘要中再将这16位数字按规则掺入形成一个48位的字符串</p>
     * @param source 源字符串
     * @return java.lang.String
     */
    public static String encryptWithSalt(String source) {
        Random r = new Random();
        StringBuilder sb = new StringBuilder(16);
        sb.append(r.nextInt(99999999)).append(r.nextInt(99999999));
        int len = sb.length();
        if (len < 16) {
            for (int i = 0; i < 16 - len; i++) {
                sb.append("0");
            }
        }
        String salt = sb.toString();
        source = md5Hex(source + salt);
        char[] cs = new char[48];
        for (int i = 0; i < 48; i += 3) {
            cs[i] = source.charAt(i / 3 * 2);
            char c = salt.charAt(i / 3);
            cs[i + 1] = c;
            cs[i + 2] = source.charAt(i / 3 * 2 + 1);
        }
        return new String(cs);
    }

    /**
     * <p>Description: 获取十六进制字符串形式的MD5摘要</p>
     * @param src 字符串
     * @return java.lang.String
     */
    private static String md5Hex(String src) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] bs = md5.digest(src.getBytes());
            return new String(new Hex().encode(bs));
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * <p>Description: 校验加盐后是否和原文一致</p>
     * @param password 原密码
     * @param md5 加盐散列后的密码
     * @return boolean
     */
    public static boolean verify(String password, String md5){
        char[] cs1 = new char[32];
        char[] cs2 = new char[16];
        for (int i = 0; i < 48; i += 3) {
            cs1[i / 3 * 2] = md5.charAt(i);
            cs1[i / 3 * 2 + 1] = md5.charAt(i + 2);
            cs2[i / 3] = md5.charAt(i + 1);
        }
        String salt = new String(cs2);
        return StringCustomUtils.isEquals(md5Hex(password + salt), new String(cs1));
    }
}

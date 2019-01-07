package cn.cescforz.foo.util;

import cn.cescforz.foo.constant.SystemConstants;
import org.apache.commons.codec.binary.Base64;


/**
 * <p>©2018 Cesc. All Rights Reserved.</p>
 * <p>Description: Base64工具类(对称的加密算法)</p>
 *
 * @author cesc
 * @version v1.0
 * @date Create in 2018-12-22 20:13
 */
public final class Base64Utils {


    /**
     * Base64是网络上最常见的用于传输8Bit字节代码的编码方式之一，Base64编码可用于在HTTP环境下传递较长的标识信息。
     * 例如，在Java Persistence系统Hibernate中，就采用了Base64来将一个较长的唯一标识符（一般为128-bit的UUID）编码为一个字符串，用作HTTP表单和HTTP GET URL中的参数。
     * 在其他应用程序中，也常常需要把二进制数据编码为适合放在URL（包括隐藏表单域）中的形式。此时，采用Base64编码具有不可读性，即所编码的数据不会被人用肉眼所直接看到。
     *
     *  然而，标准的Base64并不适合直接放在URL里传输
     *  因为URL编码器会把标准Base64中的“/”和“+”字符变为形如“%XX”的形式，而这些“%”号在存入数据库时还需要再进行转换，因为ANSI SQL中已将“%”号用作通配符。
     *
     *  为解决此问题，可采用一种用于URL的改进Base64编码.
     *  它不仅在末尾去掉填充的'='号，并将标准Base64中的“+”和“/”分别改成了“-”和“_”
     *  这样就免去了在URL编解码和数据库存储时所要作的转换，避免了编码信息长度在此过程中的增加，并统一了数据库、表单等处对象标识符的格式。
     *
     *  BASE64 是对称的加密算法（PS：对称加密就是加密用的密码和解密用的密码是一样的，非对称就是加密和解密用的密钥不一样）
     */


    /**工具类中的方法都是静态方式访问的因此将构造器私有不允许创建对象*/
    private Base64Utils(){throw new AssertionError();}


    /**
     * <p>Description: 给指定字符串进行base64编码</p>
     * @param inputData 字符串
     * @return java.lang.String
     */
    public static String encodeData(String inputData) {
        if (null == inputData) {
            return null;
        }
        return new String(Base64.encodeBase64(inputData.getBytes(SystemConstants.CHARSET_UTF_8)), SystemConstants.CHARSET_UTF_8);
    }


    /**
     * <p>Description: 对给定的字符串进行base64解码</p>
     * @param inputData 字符串
     * @return java.lang.String
     */
    public static String decodeData(String inputData) {
        if (null == inputData) {
            return null;
        }
        return new String(Base64.decodeBase64(inputData.getBytes(SystemConstants.CHARSET_UTF_8)), SystemConstants.CHARSET_UTF_8);
    }



}

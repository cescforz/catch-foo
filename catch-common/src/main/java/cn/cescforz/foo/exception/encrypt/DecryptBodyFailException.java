package cn.cescforz.foo.exception.encrypt;

/**
 * <p>©2018 Cesc. All Rights Reserved.</p>
 * <p>Description: 解密数据失败异常</p>
 *
 * @author cesc
 * @version v1.0
 * @date Create in 2018-12-23 20:56
 */
public class DecryptBodyFailException extends RuntimeException {

    public DecryptBodyFailException() {
        super("Decrypting data failed. (解密数据失败)");
    }

    public DecryptBodyFailException(String message) {
        super(message);
    }

}

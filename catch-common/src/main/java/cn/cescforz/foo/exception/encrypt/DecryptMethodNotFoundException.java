package cn.cescforz.foo.exception.encrypt;

/**
 * <p>©2018 Cesc. All Rights Reserved.</p>
 * <p>Description: 加密方式未找到或未定义异常</p>
 *
 * @author cesc
 * @version v1.0
 * @date Create in 2018-12-23 20:57
 */
public class DecryptMethodNotFoundException extends RuntimeException {

    public DecryptMethodNotFoundException() {
        super("Decryption method is not defined. (解密方式未定义)");
    }

    public DecryptMethodNotFoundException(String message) {
        super(message);
    }
}

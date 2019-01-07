package cn.cescforz.foo.util.encrypt;

/**
 * <p>©2018 Cesc. All Rights Reserved.</p>
 * <p>Description: 二进制转换工具类</p>
 *
 * @author cesc
 * @version v1.0
 * @date Create in 2018-12-23 02:57
 */
public final class Hex2Utils {

    private Hex2Utils(){ throw new AssertionError();}

    /**
     * <p>Description: 二进位组转十六进制字符串</p>
     * @param buf 二进位组
     * @return java.lang.String 十六进制字符串
     */
    public static String parseByte2HexStr(byte buf[]) {
        StringBuilder sb = new StringBuilder();
        for (byte b : buf) {
            String hex = Integer.toHexString(b & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * <p>Description: 十六进制字符串转二进位组</p>
     * @param hexStr 十六进制字符串
     * @return byte[] 二进位组
     */
    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1) {
            return null;
        }
        byte[] result = new byte[hexStr.length() / 2];

        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }
}

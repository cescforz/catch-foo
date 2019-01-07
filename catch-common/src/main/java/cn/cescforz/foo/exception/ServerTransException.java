package cn.cescforz.foo.exception;

import cn.cescforz.foo.enumeration.ResponseCode;
import org.apache.commons.lang3.StringUtils;

/**
 * <p>©2018 Cesc. All Rights Reserved.</p>
 * <p>Description: 服务异常类</p>
 *
 * @author cesc
 * @version 1.0
 * @date Create in 2018-12-17 10:09
 */
public class ServerTransException extends RuntimeException {

    private static final long serialVersionUID = 2590383418428628755L;

    private ResponseCode responseCode;

    public String getErrCode() {
        return responseCode.getCode();
    }

    public ResponseCode getResponseCode() {
        return responseCode;
    }

    public ServerTransException(ResponseCode responseCode) {
        super(responseCode.getMsg());
        this.responseCode = responseCode;
    }

    public ServerTransException(ResponseCode responseCode, String msg) {
        this(responseCode, msg, null);
    }

    public ServerTransException(ResponseCode responseCode, String msg, String boundSymbol) {
        super(String.format("%s%s %s", responseCode.getMsg(), StringUtils.isEmpty(boundSymbol) ? "," : boundSymbol, msg));
        this.responseCode = responseCode;
    }
}

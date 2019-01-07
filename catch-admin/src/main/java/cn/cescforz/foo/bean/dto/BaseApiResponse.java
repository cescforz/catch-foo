package cn.cescforz.foo.bean.dto;

import cn.cescforz.foo.enumeration.ResponseCode;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>©2018 Cesc. All Rights Reserved.</p>
 * <p>Description: 基础的服务响应体</p>
 *
 * @author cesc
 * @version v1.0
 * @date Create in 2018-12-26 10:03
 */
@Data
public abstract class BaseApiResponse implements Serializable {

    private static final long serialVersionUID = -9157387494465415954L;

    private static final String RESP_CODE_SUCCESS = ResponseCode.RESP_CODE_SUCCESS.getCode();
    private static final String RESP_CODE_ERROR = ResponseCode.RESP_CODE_ERROR.getCode();

    /**响应码*/
    private String respCode;
    /**响应内容*/
    private String respDesc;
    /**错误码*/
    private String errCode;
    /**错误内容*/
    private String errDesc;

    public BaseApiResponse(boolean success, String errCode, String errDesc) {
        this(errCode, errDesc);
        this.respCode = success ? RESP_CODE_SUCCESS : RESP_CODE_ERROR;
        this.respDesc = success ? ResponseCode.RESP_CODE_SUCCESS.getMsg() : ResponseCode.RESP_CODE_ERROR.getMsg();
    }

    public BaseApiResponse(String errCode, String errDesc) {
        this.errCode = errCode;
        this.errDesc = errDesc;
    }

    public BaseApiResponse() {
        this(true, null, null);
    }

    public boolean checkSuccess() {
        return RESP_CODE_SUCCESS.equals(this.respCode);
    }
}

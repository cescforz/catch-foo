package cn.cescforz.foo.bean.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * <p>©2018 Cesc. All Rights Reserved.</p>
 * <p>Description: 接口调用返回</p>
 *
 * @author cesc
 * @version v1.0
 * @date Create in 2018-12-26 09:49
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ResponseDTO<T> extends BaseApiResponse {

    private static final long serialVersionUID = -8094962736273570120L;

    /**返回数据*/
    private T data;

    public ResponseDTO(boolean success, String errCode, String errDesc, T data) {
        super(success, errCode, errDesc);
        this.data = data;
    }

    public ResponseDTO(boolean success, String errCode, String errDesc) {
        this(success, errCode, errDesc, null);
    }

    public ResponseDTO() {
        this(true, null, null, null);
    }

    public ResponseDTO(T data) {
        this(true, null, null, data);
    }
}

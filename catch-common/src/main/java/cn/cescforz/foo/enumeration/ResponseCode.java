package cn.cescforz.foo.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p>©2018 Cesc. All Rights Reserved.</p>
 * <p>Description: 响应码枚举类型</p>
 *
 * @author cesc
 * @version 1.0
 * @date Create in 2018-12-17 10:11
 */
@Getter
@AllArgsConstructor  //在枚举类型中，可以添加构造方法，但是规定构造方法必须为private修饰符所修饰
public enum ResponseCode {


    RESP_CODE_SUCCESS("000","成功"),
    RESP_CODE_ERROR("999","失败"),
    SYSTEM_ERROR("SYSTEM_ERROR", "系统错误"),
    SERVICE_EXCEPTION("SERVICE_EXCEPTION", "服务异常"),
    ILLEGAL_ARGUMENT_EXCEPTION("ILLEGAL_ARGUMENT_EXCEPTION", "请求参数异常"),
    GATE_WAY_CHECK_SIGN_FAIL("GATE_WAY_CHECK_SIGN_FAIL", "网关验签失败"),
    RECORD_EXIST("RECORD_EXIST", "数据已存在"),
    RECORD_NOT_EXIST("RECORD_NOT_EXIST", "数据不存在"),
    RECORD_RELATED("RECORD_RELATED", "数据已被关联"),
    ACCOUNT_LOGIN_EXCEPTION("ACCOUNT_LOGIN_EXCEPTION", "账户登录异常"),
    ACCOUNT_BAD_CREDENTIALS("LOGIN_BAD_CREDENTIALS", "用户名或密码不正确"),
    ACCOUNT_LOCKED("ACCOUNT_LOCKED", "账户已锁定"),
    ACCOUNT_DISABLED("ACCOUNT_DISABLED", "账户未启用"),
    ACCOUNT_EXPIRED("ACCOUNT_EXPIRED", "账户已过期");

    private String code;
    private String msg;
}

package cn.cescforz.foo.exception;

/**
 * <p>©2018 Cesc. All Rights Reserved.</p>
 * <p>Description: </p>
 *
 * @author cesc
 * @version 1.0
 * @date Create in 2018-12-17 10:02
 */
public class HttpClientException extends Exception {

    private static final long serialVersionUID = 5299084037818608483L;

    private int statusCode;
    private String content;

    public HttpClientException(String message, int statusCode, String content) {
        super(message);
        this.statusCode = statusCode;
        this.content = content;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

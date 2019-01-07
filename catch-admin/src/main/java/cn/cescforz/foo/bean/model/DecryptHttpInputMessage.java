package cn.cescforz.foo.bean.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;

import java.io.IOException;
import java.io.InputStream;

/**
 * <p>©2018 Cesc. All Rights Reserved.</p>
 * <p>Description: 解密信息输入流</p>
 * @author cesc
 * @version v1.0
 * @date Create in 2018-12-23 21:28
 */
@NoArgsConstructor
@AllArgsConstructor
public class DecryptHttpInputMessage implements HttpInputMessage {

    private InputStream body;
    private HttpHeaders headers;

    @Override
    public InputStream getBody() throws IOException {
        return body;
    }

    @Override
    public HttpHeaders getHeaders() {
        return headers;
    }
}

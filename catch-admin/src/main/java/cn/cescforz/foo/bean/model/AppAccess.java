package cn.cescforz.foo.bean.model;

import java.io.Serializable;

/**
 * <p>©2018 Cesc. All Rights Reserved.</p>
 * <p>Description: </p>
 *
 * @author cesc
 * @version v1.0
 * @date Create in 2018-12-17 17:03
 */
public class AppAccess implements Serializable {
    private static final long serialVersionUID = -7230241949942173327L;

    private String appKey;
    private String appSecret;
    private String bucket;
    private String endPoint;

    private AppAccess(Builder builder) {
        this.appKey = builder.appKey;
        this.appSecret = builder.appSecret;
        this.bucket = builder.bucket;
        this.endPoint = builder.endPoint;
    }

    public static Builder options() {
        return new Builder();
    }

    public String getAppKey() {
        return appKey;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public String getBucket() {
        return bucket;
    }

    public String getEndPoint() {
        return endPoint;
    }

    public static class Builder {
        private String appKey;
        private String appSecret;
        private String bucket;
        private String endPoint;

        public Builder setAppKey(String appKey) {
            this.appKey = appKey;
            return this;
        }

        public Builder setAppSecret(String appSecret) {
            this.appSecret = appSecret;
            return this;
        }

        public Builder setBucket(String bucket) {
            this.bucket = bucket;
            return this;
        }

        public Builder setEndPoint(String endPoint) {
            this.endPoint = endPoint;
            return this;
        }

        public AppAccess build() {
            return new AppAccess(this);
        }
    }

}

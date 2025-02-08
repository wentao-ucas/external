package cn.cncc.caos.external.client.cloud.dto;

public class TokenCacheItem {
    private String huaweitoken;

    private String roaRand;

    private long expires = -1;
    private long timestamp = -1;

    public TokenCacheItem() {
    }

    public TokenCacheItem(String huaweitoken, String roaRand, long expires, long timestamp) {
        this.huaweitoken = huaweitoken;
        this.roaRand = roaRand;
        this.expires = expires;
        this.timestamp = timestamp;
    }

    public String getHuaweitoken() {
        return huaweitoken;
    }

    public void setHuaweitoken(String huaweitoken) {
        this.huaweitoken = huaweitoken;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getRoaRand() {
        return roaRand;
    }

    public void setRoaRand(String roaRand) {
        this.roaRand = roaRand;
    }

    public long getExpires() {
        return expires;
    }

    public void setExpires(long expires) {
        this.expires = expires;
    }

    @Override
    public String toString() {
        return "TokenCacheItem{" +
                "huaweitoken='" + huaweitoken + '\'' +
                ", roaRand='" + roaRand + '\'' +
                ", expires=" + expires +
                ", timestamp=" + timestamp +
                '}';
    }
}

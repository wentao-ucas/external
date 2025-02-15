package cn.cncc.caos.common.redis;


/**
 * 全局锁，包括锁的名称
 */
public class RedisLock {
    private String name;
    private String value;

    public RedisLock(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

}
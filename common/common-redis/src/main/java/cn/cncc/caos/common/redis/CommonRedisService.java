package cn.cncc.caos.common.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisClusterConnection;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class CommonRedisService {
  @Autowired
  private RedisTemplate<String, String> redisTemplate;

  public RedisClusterConnection getClusterConnection() {
    return Objects.requireNonNull(redisTemplate.getConnectionFactory()).getClusterConnection();
  }

  public RedisConnection getConnection() {
    return Objects.requireNonNull(redisTemplate.getConnectionFactory()).getConnection();
  }
}

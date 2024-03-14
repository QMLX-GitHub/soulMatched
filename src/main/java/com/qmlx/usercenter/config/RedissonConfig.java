package com.qmlx.usercenter.config;
import lombok.Data;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 自己配置一个redission的客户端，因为我们没有引入spring整合的redission
 * @author QMLX9999
 */
@Configuration
@ConfigurationProperties(prefix = "spring.redis")//用来读取配置文件redis下的信息
@Data
public class RedissonConfig {
    private String host;
    private String port;
    private String password;
    @Bean
    public RedissonClient redissonClient(){
        //创建一个配置,设置redission连接redis的信息
        Config config = new Config();
        String redisAddress=String.format("redis://%s:%s",host,port);
        //我们只有一个redis所以单机部署即可
        config.useSingleServer()
                .setAddress(redisAddress)
                .setDatabase(3)
                .setPassword(password);

        //创建一个redission实例
        RedissonClient redisson = Redisson.create(config);
        return redisson;
    }
}

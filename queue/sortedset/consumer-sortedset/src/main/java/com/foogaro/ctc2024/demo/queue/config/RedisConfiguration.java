package com.foogaro.ctc2024.demo.queue.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ObjectUtils;
import redis.clients.jedis.Jedis;

@Configuration
public class RedisConfiguration {

    @Value("${spring.data.redis.host}")
    private String redisHost;
    @Value("${spring.data.redis.port}")
    private int redisPort;
    @Value("${spring.data.redis.username}")
    private String redisUsername;
    @Value("${spring.data.redis.password}")
    private String redisPassword;

    @Bean
    public Jedis jedis() {
        Jedis jedis = new Jedis(redisHost, redisPort);
        if (!ObjectUtils.isEmpty(redisPassword)) {
            if (ObjectUtils.isEmpty(redisUsername)) redisUsername = "default";
            jedis.auth(redisUsername,redisPassword);
        }
        return jedis;
    }
}
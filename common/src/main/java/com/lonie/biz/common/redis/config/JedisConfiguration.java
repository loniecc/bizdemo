package com.lonie.biz.common.redis.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author huzeming Created time 2020/3/16 : 1:17 下午 Desc:
 */

@PropertySource(value = "classpath:redis.properties")
@Configuration
public class JedisConfiguration {

    @Value("${jedis.pool.host}")
    private String host;

    @Value("${jedis.pool.port}")
    private int port;

    @Value("${jedis.pool.passwd}")
    private String passwd;

    @Bean
    public JedisPool getJedisPool() {

        JedisPoolConfig config = new JedisPoolConfig();

        //GenericObjectPoolConfig poolConfig, String host, int port, int timeout, String password/**/
        JedisPool pool = new JedisPool(config, host, port, 2000, passwd);

        return pool;
    }

}

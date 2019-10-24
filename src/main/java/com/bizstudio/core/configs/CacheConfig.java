/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bizstudio.core.configs;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 *
 * @author obinna.asuzu
 */
@Configuration
@EnableJpaRepositories(
        basePackages = {
            "com.bizstudio.security.repositories.cache"
        }
)
public class CacheConfig {
    
    @Autowired
    Environment env;

    @Bean
    @ConfigurationProperties(prefix = "spring.cache-datasource")
    public DataSource cacheDataSource() {
        return DataSourceBuilder.create()
                .url(env.getProperty("spring.cache-datasource.url"))
                .username(env.getProperty("spring.cache-datasource.username"))
                .password(env.getProperty("spring.cache-datasource.password"))
                .driverClassName(env.getProperty("spring.cache-datasource.driver-class-name"))
                .build();
    }
}







package com.burak.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.datasource")
@Getter
@Setter
public class DataSourceProperties {

    private String driverClassName;
    private String url;
    private String username;
    private String password;
}
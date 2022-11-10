package com.coveo.challenge.cities.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "coveo")
@Data
public class ConfigProperties {

    /**
     * File with US anc CA cities.
     */
    private String filepath;
}

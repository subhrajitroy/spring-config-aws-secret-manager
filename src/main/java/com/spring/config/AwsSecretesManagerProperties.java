package com.spring.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.config.server.support.EnvironmentRepositoryProperties;

@ConfigurationProperties("spring.cloud.config.server.aws")
public class AwsSecretesManagerProperties implements EnvironmentRepositoryProperties {

    private String region;

    private String secretName;


    private int order;

    @Override
    public void setOrder(int order) {
        this.order = order;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getSecretName() {
        return secretName;
    }

    public void setSecretName(String secretName) {
        this.secretName = secretName;
    }


}

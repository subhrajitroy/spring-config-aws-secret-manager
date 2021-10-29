package com.spring.config;

import org.springframework.cloud.config.server.config.ConfigServerProperties;
import org.springframework.cloud.config.server.environment.EnvironmentRepositoryFactory;

public class AwsSecretsManagerEnvironmentRepositoryFactory implements EnvironmentRepositoryFactory<AwsSecretsManagerEnvironmentRepository,AwsSecretesManagerProperties> {
    private ConfigServerProperties server;

    public AwsSecretsManagerEnvironmentRepositoryFactory(ConfigServerProperties server) {
        this.server = server;
    }

    @Override
    public AwsSecretsManagerEnvironmentRepository build(AwsSecretesManagerProperties environmentProperties) throws Exception {
        return new AwsSecretsManagerEnvironmentRepository(environmentProperties,server);
    }
}

package com.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration(proxyBeanMethods = false)
@Profile("aws")
class AwsSecretesManagerRepositoryConfiguration {

    @Bean
    public AwsSecretsManagerEnvironmentRepository awsSecretsManagerEnvironmentRepository(AwsSecretsManagerEnvironmentRepositoryFactory factory,
                                                                                         AwsSecretesManagerProperties environmentProperties) throws Exception {
        return factory.build(environmentProperties);
    }

}

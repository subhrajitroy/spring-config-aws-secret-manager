package com.spring.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.config.server.config.ConfigServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties({AwsSecretesManagerProperties.class})
@Import({AwsSecretesManagerRepositoryConfiguration.class})
public class SecureEnvironmentRepositoryConfiguration {


    @Configuration(proxyBeanMethods = false)
    static class AwsSecretsManagerFactoryConfig {

        @Bean
        public AwsSecretsManagerEnvironmentRepositoryFactory awsSecretsManagerEnvironmentRepositoryFactory(ConfigServerProperties server) {
            return new AwsSecretsManagerEnvironmentRepositoryFactory(server);
        }

    }

}

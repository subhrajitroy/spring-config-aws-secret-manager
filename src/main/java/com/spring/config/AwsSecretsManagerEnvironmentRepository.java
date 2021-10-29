package com.spring.config;

import com.amazonaws.services.secretsmanager.AWSSecretsManager;
import com.amazonaws.services.secretsmanager.AWSSecretsManagerClientBuilder;
import com.amazonaws.services.secretsmanager.model.*;
import org.springframework.cloud.config.environment.Environment;
import org.springframework.cloud.config.environment.PropertySource;
import org.springframework.cloud.config.server.config.ConfigServerProperties;
import org.springframework.cloud.config.server.environment.EnvironmentRepository;

import java.util.Properties;

public class AwsSecretsManagerEnvironmentRepository implements EnvironmentRepository {
    private AwsSecretesManagerProperties awsSecretesManagerProperties;
    private ConfigServerProperties server;

    public AwsSecretsManagerEnvironmentRepository(AwsSecretesManagerProperties awsSecretesManagerProperties, ConfigServerProperties server) {
        this.awsSecretesManagerProperties = awsSecretesManagerProperties;
        this.server = server;
    }

    @Override
    public Environment findOne(String application, String profile, String label) {
        final Environment testEnvironment = new Environment("test", profile);
        final Properties source = new Properties();
        source.put("name", server.getDefaultApplicationName());
        source.put("region", awsSecretesManagerProperties.getRegion());
        source.put(awsSecretesManagerProperties.getSecretName(), getSecret());
        final PropertySource propertySource = new PropertySource(profile, source);
        testEnvironment.add(propertySource);
        return testEnvironment;
    }

    @Override
    public Environment findOne(String application, String profile, String label, boolean includeOrigin) {
        return EnvironmentRepository.super.findOne(application, profile, label, includeOrigin);
    }

    private String getSecret() {


        // Create a Secrets Manager client
        AWSSecretsManager client = AWSSecretsManagerClientBuilder.standard()
                .withRegion(awsSecretesManagerProperties.getRegion())
                .build();

        // In this sample we only handle the specific exceptions for the 'GetSecretValue' API.
        // See https://docs.aws.amazon.com/secretsmanager/latest/apireference/API_GetSecretValue.html
        // We rethrow the exception by default.

        String secret = null;
//        String decodedBinarySecret = null;
        GetSecretValueRequest getSecretValueRequest = new GetSecretValueRequest()
                .withSecretId(awsSecretesManagerProperties.getSecretName());
        GetSecretValueResult getSecretValueResult = null;

        try {
            getSecretValueResult = client.getSecretValue(getSecretValueRequest);
        } catch (DecryptionFailureException e) {
            // Secrets Manager can't decrypt the protected secret text using the provided KMS key.
            // Deal with the exception here, and/or rethrow at your discretion.
            throw e;
        } catch (InternalServiceErrorException e) {
            // An error occurred on the server side.
            // Deal with the exception here, and/or rethrow at your discretion.
            throw e;
        } catch (InvalidParameterException e) {
            // You provided an invalid value for a parameter.
            // Deal with the exception here, and/or rethrow at your discretion.
            throw e;
        } catch (InvalidRequestException e) {
            // You provided a parameter value that is not valid for the current state of the resource.
            // Deal with the exception here, and/or rethrow at your discretion.
            throw e;
        } catch (ResourceNotFoundException e) {
            // We can't find the resource that you asked for.
            // Deal with the exception here, and/or rethrow at your discretion.
            throw e;
        }

        // Decrypts secret using the associated KMS CMK.
        // Depending on whether the secret is a string or binary, one of these fields will be populated.
        if (getSecretValueResult.getSecretString() != null) {
            secret = getSecretValueResult.getSecretString();
        }
//        else {
//            decodedBinarySecret = new String(Base64.getDecoder().decode(getSecretValueResult.getSecretBinary()).array());
//        }

        return secret;
    }
}



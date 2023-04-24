package nus.iss.Neko.Server.config;

// import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@Configuration
public class AppConfig {

    // @Value("${accessKey}")
    // private String accessKey;

    // @Value("${secretKey}")
    // private String secretKey;

    // @Value("${endpoint}")
    // private String endpoint;

    // @Value("${endpointRegion}")
    // private String endpointRegion;

    @Bean
    public AmazonS3 getS3Client() {
        BasicAWSCredentials cred = new BasicAWSCredentials("DO00PHXL3RNQBQUHTAKM", "mri1zXZxiXAzrGXbSo/gSap6pXyH7Gxw24lz6jFTzQw");

        EndpointConfiguration epConfig = new EndpointConfiguration("https://neko-bucket.sgp1.digitaloceanspaces.com", "sgp1");

        return AmazonS3ClientBuilder.standard()
                .withEndpointConfiguration(epConfig)
                .withCredentials(new AWSStaticCredentialsProvider(cred))
                .build();
    }
}
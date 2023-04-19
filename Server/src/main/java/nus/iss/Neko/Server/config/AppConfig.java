package nus.iss.Neko.Server.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

@Configuration
public class AppConfig {
    
    @Value("${do.storage.key}")
    private String accessKey;

    @Value("${do.storage.secretkey}")
    private String secretKey;

    @Value("${do.storage.endpoint}")
    private String endPoint;

    @Value("${do.storage.endpoint.region}")
    private String endPointRegion;

    @Value("${spring.redis.host}")
	private String redisHost;
	@Value("${spring.redis.port}")
	private int redisPort;
	@Value("${spring.redis.database}")
	private int redisDatabase;

	@Value("${spring.mongo.url}")
	private String mongoUrl;

	@Value("${spaces.secret.key}")
	private String spacesSecretKey;
	@Value("${spaces.access.key}")
	private String spacesAccessKey;

	@Value("${spaces.endpoint.url}")
	private String spacesEndpointUrl;

	@Value("${spaces.endpoint.region}")
	private String spacesRegion;

	@Bean("post")
	public RedisTemplate<String, String> createRedisTemplate() {
		final RedisStandaloneConfiguration config = new RedisStandaloneConfiguration(redisHost, redisPort);
		config.setDatabase(redisDatabase);

		final JedisClientConfiguration jedisClient = JedisClientConfiguration.builder()
				.build();
		final JedisConnectionFactory jedisFac = new JedisConnectionFactory(config, jedisClient);
		jedisFac.afterPropertiesSet();

		final RedisTemplate<String, String> template = new RedisTemplate<>();
		template.setConnectionFactory(jedisFac);
		template.setKeySerializer(new StringRedisSerializer());
		template.setValueSerializer(new StringRedisSerializer());
		template.setHashKeySerializer(new StringRedisSerializer());
		template.setHashValueSerializer(new StringRedisSerializer());
		return template;
	}

	@Bean
	public MongoTemplate createMongoTemplate() {
		MongoClient client = MongoClients.create(mongoUrl);
		return new MongoTemplate(client, "articles");
	}

    @Bean 
    public AmazonS3 createS3Client(){
        System.out.println(" " + accessKey);
        System.out.println(" " + secretKey);
        System.out.println(" " + endPoint);
        System.out.println(" " + endPointRegion);
        
        BasicAWSCredentials cred= new BasicAWSCredentials(accessKey, secretKey);
        EndpointConfiguration ep = new EndpointConfiguration(endPoint, endPointRegion);
        System.out.println(" " + ep.getServiceEndpoint());
        System.out.println(" " + ep.getSigningRegion());
        return AmazonS3ClientBuilder.standard()
            .withEndpointConfiguration(ep)
            .withCredentials(new AWSStaticCredentialsProvider(cred))
            .build();
    }

}


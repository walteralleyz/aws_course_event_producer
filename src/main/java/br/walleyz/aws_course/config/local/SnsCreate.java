package br.walleyz.aws_course.config.local;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.CreateTopicRequest;
import com.amazonaws.services.sns.model.Topic;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import org.slf4j.Logger;

@Configuration
@Profile("local")
public class SnsCreate {
    private final static Logger LOG = LoggerFactory.getLogger(SnsCreate.class);

    @Bean
    public AmazonSNS snsClient() {
        return AmazonSNSClient.builder()
            .withEndpointConfiguration(new AwsClientBuilder
                .EndpointConfiguration("http://localhost:4566", Regions.US_EAST_1.getName()))
            .withCredentials(new DefaultAWSCredentialsProviderChain())
            .build();
    }

    @Bean(name = "productEventsTopic")
    public Topic snsProductEventsTopic() {
        CreateTopicRequest createTopicRequest = new CreateTopicRequest("product-events");
        String topic = snsClient().createTopic(createTopicRequest).getTopicArn();

        LOG.info("Topic created: {}", topic);
        return new Topic().withTopicArn(topic);
    }
}

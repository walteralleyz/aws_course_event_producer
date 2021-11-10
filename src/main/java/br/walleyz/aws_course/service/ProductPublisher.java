package br.walleyz.aws_course.service;

import br.walleyz.aws_course.enums.EventType;
import br.walleyz.aws_course.model.Envelope;
import br.walleyz.aws_course.model.Product;
import br.walleyz.aws_course.model.ProductEvent;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.model.Topic;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ProductPublisher {

    private static final Logger LOG = LoggerFactory.getLogger(ProductPublisher.class);
    private final AmazonSNS snsClient;
    private final Topic productEventsTopic;
    private final ObjectMapper mapper;

    public ProductPublisher(
        AmazonSNS snsClient,
        @Qualifier("productEventsTopic") Topic productEventsTopic,
        ObjectMapper mapper
    ) {
        this.snsClient = snsClient;
        this.productEventsTopic = productEventsTopic;
        this.mapper = mapper;
    }

    public void publishEvent(Product product, EventType eventType) {
        ProductEvent event = new ProductEvent();
        event.setProductId(product.getId());
        event.setCode(product.getCode());
        event.setUsername("User: " + Math.random());

        Envelope envelope = new Envelope();
        envelope.setEventType(eventType);

        try {
            envelope.setData(mapper.writeValueAsString(event));

            snsClient.publish(
                productEventsTopic.getTopicArn(),
                mapper.writeValueAsString(envelope)
            );

        } catch (JsonProcessingException e) {
            LOG.error(e.getMessage());
        }
    }
}

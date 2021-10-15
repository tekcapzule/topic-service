package com.tekcapsule.topic.domain.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.tekcapsule.topic.domain.model.Topic;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
public class TopicRepositoryImpl implements TopicDynamoRepository {

    private DynamoDBMapper dynamo;

    @Autowired
    public TopicRepositoryImpl(DynamoDBMapper dynamo) {
        this.dynamo = dynamo;
    }

    @Override
    public List<Topic> findAll() {

        return dynamo.scan(Topic.class,new DynamoDBScanExpression());
    }

    @Override
    public Topic findBy( String name) {
        return dynamo.load(Topic.class, name);
    }

    @Override
    public Topic save(Topic topic) {
        dynamo.save(topic);
        return topic;
    }

    @Override
    public void disable(String name) {
        Topic topic = findBy(name);
        if (topic != null) {
            topic.setActive(false);
            dynamo.save(topic);
        }
    }
}

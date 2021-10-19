package com.tekcapsule.topic.domain.repository;

import com.tekcapsule.core.domain.CrudRepository;
import com.tekcapsule.topic.domain.model.Topic;

public interface TopicDynamoRepository extends CrudRepository<Topic, String> {

    void disable(String code);
}

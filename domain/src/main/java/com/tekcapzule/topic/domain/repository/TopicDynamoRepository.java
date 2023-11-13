package com.tekcapzule.topic.domain.repository;

import com.tekcapzule.core.domain.CrudRepository;
import com.tekcapzule.topic.domain.model.Topic;

public interface TopicDynamoRepository extends CrudRepository<Topic, String> {
}

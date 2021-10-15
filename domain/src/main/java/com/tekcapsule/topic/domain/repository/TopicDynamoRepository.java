package com.tekcapsule.topic.domain.repository;

import com.tekcapsule.topic.domain.query.SearchItem;
import in.devstream.core.domain.CrudRepository;
import com.tekcapsule.topic.domain.model.Topic;

import java.util.List;

public interface TopicDynamoRepository extends CrudRepository<Mentor, String> {

    void disableById(String tenantId, String id);
    List<SearchItem> search(String tenantId);
}

package com.tekcapsule.topic.domain.service;

import com.tekcapsule.topic.domain.command.CreateCommand;
import com.tekcapsule.topic.domain.command.DisableCommand;
import com.tekcapsule.topic.domain.command.UpdateCommand;
import com.tekcapsule.topic.domain.query.SearchItem;
import com.tekcapsule.topic.domain.query.SearchQuery;
import com.tekcapsule.topic.domain.model.Topic;

import java.util.List;

public interface TopicService {

    Topic create(CreateCommand createCommand);

    Topic update(UpdateCommand updateCommand);

    void disable(DisableCommand disableCommand);

    List<SearchItem> search(SearchQuery searchQuery);

    Topic get(String tenantId, String userId);
}

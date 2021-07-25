package com.tekcapsule.topic.domain.service;

import com.tekcapsule.topic.domain.command.CreateCommand;
import com.tekcapsule.topic.domain.command.DisableCommand;
import com.tekcapsule.topic.domain.command.UpdateCommand;
import com.tekcapsule.topic.domain.query.SearchItem;
import com.tekcapsule.topic.domain.query.SearchQuery;
import com.tekcapsule.topic.domain.model.Mentor;

import java.util.List;

public interface TopicService {

    Mentor create(CreateCommand createCommand);

    Mentor update(UpdateCommand updateCommand);

    void disable(DisableCommand disableCommand);

    List<SearchItem> search(SearchQuery searchQuery);

    Mentor get(String tenantId, String userId);
}

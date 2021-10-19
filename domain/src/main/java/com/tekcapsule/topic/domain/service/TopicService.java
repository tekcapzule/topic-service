package com.tekcapsule.topic.domain.service;

import com.tekcapsule.topic.domain.command.CreateCommand;
import com.tekcapsule.topic.domain.command.DisableCommand;
import com.tekcapsule.topic.domain.command.UpdateCommand;
import com.tekcapsule.topic.domain.model.Topic;


public interface TopicService {

    Topic create(CreateCommand createCommand);

    Topic update(UpdateCommand updateCommand);

    void disable(DisableCommand disableCommand);

    Topic get(String code);
}

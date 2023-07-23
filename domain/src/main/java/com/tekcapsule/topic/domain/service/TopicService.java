package com.tekcapsule.topic.domain.service;

import com.tekcapsule.topic.domain.command.ApproveCommand;
import com.tekcapsule.topic.domain.command.CreateCommand;
import com.tekcapsule.topic.domain.command.DisableCommand;
import com.tekcapsule.topic.domain.command.UpdateCommand;
import com.tekcapsule.topic.domain.model.Topic;

import java.util.List;


public interface TopicService {

    void create(CreateCommand createCommand);

    void update(UpdateCommand updateCommand);

    void disable(DisableCommand disableCommand);

    List<Topic> findAll();

    Topic findBy(String code);
    void approve(ApproveCommand approveCommand);
}

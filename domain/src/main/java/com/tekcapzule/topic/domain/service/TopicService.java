package com.tekcapzule.topic.domain.service;

import com.tekcapzule.topic.domain.command.ApproveCommand;
import com.tekcapzule.topic.domain.command.CreateCommand;
import com.tekcapzule.topic.domain.command.DisableCommand;
import com.tekcapzule.topic.domain.command.UpdateCommand;
import com.tekcapzule.topic.domain.model.Topic;

import java.util.List;


public interface TopicService {

    void create(CreateCommand createCommand);

    void update(UpdateCommand updateCommand);

    void disable(DisableCommand disableCommand);

    List<Topic> findAll();

    Topic findBy(String code);
    void approve(ApproveCommand approveCommand);
}

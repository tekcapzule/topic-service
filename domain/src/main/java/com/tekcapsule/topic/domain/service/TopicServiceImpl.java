package com.tekcapsule.topic.domain.service;

import com.tekcapsule.topic.domain.command.CreateCommand;
import com.tekcapsule.topic.domain.command.DisableCommand;
import com.tekcapsule.topic.domain.command.UpdateCommand;
import com.tekcapsule.topic.domain.repository.TopicDynamoRepository;
import com.tekcapsule.topic.domain.model.Topic;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TopicServiceImpl implements TopicService {
    private TopicDynamoRepository topicDynamoRepository;

    @Autowired
    public TopicServiceImpl(TopicDynamoRepository topicDynamoRepository) {
        this.topicDynamoRepository = topicDynamoRepository;
    }

    @Override
    public Topic create(CreateCommand createCommand) {

        log.info(String.format("Entering create topic service - Topic Name :{0}", createCommand.getName()));

        Topic topic = Topic.builder()
                .build();

        topic.setAddedOn(createCommand.getExecOn());
        topic.setUpdatedOn(createCommand.getExecOn());
        topic.setAddedBy(createCommand.getExecBy().getUserId());

        return topicDynamoRepository.save(topic);
    }

    @Override
    public Topic update(UpdateCommand updateCommand) {

        log.info(String.format("Entering update topic service - Topic Name:{0}", updateCommand.getName()));

        Topic topic = topicDynamoRepository.findBy(updateCommand.getName());
        if (topic != null) {
            topic.setName(updateCommand.getName());

            topic.setUpdatedOn(updateCommand.getExecOn());
            topic.setUpdatedBy(updateCommand.getExecBy().getUserId());
            topicDynamoRepository.save(topic);
        }
        return topic;
    }

    @Override
    public void disable(DisableCommand disableCommand) {

        log.info(String.format("Entering disable topic service - Topic Id:{0}", disableCommand.getName()));

        topicDynamoRepository.disable(disableCommand.getName());
    }

    @Override
    public Topic get(String name) {

        log.info(String.format("Entering get Topic service - Topic Id:{0}", name));

        return topicDynamoRepository.findBy(name);
    }
}

package com.tekcapsule.topic.domain.service;

import com.tekcapsule.topic.domain.command.CreateCommand;
import com.tekcapsule.topic.domain.command.DisableCommand;
import com.tekcapsule.topic.domain.command.UpdateCommand;
import com.tekcapsule.topic.domain.repository.TopicDynamoRepository;
import com.tekcapsule.topic.domain.model.Topic;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

        log.info(String.format("Entering create topic service - Topic Code :{0}", createCommand.getCode()));

        Topic topic = Topic.builder()
                .code(createCommand.getCode())
                .name(createCommand.getName())
                .description(createCommand.getDescription())
                .imageUrl(createCommand.getImageUrl())
                .capsules(createCommand.getCapsules())
                .keyHighlights(createCommand.getKeyHighlights())
                .active(true)
                .aliases(createCommand.getAliases())
                .build();

        topic.setAddedOn(createCommand.getExecOn());
        topic.setUpdatedOn(createCommand.getExecOn());
        topic.setAddedBy(createCommand.getExecBy().getUserId());

        return topicDynamoRepository.save(topic);
    }

    @Override
    public Topic update(UpdateCommand updateCommand) {

        log.info(String.format("Entering update topic service - Topic Code:{0}", updateCommand.getCode()));

        Topic topic = topicDynamoRepository.findBy(updateCommand.getName());
        if (topic != null) {
            topic.setName(updateCommand.getName());
            topic.setAliases(updateCommand.getAliases());
            topic.setCapsules(updateCommand.getCapsules());
            topic.setImageUrl(updateCommand.getImageUrl());
            topic.setDescription(updateCommand.getDescription());
            topic.setKeyHighlights(updateCommand.getKeyHighlights());
            topic.setUpdatedOn(updateCommand.getExecOn());
            topic.setUpdatedBy(updateCommand.getExecBy().getUserId());
            topicDynamoRepository.save(topic);
        }
        return topic;
    }

    @Override
    public void disable(DisableCommand disableCommand) {

        log.info(String.format("Entering disable topic service - Topic Code:{0}", disableCommand.getCode()));

        topicDynamoRepository.findBy(disableCommand.getCode());
        Topic topic = topicDynamoRepository.findBy(disableCommand.getCode());
        if (topic != null) {
            topic.setActive(false);
            topic.setUpdatedOn(disableCommand.getExecOn());
            topic.setUpdatedBy(disableCommand.getExecBy().getUserId());
            topicDynamoRepository.save(topic);
        }
    }

    @Override
    public List<Topic> findAll() {

        log.info(String.format("Entering findAll Topic service"));

        return topicDynamoRepository.findAll();
    }

    @Override
    public Topic findBy(String code) {

        log.info(String.format("Entering findBy Topic service - Topic code:{0}", code));

        return topicDynamoRepository.findBy(code);
    }


}

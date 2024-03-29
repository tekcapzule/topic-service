package com.tekcapzule.topic.domain.service;

import com.tekcapzule.topic.domain.command.ApproveCommand;
import com.tekcapzule.topic.domain.command.CreateCommand;
import com.tekcapzule.topic.domain.command.DisableCommand;
import com.tekcapzule.topic.domain.command.UpdateCommand;
import com.tekcapzule.topic.domain.model.Status;
import com.tekcapzule.topic.domain.repository.TopicDynamoRepository;
import com.tekcapzule.topic.domain.model.Topic;
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
    public void create(CreateCommand createCommand) {

        log.info(String.format("Entering create topic service - Topic Code :%s", createCommand.getCode()));

        Topic topic = Topic.builder()
                .code(createCommand.getCode())
                .title(createCommand.getTitle())
                .summary(createCommand.getSummary())
                .description(createCommand.getDescription())
                .imageUrl(createCommand.getImageUrl())
                .categories(createCommand.getCategories())
                .status(Status.SUBMITTED)
                .build();

        topic.setAddedOn(createCommand.getExecOn());
        topic.setUpdatedOn(createCommand.getExecOn());
        topic.setAddedBy(createCommand.getExecBy().getUserId());

        topicDynamoRepository.save(topic);
    }

    @Override
    public void update(UpdateCommand updateCommand) {

        log.info(String.format("Entering update topic service - Topic Code:%s", updateCommand.getCode()));

        Topic topic = topicDynamoRepository.findBy(updateCommand.getCode());
        if (topic != null) {
            topic.setTitle(updateCommand.getTitle());
            topic.setSummary(updateCommand.getSummary());
            topic.setCategories(updateCommand.getCategories());
            topic.setImageUrl(updateCommand.getImageUrl());
            topic.setDescription(updateCommand.getDescription());
            topic.setUpdatedOn(updateCommand.getExecOn());
            topic.setUpdatedBy(updateCommand.getExecBy().getUserId());
            topicDynamoRepository.save(topic);
        }
    }

    @Override
    public void disable(DisableCommand disableCommand) {

        log.info(String.format("Entering disable topic service - Topic Code:%s", disableCommand.getCode()));

        topicDynamoRepository.findBy(disableCommand.getCode());
        Topic topic = topicDynamoRepository.findBy(disableCommand.getCode());
        if (topic != null) {
            topic.setStatus(Status.INACTIVE);
            topic.setUpdatedOn(disableCommand.getExecOn());
            topic.setUpdatedBy(disableCommand.getExecBy().getUserId());
            topicDynamoRepository.save(topic);
        }
    }

    @Override
    public void approve(ApproveCommand approveCommand) {
        log.info(String.format("Entering approve topic service -  Topic code:%s", approveCommand.getCode()));

        Topic topic = topicDynamoRepository.findBy(approveCommand.getCode());
        if (topic != null) {
            topic.setStatus(Status.ACTIVE);

            topic.setUpdatedOn(approveCommand.getExecOn());
            topic.setUpdatedBy(approveCommand.getExecBy().getUserId());

            topicDynamoRepository.save(topic);
        }
    }

    @Override
    public List<Topic> findAll() {

        log.info("Entering findAll Topic service");

        return topicDynamoRepository.findAll();
    }

    @Override
    public Topic findBy(String code) {

        log.info(String.format("Entering findBy Topic service - Topic code:%s", code));

        return topicDynamoRepository.findBy(code);
    }


}

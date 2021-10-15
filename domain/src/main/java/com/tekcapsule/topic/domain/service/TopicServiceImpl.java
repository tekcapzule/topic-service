package com.tekcapsule.topic.domain.service;

import com.tekcapsule.topic.domain.command.CreateCommand;
import com.tekcapsule.topic.domain.command.DisableCommand;
import com.tekcapsule.topic.domain.command.UpdateCommand;
import com.tekcapsule.topic.domain.repository.TopicDynamoRepository;
import com.tekcapsule.topic.domain.model.Topic;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
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

        log.info(String.format("Entering create topic service - Tenant Id:{0}, Name:{1}", createCommand.getTenantId(), createCommand.getName().toString()));

        Topic mentor = Topic.builder()
                .active(true)
                .activeSince(DateTime.now(DateTimeZone.UTC).toString())
                .blocked(false)
                .awards(createCommand.getAwards())
                .build();

        mentor.setAddedOn(createCommand.getExecOn());
        mentor.setUpdatedOn(createCommand.getExecOn());
        mentor.setAddedBy(createCommand.getExecBy().getUserId());

        return topicDynamoRepository.save(mentor);
    }

    @Override
    public Topic update(UpdateCommand updateCommand) {

        log.info(String.format("Entering update topic service - Tenant Id:{0}, User Id:{1}", updateCommand.getTenantId(), updateCommand.getUserId()));

        Mentor mentor = mentorRepository.findBy(updateCommand.getTenantId(), updateCommand.getUserId());
        if (mentor != null) {
            mentor.setAwards(updateCommand.getAwards());
            mentor.setHeadLine(updateCommand.getHeadLine());
            mentor.setPublications(updateCommand.getPublications());

            mentor.setUpdatedOn(updateCommand.getExecOn());
            mentor.setUpdatedBy(updateCommand.getExecBy().getUserId());

            topicDynamoRepository.save(mentor);
        }
        return mentor;
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

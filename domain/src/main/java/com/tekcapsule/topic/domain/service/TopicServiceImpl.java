package com.tekcapsule.topic.domain.service;

import com.tekcapsule.topic.domain.command.CreateCommand;
import com.tekcapsule.topic.domain.command.DisableCommand;
import com.tekcapsule.topic.domain.command.UpdateCommand;
import com.tekcapsule.topic.domain.query.SearchItem;
import com.tekcapsule.topic.domain.query.SearchQuery;
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
    private TopicDynamoRepository mentorRepository;

    @Autowired
    public TopicServiceImpl(TopicDynamoRepository mentorRepository) {
        this.mentorRepository = mentorRepository;
    }

    @Override
    public Topic create(CreateCommand createCommand) {

        log.info(String.format("Entering create mentor service - Tenant Id:{0}, Name:{1}", createCommand.getTenantId(), createCommand.getName().toString()));

        Topic mentor = Topic.builder()
                .active(true)
                .activeSince(DateTime.now(DateTimeZone.UTC).toString())
                .blocked(false)
                .awards(createCommand.getAwards())
                .build();

        mentor.setAddedOn(createCommand.getExecOn());
        mentor.setUpdatedOn(createCommand.getExecOn());
        mentor.setAddedBy(createCommand.getExecBy().getUserId());

        return mentorRepository.save(mentor);
    }

    @Override
    public Topic update(UpdateCommand updateCommand) {

        log.info(String.format("Entering update mentor service - Tenant Id:{0}, User Id:{1}", updateCommand.getTenantId(), updateCommand.getUserId()));

        Mentor mentor = mentorRepository.findBy(updateCommand.getTenantId(), updateCommand.getUserId());
        if (mentor != null) {
            mentor.setAwards(updateCommand.getAwards());
            mentor.setHeadLine(updateCommand.getHeadLine());
            mentor.setPublications(updateCommand.getPublications());

            mentor.setUpdatedOn(updateCommand.getExecOn());
            mentor.setUpdatedBy(updateCommand.getExecBy().getUserId());

            mentorRepository.save(mentor);
        }
        return mentor;
    }

    @Override
    public void disable(DisableCommand disableCommand) {

        log.info(String.format("Entering disable mentor service - Tenant Id:{0}, User Id:{1}", disableCommand.getTenantId(), disableCommand.getUserId()));

        mentorRepository.disableById(disableCommand.getTenantId(), disableCommand.getUserId());
    }

    @Override
    public List<SearchItem> search(SearchQuery searchQuery) {

        log.info(String.format("Entering search mentor service - Tenant Id:{0}", searchQuery.getTenantId()));

        return mentorRepository.search(searchQuery.getTenantId());
    }

    @Override
    public Topic get(String tenantId, String userId) {

        log.info(String.format("Entering get mentor service - Tenant Id:{0}, User Id:{1}", tenantId, userId));

        return mentorRepository.findBy(tenantId, userId);
    }
}

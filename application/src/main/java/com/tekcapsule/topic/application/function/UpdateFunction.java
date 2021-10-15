package com.tekcapsule.topic.application.function;

import com.tekcapsule.topic.application.config.AppConstants;
import com.tekcapsule.topic.application.function.input.UpdateInput;
import com.tekcapsule.topic.application.mapper.InputOutputMapper;
import com.tekcapsule.topic.domain.service.TopicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
@Slf4j
public class UpdateFunction implements Function<Message<UpdateInput>, Message<Mentor>> {

    private final TopicService topicService;

    public UpdateFunction(final TopicService topicService) {
        this.topicService = topicService;
    }

    @Override
    public Message<Mentor> apply(Message<UpdateInput> updateInputMessage) {
        UpdateInput updateInput = updateInputMessage.getPayload();

        log.info(String.format("Entering update mentor Function - Tenant Id:{0}, User Id:{1}", updateInput.getTenantId(), updateInput.getUserId()));

        Origin origin = HeaderUtil.buildOriginFromHeaders(updateInputMessage.getHeaders());

        UpdateCommand updateCommand = InputOutputMapper.buildUpdateCommandFromUpdateInput.apply(updateInput, origin);
        Mentor mentor = mentorService.update(updateCommand);
        Map<String, Object> responseHeader = new HashMap();
        responseHeader.put(AppConstants.HTTP_STATUS_CODE_HEADER, HttpStatus.OK.value());

        return new GenericMessage(mentor, responseHeader);

    }
}
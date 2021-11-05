package com.tekcapsule.topic.application.function;

import com.tekcapsule.topic.application.config.AppConstants;
import com.tekcapsule.topic.application.function.input.GetInput;
import com.tekcapsule.topic.domain.model.Topic;
import com.tekcapsule.topic.domain.service.TopicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Function;

@Component
@Slf4j
public class GetAllFunction implements Function<Message<GetInput>, Message<List<Topic>>> {

    private final TopicService topicService;

    public GetAllFunction(final TopicService topicService) {
        this.topicService = topicService;
    }


    @Override
    public Message<List<Topic>> apply(Message<GetInput> getInputMessage) {

        log.info("Entering get all topics Function");

        List<Topic> topics = topicService.findAll();
        Map<String, Object> responseHeader = new HashMap<>();
        responseHeader.put(AppConstants.HTTP_STATUS_CODE_HEADER, HttpStatus.OK.value());
        responseHeader.put(AppConstants.ACCESS_CONTROL_ALLOW_CREDENTIALS, AppConstants.ALLOW_CREDENTIALS);
        responseHeader.put(AppConstants.ACCESS_CONTROL_ALLOW_HEADERS, AppConstants.ALLOW_HEADERS);
        responseHeader.put(AppConstants.ACCESS_CONTROL_ALLOW_METHODS, AppConstants.ALLOW_METHODS);
        responseHeader.put(AppConstants.ACCESS_CONTROL_ALLOW_ORIGIN, AppConstants.ALLOW_ORIGIN);

        return new GenericMessage<>(topics, responseHeader);
    }
}
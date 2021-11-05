package com.tekcapsule.topic.application.function;

import com.tekcapsule.core.domain.EmptyFunctionInput;
import com.tekcapsule.core.utils.HeaderUtil;
import com.tekcapsule.core.utils.Outcome;
import com.tekcapsule.core.utils.Stage;
import com.tekcapsule.topic.application.config.AppConfig;
import com.tekcapsule.topic.application.function.input.GetInput;
import com.tekcapsule.topic.domain.model.Topic;
import com.tekcapsule.topic.domain.service.TopicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Function;

@Component
@Slf4j
public class GetAllFunction implements Function<Message<EmptyFunctionInput>, Message<List<Topic>>> {

    private final TopicService topicService;

    private final AppConfig appConfig;

    public GetAllFunction(final TopicService topicService, final AppConfig appConfig) {
        this.topicService = topicService;
        this.appConfig = appConfig;
    }


    @Override
    public Message<List<Topic>> apply(Message<EmptyFunctionInput> getAllInputMessage) {

        Map<String, Object> responseHeaders = new HashMap<>();
        List<Topic> topics = new ArrayList<>();
        String stage = appConfig.getStage().toUpperCase();
        try {
            log.info("Entering get all topics Function");
            topics = topicService.findAll();
            responseHeaders = HeaderUtil.populateResponseHeaders(responseHeaders, Stage.valueOf(stage), Outcome.SUCCESS);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            responseHeaders = HeaderUtil.populateResponseHeaders(responseHeaders, Stage.valueOf(stage), Outcome.ERROR);
        }
        return new GenericMessage<>(topics, responseHeaders);
    }
}
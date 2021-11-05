package com.tekcapsule.topic.application.function;

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

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
@Slf4j
public class GetFunction implements Function<Message<GetInput>, Message<Topic>> {

    private final TopicService topicService;

    private final AppConfig appConfig;

    public GetFunction(final TopicService topicService, final AppConfig appConfig) {
        this.topicService = topicService;
        this.appConfig = appConfig;
    }


    @Override
    public Message<Topic> apply(Message<GetInput> getInputMessage) {

        Map<String, Object> responseHeaders = new HashMap<>();
        Topic topic = new Topic();

        String stage = appConfig.getStage().toUpperCase();

        try {
            GetInput getInput = getInputMessage.getPayload();
            log.info(String.format("Entering get topic Function -Topic Code:%s", getInput.getCode()));
            topic = topicService.findBy(getInput.getCode());
            if (topic == null) {
                responseHeaders = HeaderUtil.populateResponseHeaders(responseHeaders, Stage.valueOf(stage), Outcome.NOT_FOUND);
                topic = Topic.builder().build();
            } else {
                responseHeaders = HeaderUtil.populateResponseHeaders(responseHeaders, Stage.valueOf(stage), Outcome.SUCCESS);
            }
        } catch (Exception ex) {
            log.error(ex.getMessage());
            responseHeaders = HeaderUtil.populateResponseHeaders(responseHeaders, Stage.valueOf(stage), Outcome.ERROR);
        }
        return new GenericMessage<>(topic, responseHeaders);
    }
}
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

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
@Slf4j
public class GetFunction implements Function<Message<GetInput>, Message<Topic>> {

    private final TopicService topicService;

    public GetFunction(final TopicService topicService) {
        this.topicService = topicService;
    }


    @Override
    public Message<Topic> apply(Message<GetInput> getInputMessage) {
        GetInput getInput = getInputMessage.getPayload();

        log.info(String.format("Entering get topic Function -User Id:{0}", getInput.getName()));

        Topic topic = topicService.get(getInput.getName());
        Map<String, Object> responseHeader = new HashMap();
        if (topic == null) {
            responseHeader.put(AppConstants.HTTP_STATUS_CODE_HEADER, HttpStatus.NOT_FOUND.value());
            topic = Topic.builder().build();
        } else {
            responseHeader.put(AppConstants.HTTP_STATUS_CODE_HEADER, HttpStatus.OK.value());
        }
        return new GenericMessage(topic, responseHeader);
    }
}
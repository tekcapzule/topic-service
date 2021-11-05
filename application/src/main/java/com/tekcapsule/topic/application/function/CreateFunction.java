package com.tekcapsule.topic.application.function;

import com.tekcapsule.core.domain.Origin;
import com.tekcapsule.core.utils.HeaderUtil;
import com.tekcapsule.topic.application.config.AppConstants;
import com.tekcapsule.topic.application.function.input.CreateInput;
import com.tekcapsule.topic.application.mapper.InputOutputMapper;
import com.tekcapsule.topic.domain.command.CreateCommand;
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
public class CreateFunction implements Function<Message<CreateInput>, Message<Void>> {

    private final TopicService topicService;

    public CreateFunction(final TopicService topicService) {
        this.topicService = topicService;
    }


    @Override
    public Message<Void> apply(Message<CreateInput> createInputMessage) {

        CreateInput createInput = createInputMessage.getPayload();

        log.info(String.format("Entering create topic Function - Topic Code:%s", createInput.getCode()));

        Origin origin = HeaderUtil.buildOriginFromHeaders(createInputMessage.getHeaders());

        CreateCommand createCommand = InputOutputMapper.buildCreateCommandFromCreateInput.apply(createInput, origin);
        topicService.create(createCommand);
        Map<String, Object> responseHeader = new HashMap<>();

        responseHeader.put(AppConstants.HTTP_STATUS_CODE_HEADER, HttpStatus.OK.value());
        responseHeader = HeaderUtil.populateCorsHeaders(responseHeader);

        return new GenericMessage(new HashMap<String, Object>().put(AppConstants.HTTP_STATUS_CODE_HEADER, HttpStatus.OK.value()), responseHeader);
    }
}
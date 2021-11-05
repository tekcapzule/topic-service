package com.tekcapsule.topic.application.function;

import com.tekcapsule.core.domain.Origin;
import com.tekcapsule.core.utils.HeaderUtil;
import com.tekcapsule.topic.application.config.AppConstants;
import com.tekcapsule.topic.application.function.input.DisableInput;
import com.tekcapsule.topic.application.mapper.InputOutputMapper;
import com.tekcapsule.topic.domain.command.DisableCommand;
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
public class DisableFunction implements Function<Message<DisableInput>, Message<Void>> {

    private final TopicService topicService;

    public DisableFunction(final TopicService topicService) {
        this.topicService = topicService;
    }


    @Override
    public Message<Void> apply(Message<DisableInput> disableInputMessage) {

        DisableInput disableInput = disableInputMessage.getPayload();

        log.info(String.format("Entering disable topic Function - Topic Code:%s", disableInput.getCode()));

        Origin origin = HeaderUtil.buildOriginFromHeaders(disableInputMessage.getHeaders());

        DisableCommand disableCommand = InputOutputMapper.buildDisableCommandFromDisableInput.apply(disableInput, origin);
        topicService.disable(disableCommand);
        Map<String, Object> responseHeader = new HashMap();
        responseHeader.put(AppConstants.HTTP_STATUS_CODE_HEADER, HttpStatus.OK.value());
        responseHeader = HeaderUtil.populateCorsHeaders(responseHeader);

        return new GenericMessage( new HashMap<String, Object>().put(AppConstants.HTTP_STATUS_CODE_HEADER, HttpStatus.OK.value()), responseHeader);
    }
}

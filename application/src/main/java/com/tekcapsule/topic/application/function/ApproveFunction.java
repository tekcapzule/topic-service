package com.tekcapsule.topic.application.function;

import com.tekcapsule.core.domain.Origin;
import com.tekcapsule.core.utils.HeaderUtil;
import com.tekcapsule.core.utils.Outcome;
import com.tekcapsule.core.utils.PayloadUtil;
import com.tekcapsule.core.utils.Stage;
import com.tekcapsule.topic.application.config.AppConfig;
import com.tekcapsule.topic.application.function.input.ApproveTopicInput;
import com.tekcapsule.topic.application.mapper.InputOutputMapper;
import com.tekcapsule.topic.domain.command.ApproveCommand;
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
public class ApproveFunction implements Function<Message<ApproveTopicInput>, Message<Void>> {

    private final TopicService topicService;

    private final AppConfig appConfig;

    public ApproveFunction(final TopicService topicService, final AppConfig appConfig) {
        this.topicService = topicService;
        this.appConfig = appConfig;
    }

    @Override
    public Message<Void> apply(Message<ApproveTopicInput> approveTopicInputMessage) {
        Map<String, Object> responseHeaders = new HashMap<>();
        Map<String, Object> payload = new HashMap<>();
        String stage = appConfig.getStage().toUpperCase();
        try {
            ApproveTopicInput approveTopicInput = approveTopicInputMessage.getPayload();
            log.info(String.format("Entering approve topic Function -  Topic code:%s", approveTopicInput.getCode()));
            Origin origin = HeaderUtil.buildOriginFromHeaders(approveTopicInputMessage.getHeaders());
            ApproveCommand approveCommand = InputOutputMapper.buildApproveCommandFromApproveInput.apply(approveTopicInput, origin);
            topicService.approve(approveCommand);
            responseHeaders = HeaderUtil.populateResponseHeaders(responseHeaders, Stage.valueOf(stage), Outcome.SUCCESS);
            payload = PayloadUtil.composePayload(Outcome.SUCCESS);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            responseHeaders = HeaderUtil.populateResponseHeaders(responseHeaders, Stage.valueOf(stage), Outcome.ERROR);
            payload = PayloadUtil.composePayload(Outcome.ERROR);
        }
        return new GenericMessage(payload, responseHeaders);

    }
}
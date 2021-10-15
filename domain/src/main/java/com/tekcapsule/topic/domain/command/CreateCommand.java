package com.tekcapsule.topic.domain.command;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tekcapsule.core.domain.Command;
import com.tekcapsule.topic.domain.model.*;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
public class CreateCommand extends Command {
    private String name;
    private String description;
    private String imageUrl;
    private List<String> aliases;
    private List<String> keyHighlights;
    private List<String> capsules;
    private Category category;
}

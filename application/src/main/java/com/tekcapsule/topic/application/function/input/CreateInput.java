package com.tekcapsule.topic.application.function.input;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tekcapsule.topic.domain.model.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
public class CreateInput {
    private String code;
    private String title;
    private List<Category> categories;
    private String summary;
    private String description;
    private String imageUrl;
    private List<String> aliases;
}
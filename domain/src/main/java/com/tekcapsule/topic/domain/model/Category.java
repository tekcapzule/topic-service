package com.tekcapsule.topic.domain.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@Builder
public class Category {
    @DynamoDBAttribute(attributeName ="name")
    private String name;
    @DynamoDBAttribute(attributeName ="summary")
    private String summary;
    @DynamoDBAttribute(attributeName ="description")
    private String description;
    @DynamoDBAttribute(attributeName ="imageURL")
    private String imageURL;
}
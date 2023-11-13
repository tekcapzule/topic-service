package com.tekcapzule.topic.domain.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;


@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamoDBDocument
public class Category {
    @DynamoDBAttribute(attributeName ="title")
    private String title;
    @DynamoDBAttribute(attributeName ="summary")
    private String summary;
    @DynamoDBAttribute(attributeName ="imageURL")
    private String imageURL;
}
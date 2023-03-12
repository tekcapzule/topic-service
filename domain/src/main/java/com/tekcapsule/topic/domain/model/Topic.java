package com.tekcapsule.topic.domain.model;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tekcapsule.core.domain.AggregateRoot;
import com.tekcapsule.core.domain.BaseDomainEntity;
import lombok.*;

import java.util.List;


@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@EqualsAndHashCode(callSuper = true)
@DynamoDBTable(tableName = "Topic")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Topic extends BaseDomainEntity implements AggregateRoot {

    @DynamoDBHashKey(attributeName = "code")
    private String code;
    @DynamoDBAttribute(attributeName = "name")
    private String name;
    @DynamoDBAttribute(attributeName = "categories")
    private List<Category> categories;
    @DynamoDBAttribute(attributeName = "summary")
    private String summary;
    @DynamoDBAttribute(attributeName = "description")
    private String description;
    @DynamoDBAttribute(attributeName = "imageUrl")
    private String imageUrl;
    @DynamoDBAttribute(attributeName = "status")
    private String status;
}
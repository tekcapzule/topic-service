package com.tekcapsule.topic.domain.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
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

    @DynamoDBHashKey(attributeName="code")
    private String code;
    @DynamoDBRangeKey(attributeName="status")
    private String status;
    @DynamoDBAttribute(attributeName="name")
    private String name;
    @DynamoDBAttribute(attributeName = "description")
    private String description;
    @DynamoDBAttribute(attributeName = "imageUrl")
    private String imageUrl;
    @DynamoDBAttribute(attributeName = "aliases")
    private List<String> aliases;
    @DynamoDBAttribute(attributeName = "keyHighlights")
    private List<String> keyHighlights;
    @DynamoDBAttribute(attributeName = "capsules")
    private List<String> capsules;

}
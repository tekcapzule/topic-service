package com.tekcapsule.topic.domain.utils;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tekcapsule.topic.domain.model.Category;

import java.io.DataInput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CategoryConverter implements DynamoDBTypeConverter<List<Map<String, AttributeValue>>, List<Category>> {
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public List<Map<String, AttributeValue>> convert(List<Category> categories) {
        List<Map<String, AttributeValue>> categoryMaps = new ArrayList<>();
        for (Category category : categories) {
            Map<String, AttributeValue> categoryMap = new HashMap<>();
            try {
                categoryMap.put("object", new AttributeValue(mapper.writeValueAsString(category)));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            categoryMaps.add(categoryMap);
        }
        return categoryMaps;
    }

    @Override
    public List<Category> unconvert(List<Map<String, AttributeValue>> categoryMaps) {
        List<Category> categories = new ArrayList<>();
        for (Map<String, AttributeValue> conceptMap : categoryMaps) {
            Category category = null;
            try {
                category = mapper.readValue((DataInput) conceptMap.get("object"), Category.class);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            categories.add(category);
        }
        return categories;
    }
}


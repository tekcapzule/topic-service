package com.tekcapsule.topic.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum Category {
    EMERGING_TECH("Emerging Tech"),
    TECH_IN_DEMAND("Tech In-demand"),
    SOFTWARE_DEVELOPMENT("Software Development");

    @Getter
    private String value;
}
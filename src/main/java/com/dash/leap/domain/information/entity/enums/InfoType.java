package com.dash.leap.domain.information.entity.enums;

import lombok.Getter;

@Getter
public enum InfoType {
    ECONOMY("경제"), CAREER("진로"), HOUSING("주거");

    private final String infoType;

    InfoType(String infoType) {
        this.infoType = infoType;
    }
}

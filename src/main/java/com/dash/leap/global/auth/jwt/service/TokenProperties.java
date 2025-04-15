package com.dash.leap.global.auth.jwt.service;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "jwt")
public record TokenProperties(String secretKey) {
}

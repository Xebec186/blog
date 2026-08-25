package com.xebec.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ErrorResponse {
    private int status;
    private String message;
    private String path;
    private Map<String, String> fieldErrors;

    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();
}
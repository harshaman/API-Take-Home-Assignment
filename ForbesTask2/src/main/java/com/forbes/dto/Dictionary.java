package com.forbes.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Dictionary {
    private Map<String,List<String>> dictionary;
    private String error;
}

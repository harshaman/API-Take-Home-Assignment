package com.forbes.dto;

import lombok.Data;

import java.util.List;

@Data
public class GoodRequest {
    private String story;
    private List<UnMatchedWords> unmatchedWords;
}

package com.forbes.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class DictionaryItems {
    private Map<String,List<String>> list;
}

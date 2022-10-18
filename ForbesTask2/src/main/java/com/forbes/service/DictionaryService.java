package com.forbes.service;

import com.forbes.dto.Dictionary;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public interface DictionaryService {
    Dictionary getAllItems() throws IOException;
    List<String> addItems( List<String> wordsToAdd) throws IOException;
    List<String> removeItems(List<String> wordsToRemove) throws  IOException;

}

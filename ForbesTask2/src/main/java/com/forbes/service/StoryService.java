package com.forbes.service;

import com.forbes.dto.UnMatchedWords;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public interface StoryService {
    /**
     * Returns a list of closest matching words which are not in the dictionary, with a threshold of 2(default).
     * @param words
     * @return
     * @throws IOException
     */
    List<UnMatchedWords> getClosestMatchingWords(String[] words) throws IOException;
}

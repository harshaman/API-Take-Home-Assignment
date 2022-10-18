package com.forbes.service.impl;

import com.forbes.dto.UnMatchedWords;
import com.forbes.service.StoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class StoryServiceImpl implements StoryService {

    private static final Logger LOG = LoggerFactory.getLogger(StoryServiceImpl.class);

    private static final String FILEPATH = "src/main/resources/data/dictionary.txt";
    @Override
    public List<UnMatchedWords> getClosestMatchingWords(String[] words) throws IOException {
        List<UnMatchedWords> list=new ArrayList<>();
        List<String> dictWords = readFile(FILEPATH);
        for(String word: words) {
            word= word.endsWith(".") ? word.substring(0, word.length() - 1) : word;
            word= word.endsWith(",") ? word.substring(0, word.length() - 1) : word;
            if(!dictWords.contains(word))
                for(String d: dictWords) {
                    if(!word.equalsIgnoreCase(d) && findClosest(word,d)<=2){
                        UnMatchedWords unMatchedWords = new UnMatchedWords();
                        unMatchedWords.setWord(word);
                        unMatchedWords.setCloseMatch(d);
                        list.add(unMatchedWords);
                    }
                }
        }
        return list;
    }

    private int findClosest(String word1, String word2) {
        int len1 = word1.length();
        int len2 = word2.length();
        int m,n,p;

        int[][] cost = new int[len1 + 1][len2 + 1];
        for(int i = 0; i <= len1; i++) cost[i][0] = i;
        for(int i = 1; i <= len2; i++) cost[0][i] = i;

        for(int i = 0; i < len1; i++) {
            for(int j = 0; j < len2; j++) {
                if(word1.charAt(i) == word2.charAt(j)) cost[i + 1][j + 1] = cost[i][j];
                else {
                    m = cost[i][j];
                    n = cost[i][j + 1];
                    p = cost[i + 1][j];
                    cost[i + 1][j + 1] = m < n ? (m < p ? m : p) : (n < p ? n : p);
                    cost[i + 1][j + 1]++;
                }
            }
        }
        return cost[len1][len2];
    }

    private List<String> readFile(String file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line = null;
        List<String> list = new ArrayList<>();

        try {
            while((line = reader.readLine()) != null) list.add(line);
            return list;
        } finally {
            reader.close();
        }
    }
}

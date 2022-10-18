package com.forbes.service.impl;

import com.forbes.dto.Dictionary;
import com.forbes.service.DictionaryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 */
@Service
public class DictionaryServiceImpl implements DictionaryService {

    private static final Logger LOG = LoggerFactory.getLogger(DictionaryServiceImpl.class);

    private static final String FILEPATH = "src/main/resources/data/dictionary.txt";
    @Override
    public Dictionary getAllItems() throws IOException {
        Dictionary dictionary = new Dictionary();
        Map<String, List<String>> dict = new HashMap<>();

        dict.put("list",readFile(FILEPATH));
        dictionary.setDictionary(dict);
        return dictionary;
    }

    @Override
    public List<String> addItems(List<String> wordsToAdd) throws IOException {
        List<String> dictionaryItems  = readFile(FILEPATH);
        List<String> duplicateWords = new ArrayList<>();
        for(String s: wordsToAdd) {
            if(dictionaryItems.contains(s)) {
                //duplicate entry
                duplicateWords.add(s);
            } else {
                //write to file
                writeToFile(FILEPATH, s+"\n");
            }
        }
        return duplicateWords;
    }

    @Override
    public List<String> removeItems(List<String> wordsToRemove) throws IOException {
        List<String> dictionaryItems  = readFile(FILEPATH);
        List<String> wordsNotFound = new ArrayList<>();
        for(String s: wordsToRemove) {
            if(!dictionaryItems.contains(s)) {
                //entry not found
                wordsNotFound.add(s);
            } else {
                //remove from file
                dictionaryItems.remove(s);
            }
            PrintWriter writer = new PrintWriter
                    (new File(FILEPATH));
            for(String word: dictionaryItems) writer.append(word+"\n");
            writer.flush();
        }
        return wordsNotFound;
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

    private void writeToFile(String file, String content) throws IOException {
        Path path = Paths.get(file);
        //for(String content: contents) {
            Files.write(path, content.getBytes(StandardCharsets.UTF_8),
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND);
        //}

    }

}

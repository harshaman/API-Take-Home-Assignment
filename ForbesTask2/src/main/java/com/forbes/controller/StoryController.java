package com.forbes.controller;

import com.forbes.dto.ErrorsDto;
import com.forbes.dto.GoodRequest;
import com.forbes.dto.StoryRequest;
import com.forbes.dto.UnMatchedWords;
import com.forbes.service.StoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/story")
public class StoryController {

    @Autowired
    private StoryService storyService;

    private static final Logger LOG = LoggerFactory.getLogger(StoryController.class);

    @PostMapping
    public ResponseEntity<Object> findClosestMatch(@RequestBody final StoryRequest storyRequest) {
        //check if request is invalid
        if(storyRequest==null || storyRequest.getStory()==null) {
            ErrorsDto errors = buildErrorResponse("bad request");
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        //split the words in the request
        String[] words = storyRequest.getStory().split(" ");
        try{
            List<UnMatchedWords> list = storyService.getClosestMatchingWords(words);
            GoodRequest response = new GoodRequest();
            response.setStory(storyRequest.getStory());
            response.setUnmatchedWords(list);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch(IOException e) {
            LOG.error(String.format("Exception occurred: %s", e.getMessage()));
            ErrorsDto error = buildErrorResponse("Exception occurred");
            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private ErrorsDto buildErrorResponse(String errorMsg) {
        ErrorsDto error= new ErrorsDto();
        error.setError(errorMsg);
        return error;
    }
}

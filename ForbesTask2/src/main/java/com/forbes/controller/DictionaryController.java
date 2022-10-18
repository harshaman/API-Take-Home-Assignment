package com.forbes.controller;

import com.forbes.dto.Dictionary;
import com.forbes.dto.ErrorsDto;
import com.forbes.service.DictionaryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/dictionary")
public class DictionaryController {

    private static final Logger LOG = LoggerFactory.getLogger(DictionaryController.class.getName());

    @Autowired
    private DictionaryService dictionaryService;

    /**
     * Fetches and lists all items from dictionary
     * @return
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> listDictionaryItems() {
        try {
            return new ResponseEntity<>(dictionaryService.getAllItems(), HttpStatus.OK);
        } catch (IOException e) {
            LOG.error(String.format("Unable to retrieve data: %s", e.getMessage()));
            ErrorsDto error = buildErrorResponse("Unable to retrieve data");
            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Add items to dictionary
     * @param request
     * @return
     */
    @PostMapping
    public ResponseEntity<Object> addItems(@RequestBody final Dictionary request) {
        try {
            //check if request is invalid
            if(request.getDictionary()==null|| request.getDictionary().get("add")==null) {
                ErrorsDto error = buildErrorResponse("bad request");
                return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
            }

            List<String> duplicateWords = dictionaryService.addItems(request.getDictionary().get("add"));

            if(duplicateWords.isEmpty()){
                //no duplicate entry
                return new ResponseEntity<>(null,HttpStatus.ACCEPTED);
            } else {
                Dictionary response = getDictionary("add", duplicateWords, "duplicate entry");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }

        } catch (Exception e) {
            LOG.error(String.format("Unable to add data: %s", e.getMessage()));
            ErrorsDto error = buildErrorResponse("Unable to add data");
            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Removes items from dictionary
     * @param request
     * @return
     */
    @DeleteMapping
    public ResponseEntity<Object> deleteItems(@RequestBody final Dictionary request) {
        try {
            //check if request is invalid
            if(request.getDictionary()==null|| request.getDictionary().get("remove")==null) {
                ErrorsDto error = buildErrorResponse("bad request");
                return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
            }
            List<String> wordsNotFound = dictionaryService.removeItems(request.getDictionary().get("remove"));

            if(wordsNotFound.isEmpty()){
                //all words requested for delete are present in the dictionary
                return new ResponseEntity<>(null,HttpStatus.ACCEPTED);
            } else {
                Dictionary response = getDictionary("remove", wordsNotFound, "entry not found");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            LOG.error(String.format("Unable to delete data: %s", e.getMessage()));
            ErrorsDto error = buildErrorResponse("Unable to delete data");
            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Builds and returns a Dictionary object
     * @param remove
     * @param words
     * @param errMsg
     * @return
     */
    private Dictionary getDictionary(String remove, List<String> words, String errMsg) {
        Dictionary response = new Dictionary();
        Map<String, List<String>> responseMap = new HashMap<>();
        responseMap.put(remove, words);
        response.setDictionary(responseMap);
        if(!errMsg.isEmpty()) response.setError(errMsg);
        return response;
    }

    /**
     * Returns an ErrorsDto object
     * @param errorMsg
     * @return
     */
    private ErrorsDto buildErrorResponse(String errorMsg) {
        ErrorsDto error= new ErrorsDto();
        error.setError(errorMsg);
        return error;
    }
}

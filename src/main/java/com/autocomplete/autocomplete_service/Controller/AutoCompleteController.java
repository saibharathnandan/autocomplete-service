package com.autocomplete.autocomplete_service.Controller;

import com.autocomplete.autocomplete_service.Model.ErrorResponse;
import com.autocomplete.autocomplete_service.Model.SuccessResponse;
import com.autocomplete.autocomplete_service.Service.AutoCompleteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.regex.Pattern;

@RestController
public class AutoCompleteController {
    private final AutoCompleteService autocompleteService;
    private static final Logger logger = LoggerFactory.getLogger(AutoCompleteController.class);

    @Autowired
    public AutoCompleteController(AutoCompleteService autocompleteService) {
        this.autocompleteService = autocompleteService;
    }

    @GetMapping("/autocomplete")
    public ResponseEntity<?> autocomplete(@RequestParam String prefix) {

        if (prefix == null || prefix.trim().isEmpty()) {
            logger.error("Autocomplete request received with empty prefix.");
            return ResponseEntity.badRequest().body(new ErrorResponse("Prefix cannot be empty"));
        }

        if (!Pattern.matches("[a-zA-Z ]+", prefix)) {
            logger.error("Autocomplete request received with invalid prefix: {}", prefix);
            return ResponseEntity.badRequest().body(new ErrorResponse("Prefix must only contain alphabetic characters and spaces"));
        }

        logger.info("Autocomplete request received with prefix: {}", prefix);
        List<String> results = autocompleteService.getAutocompleteSuggestions(prefix);

        if (results.isEmpty()) {
            logger.info("No results found for prefix: {}", prefix);
            return ResponseEntity.status(404).body(new ErrorResponse("No matching names found"));
        }

        logger.info("Found {} matching names for prefix: {}", results.size(), prefix);
        return ResponseEntity.ok(new SuccessResponse(results));
    }
}


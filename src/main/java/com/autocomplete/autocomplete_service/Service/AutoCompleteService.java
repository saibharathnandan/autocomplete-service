package com.autocomplete.autocomplete_service.Service;

import com.autocomplete.autocomplete_service.Entity.Name;
import com.autocomplete.autocomplete_service.Repository.NameRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AutoCompleteService implements ApplicationListener<ApplicationReadyEvent> {

    private static final Logger logger = LoggerFactory.getLogger(AutoCompleteService.class);
    private final  NameRepository nameRepository;
    private Trie trie;

    @Autowired
    public AutoCompleteService(NameRepository nameRepository) {
        this.nameRepository = nameRepository;
    }

    public List<String> getAutocompleteSuggestions(String prefix) {
        if (prefix == null || prefix.trim().isEmpty()) {
            logger.error("Autocomplete service received an empty prefix.");
            throw new IllegalArgumentException("Prefix cannot be null or empty");
        }

        logger.debug("Searching for names with prefix: {}", prefix);
        return trie.findByPrefix(prefix.toLowerCase());
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        this.trie = new Trie();
        List<Name> names = nameRepository.findAll();
        for (Name name : names) {
            trie.insert(name.getName());
        }
    }
}


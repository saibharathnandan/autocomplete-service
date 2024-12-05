package com.autocomplete.autocomplete_service.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Trie {

    private static final Logger logger = LoggerFactory.getLogger(Trie.class);
    private TrieNode root;

    public Trie() {
        root = new TrieNode();
    }

    public void insert(String word) {
        logger.debug("loading into cache");
        TrieNode node = root;
        for (char c : word.toLowerCase().toCharArray()) {
            node = node.children.computeIfAbsent(c, k -> new TrieNode());
        }
        node.isEndOfWord = true;
        node.originalWords.add(word);
    }

    public List<String> findByPrefix(String prefix) {
        logger.debug("searching for prefix {}",prefix);
        TrieNode node = root;
        for (char c : prefix.toLowerCase().toCharArray()) {
            node = node.children.get(c);
            if (node == null) {
                return new ArrayList<>();
            }
        }
        return collectWords(node);
    }

    private List<String> collectWords(TrieNode node) {
        List<String> words = new ArrayList<>();
        if (node.isEndOfWord) {
            words.addAll(node.originalWords);
        }
        for (TrieNode childNode : node.children.values()) {
            words.addAll(collectWords(childNode));
        }
        return words;
    }

    private static class TrieNode {
        private boolean isEndOfWord;
        private final Map<Character, TrieNode> children = new HashMap<>();
        private final List<String> originalWords = new ArrayList<>();
    }
}

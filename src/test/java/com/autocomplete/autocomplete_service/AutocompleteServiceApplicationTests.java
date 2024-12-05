package com.autocomplete.autocomplete_service;

import com.autocomplete.autocomplete_service.Controller.AutoCompleteController;
import com.autocomplete.autocomplete_service.Service.AutoCompleteService;
import com.autocomplete.autocomplete_service.Service.Trie;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
public class AutocompleteServiceApplicationTests {


    @Mock
    private AutoCompleteService nameService;

    @InjectMocks
    private AutoCompleteController nameController;
	private MockMvc mockMvc;


    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(nameController).build();
    }

	@Test
	public void testAutocomplete_emptyPrefix() throws Exception {
		mockMvc.perform(get("/autocomplete?prefix="))
				.andExpect(status().isBadRequest())
				.andExpect(content().json("{\"message\":\"Prefix cannot be empty\"}"));
	}

	@Test
	public void testAutocomplete_invalidPrefix() throws Exception {
		mockMvc.perform(get("/autocomplete?prefix=ban123"))
				.andExpect(status().isBadRequest())
				.andExpect(content().json("{\"message\":\"Prefix must only contain alphabetic characters and spaces\"}"));
	}

	@Test
	public void testAutocomplete_noMatches() throws Exception {
		when(nameService.getAutocompleteSuggestions("xyz")).thenReturn(List.of());
		mockMvc.perform(get("/autocomplete?prefix=xyz"))
				.andExpect(status().isNotFound())
				.andExpect(content().json("{\"message\":\"No matching names found\"}"));
	}

}
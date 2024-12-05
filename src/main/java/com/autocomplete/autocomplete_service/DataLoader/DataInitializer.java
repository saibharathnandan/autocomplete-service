package com.autocomplete.autocomplete_service.DataLoader;

import com.autocomplete.autocomplete_service.Entity.Name;
import com.autocomplete.autocomplete_service.Repository.NameRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@Component
public class DataInitializer implements ApplicationRunner {
    private final NameRepository nameRepository;

    public DataInitializer(NameRepository nameRepository) {
        this.nameRepository = nameRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        ClassPathResource resource = new ClassPathResource("Data/BoyNames.txt");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    Name name = new Name();
                    name.setName(line.trim());
                    nameRepository.save(name);
                }
            }
        }
    }

}

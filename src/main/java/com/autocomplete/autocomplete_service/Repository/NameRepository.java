package com.autocomplete.autocomplete_service.Repository;

import com.autocomplete.autocomplete_service.Entity.Name;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NameRepository extends JpaRepository<Name,Long> {

}

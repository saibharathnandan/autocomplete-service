package com.autocomplete.autocomplete_service.Model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class SuccessResponse {
    private List<String> data;

    public SuccessResponse(List<String> data) {
        this.data = data;
    }

}

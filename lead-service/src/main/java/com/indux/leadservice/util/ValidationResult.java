package com.indux.leadservice.util;

import com.indux.leadservice.dto.LeadDTO;
import lombok.Getter;

import java.util.List;

@Getter
public class ValidationResult {

    private final List<LeadDTO> validLeads;
    private final List<String> errors;

    public ValidationResult(List<LeadDTO> validLeads, List<String> errors) {
        this.validLeads = validLeads;
        this.errors = errors;
    }

}
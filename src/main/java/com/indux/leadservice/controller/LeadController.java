package com.indux.leadservice.controller;

import com.indux.leadservice.model.Lead;
import com.indux.leadservice.service.LeadService;
import com.indux.leadservice.util.ValidationResult;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/leads")
public class LeadController {

    private final LeadService leadService;

    public LeadController(LeadService leadService) {
        this.leadService = leadService;
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ValidationResult uploadCSV(@RequestParam("file") MultipartFile file) {
        return leadService.processCSV(file);
    }

    @GetMapping
    public List<Lead> getAllLeads() {
        return leadService.getAllLeads();
    }
}
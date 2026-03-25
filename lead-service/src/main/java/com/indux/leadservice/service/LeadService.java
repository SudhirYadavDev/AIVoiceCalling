package com.indux.leadservice.service;

import com.indux.leadservice.dto.LeadDTO;
import com.indux.leadservice.model.Lead;
import com.indux.leadservice.repository.LeadRepository;
import com.indux.leadservice.util.CSVHelper;
import com.indux.leadservice.util.ValidationResult;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LeadService {

    private final LeadRepository leadRepository;
    public LeadService(LeadRepository leadRepository) {
        this.leadRepository = leadRepository;
    }

    public Lead saveLead(LeadDTO dto) {
        Lead lead = mapToEntity(dto);
        return leadRepository.save(lead);
    }

    public List<Lead> saveAll(List<LeadDTO> dtos) {
        List<Lead> leads = dtos.stream()
                .map(this::mapToEntity)
                .collect(Collectors.toList());
        return leadRepository.saveAll(leads);
    }

    public List<LeadDTO> getAllLeadsDTO() {
        return leadRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    public ValidationResult processCSV(MultipartFile file) {

        ValidationResult result = CSVHelper.parseCSV(file);

        List<LeadDTO> finalValidLeads = result.getValidLeads();
        List<String> errors = result.getErrors();

        List<LeadDTO> filteredLeads = finalValidLeads.stream()
                .filter(dto -> {
                    boolean existsByEmail = leadRepository.findByEmail(dto.getEmail()).isPresent();
                    boolean existsByPhone = leadRepository.findByPhone(dto.getPhone()).isPresent();

                    if (existsByEmail) {
                        errors.add("Duplicate in DB: Email already exists → " + dto.getEmail());
                        return false;
                    }
                    if (existsByPhone) {
                        errors.add("Duplicate in DB: Phone already exists → " + dto.getPhone());
                        return false;
                    }
                    return true;
                })
                .toList();

        if (!filteredLeads.isEmpty()) {
            saveAll(filteredLeads);
        }
        return new ValidationResult(filteredLeads, errors);
    }

    private Lead mapToEntity(LeadDTO dto) {
        Lead lead = new Lead();
        lead.setName(dto.getName());
        lead.setPhone(dto.getPhone());
        lead.setEmail(dto.getEmail());
        lead.setRegion(dto.getRegion());
        return lead;
    }

    private LeadDTO mapToDTO(Lead lead) {
        LeadDTO dto = new LeadDTO();
        dto.setName(lead.getName());
        dto.setEmail(lead.getEmail());
        dto.setPhone(lead.getPhone());
        dto.setRegion(lead.getRegion());
        return dto;
    }
}
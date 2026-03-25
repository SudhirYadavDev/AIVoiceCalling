package com.indux.leadservice.util;

import com.indux.leadservice.dto.LeadDTO;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStreamReader;
import java.util.*;

public class CSVHelper {

    public static ValidationResult parseCSV(MultipartFile file) {

        List<LeadDTO> validLeads = new ArrayList<>();
        List<String> errors = new ArrayList<>();

        Set<String> uniquePhones = new HashSet<>();
        Set<String> uniqueEmails = new HashSet<>();

        int rowNumber = 2; // header skip

        try (
                CSVParser csvParser = new CSVParser(
                        new InputStreamReader(file.getInputStream()),
                        CSVFormat.DEFAULT.builder()
                                .setHeader()
                                .setSkipHeaderRecord(true)
                                .setIgnoreHeaderCase(true)
                                .setTrim(true)
                                .build()
                )
        ) {
            for (CSVRecord record : csvParser) {

                try {
                    LeadDTO lead = LeadDTO.builder()
                            .name(record.get("name"))
                            .email(record.get("email"))
                            .phone(record.get("phone"))
                            .region(record.get("region"))
                            .build();

                    String error = LeadValidator.validate(lead, uniquePhones, uniqueEmails);

                    if (error == null) {
                        validLeads.add(lead);
                    } else {
                        errors.add("Row " + rowNumber + ": " + error);
                    }

                } catch (Exception ex) {
                    errors.add("Row " + rowNumber + ": Invalid format");
                }

                rowNumber++;
            }

        } catch (Exception e) {
            throw new RuntimeException("Failed to parse CSV file: " + e.getMessage());
        }

        return new ValidationResult(validLeads, errors);
    }
}
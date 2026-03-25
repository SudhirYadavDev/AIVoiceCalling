package com.indux.leadservice.util;

import com.indux.leadservice.dto.LeadDTO;

import java.util.Set;
import java.util.regex.Pattern;

public class LeadValidator {

    private static final Pattern EMAIL_REGEX =
            Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    private static final Pattern PHONE_REGEX =
            Pattern.compile("^\\+?[0-9]{10,15}$");

    public static String validate(LeadDTO lead, Set<String> uniquePhones, Set<String> uniqueEmails) {
        if (lead.getName() == null || lead.getName().isEmpty()) {
            return "Name is missing";
        }
        if (lead.getEmail() == null || !EMAIL_REGEX.matcher(lead.getEmail()).matches()) {
            return "Invalid email";
        }
        if (lead.getPhone() == null || !PHONE_REGEX.matcher(lead.getPhone()).matches()) {
            return "Invalid phone";
        }
        if (uniquePhones.contains(lead.getPhone())) {
            return "Duplicate phone";
        }
        if (uniqueEmails.contains(lead.getEmail())) {
            return "Duplicate email";
        }

        uniquePhones.add(lead.getPhone());
        uniqueEmails.add(lead.getEmail());
        return null;
    }
}
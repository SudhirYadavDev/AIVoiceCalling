package com.indux.leadservice.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LeadDTO {

    private String name;
    private String phone;
    private String email;
    private String region;
}
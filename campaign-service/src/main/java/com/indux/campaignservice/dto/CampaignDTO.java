package com.indux.campaignservice.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class CampaignDTO {
    private String name;
    private String region;
    private String language;
    private String status;

}
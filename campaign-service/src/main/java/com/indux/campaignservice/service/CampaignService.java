package com.indux.campaignservice.service;

import com.indux.campaignservice.client.EmailClient;
import com.indux.campaignservice.client.LeadClient;
import com.indux.campaignservice.dto.CampaignDTO;
import com.indux.campaignservice.dto.LeadDTO;
import com.indux.campaignservice.model.Campaign;
import com.indux.campaignservice.repository.CampaignRepository;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

@Service
public class CampaignService {

    private final CampaignRepository campaignRepository;

    private final LeadClient leadClient;
    private final EmailClient emailClient;

    public CampaignService(CampaignRepository campaignRepository,
                           LeadClient leadClient,
                           EmailClient emailClient) {
        this.campaignRepository = campaignRepository;
        this.leadClient = leadClient;
        this.emailClient = emailClient;
    }

    public String startCampaign(Long campaignId) {

        Campaign campaign = campaignRepository.findById(campaignId)
                .orElseThrow( () -> new RuntimeException("Campaign Not Found!"));

        List<LeadDTO> leads = leadClient.getAllLeads();

        for(LeadDTO lead: leads) {
            if(!lead.getRegion().equalsIgnoreCase(campaign.getRegion())) {
                continue;
            }
            emailClient.sendEmail(
                    lead.getEmail(),
                    "campaign: " + campaign.getName(),
                    "hello" + lead.getName() + ", this is a campaign message!"
            );
        }
        campaign.setStatus("RUNNING");
        campaignRepository.save(campaign);

        return "Campaign started and emails sent!";
    }

    public CampaignDTO createCampaign(CampaignDTO dto) {
        Campaign campaign = this.mapToEntity(dto);
        Campaign saved = this.campaignRepository.save(campaign);
        return this.mapToDTO(saved);
    }

    public List<CampaignDTO> getAllCampaign() {
        return (List)this.campaignRepository.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public CampaignDTO updateStatus(Long id, String status) {
        Campaign campaign = this.campaignRepository.findById(id).orElseThrow(() -> new RuntimeException("Campaign Not Found!"));
        campaign.setStatus(status);
        Campaign updated = this.campaignRepository.save(campaign);
        return this.mapToDTO(updated);
    }

    private CampaignDTO mapToDTO(Campaign campaign) {
        CampaignDTO dto = new CampaignDTO();
        dto.setName(campaign.getName());
        dto.setRegion(campaign.getRegion());
        dto.setLanguage(campaign.getLanguage());
        dto.setStatus(campaign.getStatus());
        return dto;
    }

    private Campaign mapToEntity(CampaignDTO dto) {
        Campaign campaign = new Campaign();
        campaign.setName(dto.getName());
        campaign.setRegion(dto.getRegion());
        campaign.setLanguage(dto.getLanguage());
        campaign.setStatus(dto.getStatus());
        return campaign;
    }
}
package com.indux.campaignservice.controller;

import com.indux.campaignservice.dto.CampaignDTO;
import com.indux.campaignservice.service.CampaignService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/api/campaigns"})
public class CampaignController {
    private final CampaignService campaignService;

    public CampaignController(CampaignService campaignService) {
        this.campaignService = campaignService;
    }

    @PostMapping("/{id}/start")
    public String startCampaign(@PathVariable Long id) {
        return campaignService.startCampaign(id);
    }

    @PostMapping
    public CampaignDTO createCampaign(@RequestBody CampaignDTO dto) {
        return this.campaignService.createCampaign(dto);
    }

    @GetMapping
    public List<CampaignDTO> getAllCampaigns() {
        return this.campaignService.getAllCampaign();
    }

    @PutMapping({"/{id}/status"})
    public CampaignDTO updateStatus(@PathVariable Long id, @RequestParam String status) {
        return this.campaignService.updateStatus(id, status);
    }
}

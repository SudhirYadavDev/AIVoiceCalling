package com.indux.campaignservice.repository;

import com.indux.campaignservice.model.Campaign;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CampaignRepository extends JpaRepository<Campaign, Long> {
}
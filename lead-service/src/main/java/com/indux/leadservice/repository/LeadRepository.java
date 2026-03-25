package com.indux.leadservice.repository;

import com.indux.leadservice.model.Lead;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LeadRepository extends JpaRepository<Lead, Long> {

    Optional<Lead> findByEmail(String email);

    Optional<Lead> findByPhone(String phone);

    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);
}
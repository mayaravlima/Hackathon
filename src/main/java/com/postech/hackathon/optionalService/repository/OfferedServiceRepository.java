package com.postech.hackathon.optionalService.repository;

import com.postech.hackathon.optionalService.entity.OfferedService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OfferedServiceRepository extends JpaRepository<OfferedService, Long> {
}

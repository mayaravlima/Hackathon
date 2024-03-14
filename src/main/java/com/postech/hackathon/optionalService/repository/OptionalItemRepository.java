package com.postech.hackathon.optionalService.repository;

import com.postech.hackathon.optionalService.entity.OptionalItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OptionalItemRepository extends JpaRepository<OptionalItem, Long> {
}

package com.postech.hackathon.locality.repository;

import com.postech.hackathon.locality.entity.Building;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BuildingRepository extends JpaRepository<Building, Long> {
    Page<Building> findByLocalityId(Long localityId, Pageable pageable);
}

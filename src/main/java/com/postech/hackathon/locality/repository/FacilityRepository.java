package com.postech.hackathon.locality.repository;

import com.postech.hackathon.locality.entity.Bed;
import com.postech.hackathon.locality.entity.Facility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FacilityRepository extends JpaRepository<Facility, Long> {
}

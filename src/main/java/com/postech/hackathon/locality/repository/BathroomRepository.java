package com.postech.hackathon.locality.repository;

import com.postech.hackathon.locality.entity.Bathroom;
import com.postech.hackathon.locality.entity.Building;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BathroomRepository extends JpaRepository<Bathroom, Long> {
}

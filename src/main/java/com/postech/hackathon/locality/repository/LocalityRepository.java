package com.postech.hackathon.locality.repository;

import com.postech.hackathon.locality.entity.Locality;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocalityRepository extends JpaRepository<Locality, Long> {
}

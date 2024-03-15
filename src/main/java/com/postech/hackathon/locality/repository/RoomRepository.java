package com.postech.hackathon.locality.repository;

import com.postech.hackathon.locality.entity.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    Page<Room> findByBuildingId(Long buildingId, Pageable pageable);

    List<Room> findByCapacityGreaterThanEqual(int quantity);
}

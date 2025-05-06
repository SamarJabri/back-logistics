package com.app.logistics.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.logistics.entities.Shipment;

@Repository
public interface ShipmentRepository extends JpaRepository<Shipment, Long> {
	
	//List<Shipment> findByUserIdUser(Long userId);
    List<Shipment> findByEstimatedArrivalBefore(LocalDateTime dateTime);

}

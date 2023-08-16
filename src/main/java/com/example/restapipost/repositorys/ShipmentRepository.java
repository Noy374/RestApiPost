package com.example.restapipost.repositorys;

import com.example.restapipost.entity.Shipments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShipmentRepository extends JpaRepository<Shipments,Long> {
}

package org.example.moviereservation.repository;

import org.example.moviereservation.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation,Long>{}

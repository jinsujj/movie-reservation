package org.example.moviereservation.repository;

import org.example.moviereservation.entity.Screening;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScreeningRepository extends JpaRepository<Screening, Long>{}
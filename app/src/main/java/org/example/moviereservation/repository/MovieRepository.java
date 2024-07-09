package org.example.moviereservation.repository;

import org.example.moviereservation.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Long>{}

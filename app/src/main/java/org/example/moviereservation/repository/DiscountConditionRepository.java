package org.example.moviereservation.repository;

import org.example.moviereservation.entity.discount.condition.DiscountCondition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiscountConditionRepository extends JpaRepository<DiscountCondition,Long>{}

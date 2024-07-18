package org.example.moviereservation.repository;

import org.example.moviereservation.entity.policy.DiscountPolicy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiscountPolicyRepository extends JpaRepository<DiscountPolicy,Long> {}
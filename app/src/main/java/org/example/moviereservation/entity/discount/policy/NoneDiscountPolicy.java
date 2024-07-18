package org.example.moviereservation.entity.policy;

import org.example.moviereservation.entity.Money;
import org.example.moviereservation.entity.Screening;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@DiscriminatorValue("None")
public class NoneDiscountPolicy extends DiscountPolicy{

    @Override
    public Money calculateDiscountAmount(Screening screening) {
        return Money.ZERO;
    }
    
}

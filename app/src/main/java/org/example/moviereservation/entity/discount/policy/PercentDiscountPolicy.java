package org.example.moviereservation.entity.policy;

import java.util.List;

import org.example.moviereservation.entity.Money;
import org.example.moviereservation.entity.Screening;
import org.example.moviereservation.entity.discount.condition.DiscountCondition;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@DiscriminatorValue("PERCENT")
public class PercentDiscountPolicy extends DiscountPolicy {
    private double percent;

    public PercentDiscountPolicy(double percent, List<DiscountCondition> conditions) {
        this.percent = percent;
        this.setConditions(conditions);
    }

    @Override
    public Money calculateDiscountAmount(Screening screening) {
        for (DiscountCondition condition :getConditions()) {
            if (condition.isSatisfiedBy(screening)) {
                return screening.getMovie().getFee().times(percent);
            }
        }
        return Money.ZERO;
    }
}
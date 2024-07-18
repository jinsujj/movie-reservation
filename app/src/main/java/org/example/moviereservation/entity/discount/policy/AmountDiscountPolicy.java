package org.example.moviereservation.entity.discount.policy;

import java.util.List;

import org.example.moviereservation.entity.Money;
import org.example.moviereservation.entity.Screening;
import org.example.moviereservation.entity.discount.condition.DiscountCondition;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@DiscriminatorValue("AMOUNT")
public class AmountDiscountPolicy extends DiscountPolicy {
    private Money discountAmount;

    public AmountDiscountPolicy(Money discountAmount, List<DiscountCondition> conditions) {
        this.discountAmount = discountAmount;
        this.setConditions(conditions);
    }

    @Override
    public Money calculateDiscountAmount(Screening screening) {
        for (DiscountCondition condition : getConditions()) {
            if (condition.isSatisfiedBy(screening)) {
                return discountAmount;
            }
        }
        return Money.ZERO;
    }
}
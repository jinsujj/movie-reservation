package org.example.moviereservation.entity.discount.condition;

import org.example.moviereservation.entity.Screening;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@DiscriminatorValue("SEQUENCE")
public class SequenceCondition extends DiscountCondition {
    private int sequence;

    public SequenceCondition(int sequence) {
        this.sequence = sequence;
    }

    @Override
    public boolean isSatisfiedBy(Screening screening) {
        /* 상영하는 숫자가 일치하는 경우 */
        return screening.getSequence() == sequence;
    }
}
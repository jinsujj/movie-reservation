package org.example.moviereservation.entity.discount.condition;

import java.time.DayOfWeek;
import java.time.LocalTime;

import org.example.moviereservation.entity.Screening;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@DiscriminatorValue("PERIOD")
public class PeriodCondition extends DiscountCondition {
    private DayOfWeek dayOfWeek;
    private LocalTime startTime;
    private LocalTime endTime;

    public PeriodCondition(DayOfWeek dayOfWeek, LocalTime startTime, LocalTime endTime) {
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Override
    public boolean isSatisfiedBy(Screening screening) {
        /* 요일이 일치하고, 시작시간과 종료시간 이내에 상영하는 영화의 경우 */
        return screening.getWhenScreened().getDayOfWeek().equals(dayOfWeek) &&
                !screening.getWhenScreened().toLocalTime().isBefore(startTime) &&
                !screening.getWhenScreened().toLocalTime().isAfter(endTime);
    }
}
package org.example.moviereservation.entity.discount.policy;

import java.util.List;

import org.example.moviereservation.entity.Money;
import org.example.moviereservation.entity.Screening;
import org.example.moviereservation.entity.discount.condition.DiscountCondition;

import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*
 * 단일 테이블 전략은 상속 구조의 엔티티를 하나의 테이블에 모두 통합하여 저장합니다
 * 각 엔티티의 특정 타입은 @DiscriminatorColumn을 사용하여 구분되며, 이 컬럼을 통해 어떤 하위 클래스의 데이터인지 구분할 수 있습니다.
 */

@Entity
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type")
@Getter @Setter
public abstract class DiscountPolicy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "discountPolicy")
    private List<DiscountCondition> conditions;

    public abstract Money calculateDiscountAmount(Screening screening);
}
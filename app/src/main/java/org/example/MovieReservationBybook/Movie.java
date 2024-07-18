package org.example.MovieReservationBybook;

import java.time.Duration;
import java.util.List;

/*
 * 요금을 계산하기 위해 Movie 는 기본 금액(fee), 할인 조건(discountConditions), 할인 정책 등의 정보를 알아야 한다. 
 * 현재의 설계에서 할인 정책을 Movie 의 일부로 구현하고 있기 때문에 할인 정책을 구성하는 할인 금액과 할인 비율을 Movie 의 인스턴스 변수로 선언했다.
 * 그리고 현재의 Movie 가 어떤 정책이 적용된 영화인지를 나타내기 위한 영화 종류(MovieType)를 인스턴스 변수로 포함한다.
 */

public class Movie {

	public enum MovieType {
		AMOUNT_DISCOUNT,
		PERCENT_DISCOUNT,
		NONE_DISCOUNT
	}

	private String title;
	private Duration runningTime;
	private Money fee;
	private List<DiscountCondition> discountConditions;

	private MovieType movieType;
	private Money discountAmout;
	private double discountPercent;

	public Money calculateMovieFee(Screening screening) {
		if (isDiscountable(screening)) {
			return fee.minus(calculateDiscountAmount());
		}
	}

	private boolean isDiscountable(Screening screening) {
		return discountConditions.stream().anyMatch(condition -> condition.isSatisfiedBy(screening));
	}

	private Money calculateDiscountAmount() {
		switch (movieType) {
			case AMOUNT_DISCOUNT:
				return calculateAmountDiscountAmount();

			case PERCENT_DISCOUNT:
				return calculatePercentDiscountAmount();

			case NONE_DISCOUNT:
				return calculateNoneDiscountAmount();

			default:
				break;
		}
		throw new IllegalStateException();
	}

	private Money calculateNoneDiscountAmount() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'calculateNoneDiscountAmount'");
	}

	private Money calculateAmountDiscountAmount() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'calculateAmountDiscountAmount'");
	}

	private Money calculatePercentDiscountAmount() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'calculatePercentDiscountAmount'");
	}

}

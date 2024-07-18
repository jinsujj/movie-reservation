package org.example.MovieReservationBybook;

/*
 * 1. 협력의 관점에서 Screening은 예매하라 메시지에 응답할 수 있어야 한다. 
 * 
 * 2. 책임이 결정됐으므로 책임을 수행하는데 필요한 인스턴스 변수를 결정해야 한다. 
 * 
 * 3. 영화를 예매하기 위해서는 movie 에게 가격을 계산하라 메시지를 전송해서 계산된 영화 요금을 반환해야한다. 
 */

public class Screening {
	private Movie movie;
	private int sequence;
	private LocalDatetime whenScreened;

	public Reservation reserve(Customer customer, int audienceCount) {

	}

	private Money calculateFee(int audienceCount) {
		return Movie.calculateMovieFee(this).times(audienceCount);
	}
}

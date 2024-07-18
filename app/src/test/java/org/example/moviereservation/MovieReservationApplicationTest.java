package org.example.moviereservation;

import org.example.moviereservation.entity.Customer;
import org.example.moviereservation.entity.Money;
import org.example.moviereservation.entity.Movie;
import org.example.moviereservation.entity.Reservation;
import org.example.moviereservation.entity.Screening;
import org.example.moviereservation.entity.discount.condition.DiscountCondition;
import org.example.moviereservation.entity.discount.condition.PeriodCondition;
import org.example.moviereservation.entity.discount.condition.SequenceCondition;
import org.example.moviereservation.entity.discount.policy.AmountDiscountPolicy;
import org.example.moviereservation.entity.discount.policy.DiscountPolicy;
import org.example.moviereservation.entity.discount.policy.NoneDiscountPolicy;
import org.example.moviereservation.entity.discount.policy.PercentDiscountPolicy;
import org.example.moviereservation.repository.CustomerRepository;
import org.example.moviereservation.repository.DiscountConditionRepository;
import org.example.moviereservation.repository.DiscountPolicyRepository;
import org.example.moviereservation.repository.MovieRepository;
import org.example.moviereservation.repository.ReservationRepository;
import org.example.moviereservation.repository.ScreeningRepository;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class MovieReservationApplicationTests {

    /*
     * ./gradlew bootRun
     * ./gradlew test
     */

    /*
     * Screening
     * - 상영 정보를 알고 있다 (Movie)
     * - 예매 정보를 생성한다.
     * 
     * Movie
     * - 영화 정보를 알고 있다 (DiscountPolicy)
     * - 가격을 계산한다 
     * 
     * DisCountPolicy 
     * - 영화 정책을 알고 있다 (DiscountCondition)
     * - 할인된 가격을 계산한다.
     * 
     * DiscountCondition
     * - 할인 조건을 알고 있다 (Screening)
     * - 할인 여부를 판단한다
     */

    private static final Logger logger = LoggerFactory.getLogger(MovieReservationApplicationTests.class);

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ScreeningRepository screeningRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private DiscountPolicyRepository discountPolicyRepository;

    @Autowired
    private DiscountConditionRepository discountConditionRepository;

    @Test
    void contextLoads() {
        Customer customer = new Customer("John Doe", "john.doe@example.com");
        customerRepository.save(customer);

        DiscountCondition periodCondition = new PeriodCondition(DayOfWeek.MONDAY, LocalTime.of(10, 0), LocalTime.of(22, 0));
        discountConditionRepository.save(periodCondition);

        DiscountPolicy discountPolicy = new AmountDiscountPolicy(Money.wons(800), Arrays.asList(periodCondition));
        discountPolicyRepository.save(discountPolicy);

        Movie avartar = new Movie("Avatar", Duration.ofMinutes(120), Money.wons(10000), discountPolicy);
        movieRepository.save(avartar);
        Money fee = avartar.getFee();
        System.out.println(fee.getAmount().toString());


        Screening screening = new Screening(avartar, 1, LocalDateTime.of(2024, 7, 7, 18, 0));
        screeningRepository.save(screening);

        Reservation reservation = screening.reserve(customer, 2);
        reservationRepository.save(reservation);
        logger.info("testAmountDiscountPolicy - Expected Fee: 18400, Actual Fee: {}", reservation.getFee().getAmount());
    }

    @Test
    void testAmountDiscountPolicy() {
        // given
        Customer customer = new Customer("John Doe", "john.doe@example.com");
        customerRepository.save(customer);

        DiscountCondition periodCondition = new PeriodCondition(DayOfWeek.MONDAY, LocalTime.of(10, 0), LocalTime.of(22, 0));
        discountConditionRepository.save(periodCondition);

        DiscountPolicy discountPolicy = new AmountDiscountPolicy(Money.wons(800), Arrays.asList(periodCondition));
        discountPolicyRepository.save(discountPolicy);

        Movie movie = new Movie("Avatar", Duration.ofMinutes(120), Money.wons(10000), discountPolicy);
        movieRepository.save(movie);

        Screening screening = new Screening(movie, 1, LocalDateTime.of(2024, 7, 8, 18, 0)); // Monday
        screeningRepository.save(screening);

        // when
        Reservation reservation = screening.reserve(customer, 2);

        // then
        assertThat(reservation.getFee().getAmount()).isEqualByComparingTo(BigDecimal.valueOf(18400));
        logger.info("testAmountDiscountPolicy - Expected Fee: 18400, Actual Fee: {}", reservation.getFee().getAmount());
    }

    @Test
    void testPercentDiscountPolicy() {
        // given
        Customer customer = new Customer("Jane Smith", "jane.smith@example.com");
        customerRepository.save(customer);

        DiscountCondition sequenceCondition = new SequenceCondition(1);
        discountConditionRepository.save(sequenceCondition);

        DiscountPolicy discountPolicy = new PercentDiscountPolicy(0.1, Arrays.asList(sequenceCondition));
        discountPolicyRepository.save(discountPolicy);

        Movie movie = new Movie("Inception", Duration.ofMinutes(150), Money.wons(12000), discountPolicy);
        movieRepository.save(movie);

        Screening screening = new Screening(movie, 1, LocalDateTime.of(2024, 7, 9, 15, 0)); // Tuesday, sequence 1
        screeningRepository.save(screening);

        // when
        Reservation reservation = screening.reserve(customer, 3);

        // then
        assertThat(reservation.getFee().getAmount()).isEqualByComparingTo(BigDecimal.valueOf(32400));
        logger.info("testPercentDiscountPolicy - Expected Fee: 32400, Actual Fee: {}", reservation.getFee().getAmount());
    }

    @Test
    void testNoDiscountPolicy() {
        // given
        Customer customer = new Customer("Alice Brown", "alice.brown@example.com");
        customerRepository.save(customer);

        DiscountPolicy discountPolicy = new NoneDiscountPolicy();
        discountPolicyRepository.save(discountPolicy);

        Movie movie = new Movie("The Matrix", Duration.ofMinutes(130), Money.wons(15000), discountPolicy);
        movieRepository.save(movie);

        Screening screening = new Screening(movie, 1, LocalDateTime.of(2024, 7, 10, 19, 0)); // Wednesday
        screeningRepository.save(screening);

        // when
        Reservation reservation = screening.reserve(customer, 4);

        // then
        assertThat(reservation.getFee().getAmount()).isEqualByComparingTo(BigDecimal.valueOf(60000));
        logger.info("testNoDiscountPolicy - Expected Fee: 60000, Actual Fee: {}", reservation.getFee().getAmount());
    }

    @Test
    void testMultipleDiscountConditions() {
        // given
        Customer customer = new Customer("Bob White", "bob.white@example.com");
        customerRepository.save(customer);

        DiscountCondition periodCondition = new PeriodCondition(DayOfWeek.FRIDAY, LocalTime.of(14, 0), LocalTime.of(18, 0));
        DiscountCondition sequenceCondition = new SequenceCondition(2);
        discountConditionRepository.save(periodCondition);
        discountConditionRepository.save(sequenceCondition);

        DiscountPolicy discountPolicy = new PercentDiscountPolicy(0.2, Arrays.asList(periodCondition, sequenceCondition));
        discountPolicyRepository.save(discountPolicy);

        Movie movie = new Movie("Interstellar", Duration.ofMinutes(180), Money.wons(18000), discountPolicy);
        movieRepository.save(movie);

        Screening screening = new Screening(movie, 2, LocalDateTime.of(2024, 7, 12, 16, 0)); // Friday, sequence 2
        screeningRepository.save(screening);

        // when
        Reservation reservation = screening.reserve(customer, 1);

        // then
        assertThat(reservation.getFee().getAmount()).isEqualByComparingTo(BigDecimal.valueOf(14400));
        logger.info("testMultipleDiscountConditions - Expected Fee: 14400, Actual Fee: {}", reservation.getFee().getAmount());
    }
}
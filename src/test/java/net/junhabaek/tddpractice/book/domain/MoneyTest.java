package net.junhabaek.tddpractice.book.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.BDDAssertions.*;

public class MoneyTest {
    // static 검증은 bdd가 어울리지 않기 때문에, 다른 naming 사용
    @Test
    void StaticZero_ShouldHaveZeroValue() {
        //given
        Money staticZero = Money.ZERO;
        Money zero = Money.of(0L);

        //then
        then(staticZero).isEqualTo(zero);
    }

    @Test
    void Given_MinusValue_When_CreateMoneyWithIt_Then_ThrowsIllegalArgumentException() {
        //given
        Long minusValue = -5L;

        //when
        IllegalArgumentException illegalArgumentException =
                catchThrowableOfType(()->{Money.of(minusValue);}, IllegalArgumentException.class);

        //then
        then(illegalArgumentException.getMessage()).isEqualTo("Money can not have minus value.");
    }

    @Test
    void Given_TwoMoneyWithDifferentValue_When_PlusThem_Then_ResultShouldBeSameAsExpected() {
        //given
        Money oneHundred = Money.of(100L);
        Money thirty = Money.of(30L);

        //when
        Money plusResult = oneHundred.plus(thirty);

        //then
        Money oneHundredThirty = Money.of(130L);
        then(plusResult).isEqualTo(oneHundredThirty);
    }

    @Test
    void Given_TwoMoneyWithDifferentValue_When_MinusSmallerFromBigger_Then_ResultShouldBeSameAsExpected() {
        //given
        Money oneHundred = Money.of(100L);
        Money thirty = Money.of(30L);

        //when
        Money minusResult = oneHundred.minus(thirty);

        //then
        Money seventy = Money.of(70L);
        then(minusResult).isEqualTo(seventy);
    }

    @Test
    void Given_TwoMoneyWithSameValue_When_MinusThem_Then_ResultShouldBeZero() {
        //given
        Money fifty = Money.of(50L);
        Money fifty2 = Money.of(50L);

        //when
        Money minusResult = fifty.minus(fifty2);
        Money minusResult2 = fifty2.minus(fifty);

        //then
        then(minusResult).isEqualTo(Money.ZERO);
        then(minusResult2).isEqualTo(Money.ZERO);
    }

    @Test
    void Given_TwoMoneyWithDifferentValue_When_MinusBiggerFromSmaller_Then_ThrowsIllegalArgumentException() {
        //given
        Money thirty = Money.of(30L);
        Money oneHundred = Money.of(100L);

        //when
        IllegalArgumentException illegalArgumentException =
                catchThrowableOfType(()->{thirty.minus(oneHundred);}, IllegalArgumentException.class);

        //then
        then(illegalArgumentException.getMessage()).isEqualTo("Money can not have minus value.");
    }

    @Test
    void Given_NonZeroMoney_When_MultiplyWithPositiveValue_Then_ResultShouldBeSameAsExpected() {
        //given
        Money oneHundred = Money.of(100L);
        Long multiplyFactor = 5L;

        //when
        Money multiplyResult = oneHundred.multiply(multiplyFactor);

        //then
        Money fiveHundred = Money.of(500L);
        then(multiplyResult).isEqualTo(fiveHundred);
    }

    @Test
    void Given_ZeroMoney_When_MultiplyWithPositiveValues_Then_ResultAlwaysZero() {
        //given
        Long firstMultiplyFactor = 0L;
        Long secondMultiplyFactor = 5L;
        Long thirdMultiplyFactor = 10L;

        //when
        Money firstMultiplyResult = Money.ZERO.multiply(firstMultiplyFactor);
        Money secondMultiplyResult = Money.ZERO.multiply(secondMultiplyFactor);
        Money thirdMultiplyResult = Money.ZERO.multiply(thirdMultiplyFactor);

        //then
        then(firstMultiplyResult).isEqualTo(Money.ZERO);
        then(secondMultiplyResult).isEqualTo(Money.ZERO);
        then(thirdMultiplyResult).isEqualTo(Money.ZERO);
    }

    @Test
    void Given_NonZeroMoney_When_MultiplyWithNegativeValue_Then_ThrowsIllegalArgumentException() {
        //given
        Money oneHundred = Money.of(100L);
        Long multiplyFactor = -5L;

        //when
        IllegalArgumentException illegalArgumentException =
                catchThrowableOfType(()->oneHundred.multiply(multiplyFactor), IllegalArgumentException.class);

        //then
        then(illegalArgumentException.getMessage()).isEqualTo("Money can not multiply with minus value.");
    }
}

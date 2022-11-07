package net.junhabaek.tddpractice.book.domain;

import lombok.Getter;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;
import javax.persistence.Transient;
import java.math.BigInteger;
import java.util.Objects;

@Embeddable
@Access(value= AccessType.FIELD)
public class Money {
    @Transient
    public final static Money ZERO = Money.of(0L);

    @Getter
    private final BigInteger amount;

    private Money(){
        this.amount = BigInteger.ZERO;
    }
    public Money(BigInteger amount) {
        if(amount == null || amount.compareTo(BigInteger.ZERO) < 0) {
            throw new IllegalArgumentException("Money can not have minus value.");
        }

        this.amount = amount;
    }

    public static Money of(Long value){
        return new Money(BigInteger.valueOf(value));
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(amount);
    }

    @Override
    public boolean equals(Object other) {
        if(this == other) return true;
        if(other == null || getClass() != other.getClass()) return false;

        Money otherMoney = (Money) other;

        return this.amount.equals(otherMoney.amount);
    }

    public Money plus(Money other){
        return new Money(this.amount.add(other.amount));
    }
    public Money minus(Money other) {
        return new Money(this.amount.subtract(other.amount));
    }
    public Money multiply(Long times) {
        if(times < 0){
            throw new IllegalArgumentException("Money can not multiply with minus value.");
        }
        return new Money(this.amount.multiply(BigInteger.valueOf(times)));
    }
}
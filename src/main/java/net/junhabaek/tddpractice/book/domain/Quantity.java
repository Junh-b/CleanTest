package net.junhabaek.tddpractice.book.domain;

import lombok.Getter;

import java.math.BigInteger;

// Money와 코드가 유사하긴 하지만, 현재로서는 우발적 중복으로 판단. 앞으로 변경될 이유도 다를 것.
public class Quantity {
    public static Quantity ZERO = Quantity.of(0L);

    @Getter
    private final BigInteger quantity;

    public Quantity plus(Quantity other){
        return new Quantity(this.quantity.add(other.quantity));
    }
    public Money minus(Quantity other) {
        return new Money(this.quantity.subtract(other.quantity));
    }
    public Money multiply(Long times) {
        return new Money(this.quantity.multiply(BigInteger.valueOf(times)));
    }

    private Quantity(){
        this.quantity = BigInteger.ZERO;
    }

    public Quantity(BigInteger amount) {
        if(amount == null || amount.compareTo(BigInteger.ZERO) < 0) {
            throw new IllegalStateException("Quantity can not have minus value.");
        }

        this.quantity = amount;
    }

    public static Quantity of(Long value){
        return new Quantity(BigInteger.valueOf(value));
    }
}

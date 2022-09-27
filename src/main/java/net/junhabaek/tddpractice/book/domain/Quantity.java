package net.junhabaek.tddpractice.book.domain;

import lombok.Getter;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;
import javax.persistence.Transient;
import java.math.BigInteger;
import java.util.Objects;

// can be replaced with 'Amount' VO
@Embeddable
@Access(value= AccessType.FIELD)
public class Quantity {
    @Transient
    public final static Quantity ZERO = Quantity.of(0L);

    @Getter
    private final BigInteger quantity;

    public Quantity plus(Quantity other){
        return new Quantity(this.quantity.add(other.quantity));
    }
    public Quantity minus(Quantity other) {
        return new Quantity(this.quantity.subtract(other.quantity));
    }
    public Quantity multiply(Long times) {
        return new Quantity(this.quantity.multiply(BigInteger.valueOf(times)));
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Quantity quantity1 = (Quantity) o;
        return quantity.equals(quantity1.quantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(quantity);
    }
}
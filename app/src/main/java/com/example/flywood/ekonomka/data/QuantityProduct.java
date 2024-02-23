package com.example.flywood.ekonomka.data;

import java.util.Objects;

public class QuantityProduct extends Product {
    Number count;

    public Number getCount() {
        return count;
    }

    public QuantityProduct(String barcod, String name, Number price, Number count) {
        super(barcod, name, price);
        this.count = count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof QuantityProduct)) return false;
        if (!super.equals(o)) return false;
        QuantityProduct that = (QuantityProduct) o;
        return Objects.equals(getCount(), that.getCount());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getCount());
    }
}

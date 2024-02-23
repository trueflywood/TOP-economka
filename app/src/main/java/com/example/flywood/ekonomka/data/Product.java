package com.example.flywood.ekonomka.data;

import java.util.Objects;

public class Product {
    String barcod;

    public String getBarcod() {
        return barcod;
    }

    public String getName() {
        return name;
    }

    public Number getPrice() {
        return price;
    }

    String name;
    Number price;

    public Product(String barcod, String name, Number price) {
        this.barcod = barcod;
        this.name = name;
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        Product product = (Product) o;
        return Objects.equals(getBarcod(), product.getBarcod());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getBarcod());
    }
}


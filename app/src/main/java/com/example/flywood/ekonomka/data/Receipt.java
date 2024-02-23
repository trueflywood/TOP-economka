package com.example.flywood.ekonomka.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class Receipt {
    public int getId() {
        return id;
    }

    int id;
    Date date;

    String name;

    List<Product> receiptList = new ArrayList<>();

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Product> getReceiptList() {
        return receiptList;
    }

    public Receipt() {
    }

    public Receipt(int id, Date date, String name) {
        this.id = id;
        this.date = date;
        this.name = name;
    }

    public void addProduct(Product product)  {
        this.receiptList.add(product);
    }


    /**
     * Преобразуем в список с количеством
     */
    public List<QuantityProduct> getQuantityProduct() {
        List<QuantityProduct> quantityProducts = this.receiptList.stream()
                .map(product -> new QuantityProduct(product.getBarcod(), product.getName(), product.getPrice(),
                        this.receiptList.stream()
                        .filter(p -> p.getBarcod().equals(product.getBarcod()))
                                .count()
                ))
                .distinct()
                .collect(Collectors.toList());
        return quantityProducts;
    }
}





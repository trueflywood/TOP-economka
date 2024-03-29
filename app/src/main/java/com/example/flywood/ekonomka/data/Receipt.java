package com.example.flywood.ekonomka.data;

import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class Receipt {
    public int getId() {
        return id;
    }

    int id;
    Calendar date;

    String name;

    List<Product> receiptList = new ArrayList<>();

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
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


    public Receipt(int id, Calendar date, String name) {
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

    public Float getSumm() {
        Float summ = 0f;

        for (Product product: this.getReceiptList()) {
            summ += Float.parseFloat(product.getPrice().toString());
        }

        return  summ;
    }
}





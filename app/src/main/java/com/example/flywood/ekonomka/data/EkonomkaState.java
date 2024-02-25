package com.example.flywood.ekonomka.data;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.text.DecimalFormat;

public class EkonomkaState {
//    public static Receipt currentReceipt = new Receipt();
    public static  MutableLiveData<Receipt> currentReceiptMutableLiveData = new MutableLiveData<>(new Receipt());
    public static  MutableLiveData<String> currentReceiptSummMutableLiveData = new MutableLiveData<>("= 0.00 р.");

    public static LiveData<Receipt> getLiveCurentReceipt() {
        return currentReceiptMutableLiveData;
    }
    public static Receipt getCurentReceipt() {
        return currentReceiptMutableLiveData.getValue();
    }

    public static  LiveData<String> getLiveCurentReceiptSumm() {
        return currentReceiptSummMutableLiveData;
    }

    public static void addCurrentReceiptUnit(Product product) {
        Receipt receipt = currentReceiptMutableLiveData.getValue();
        receipt.addProduct(product);
        currentReceiptMutableLiveData.postValue(receipt);
        DecimalFormat df = new DecimalFormat("#0.00");
        String roundedNumber = df.format(receipt.getSumm());
        currentReceiptSummMutableLiveData.postValue("= " + roundedNumber + " р.");
    }

}

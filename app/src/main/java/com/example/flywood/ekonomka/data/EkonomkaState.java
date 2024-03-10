package com.example.flywood.ekonomka.data;


import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.flywood.ekonomka.data.services.SqlService;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class EkonomkaState {

    /**
     * номер не известного товара
     */
    public static int unknownNumber = 1;
//    public static Receipt currentReceipt = new Receipt();
    /**
     * текущий рецепт
     */
    public static  MutableLiveData<Receipt> currentReceiptMutableLiveData = new MutableLiveData<>(new Receipt());

    /**
     * сумма текущего рецепта
     */
    public static  MutableLiveData<String> currentReceiptSummMutableLiveData = new MutableLiveData<>("= 0.00 р.");

    /**
     * полученеи текущего чека событие
     * @return
     */

    public static LiveData<Receipt> getLiveCurentReceipt() {
        return currentReceiptMutableLiveData;
    }

    /**
     * получение значения текущего чека
     * @return
     */
    public static Receipt getCurentReceipt() {
        return currentReceiptMutableLiveData.getValue();
    }

    /**
     * получение события суммы текущего чека
     * @return
     */
    public static  LiveData<String> getLiveCurentReceiptSumm() {
        return currentReceiptSummMutableLiveData;
    }

    /**
     * добавленеи товара в чек
     * @param product
     */
    public static void addCurrentReceiptUnit(Product product) {
        Receipt receipt = currentReceiptMutableLiveData.getValue();
        receipt.addProduct(product);
        currentReceiptMutableLiveData.postValue(receipt);
        DecimalFormat df = new DecimalFormat("#0.00");
        String roundedNumber = df.format(receipt.getSumm());
        currentReceiptSummMutableLiveData.postValue("= " + roundedNumber + " р.");
    }

    /**
     * список сохранненных рецептов
     */
    public static  MutableLiveData<List<Receipt>> currentReceiptListMutableLiveData = new MutableLiveData<>(new ArrayList<>());


    /**
     * получение события текущего списка чеков
     * @return
     */
    public static LiveData<List<Receipt>> getLiveListReceipt() {
        return currentReceiptListMutableLiveData;
    }
    /**
     * получение значения текущего списка чеков
     * @return
     */
    public static List<Receipt> getCurrentListReceipt() {
        return currentReceiptListMutableLiveData.getValue();
    }

    public static void  setSavedListReceipt(List<Receipt> list) {
        currentReceiptListMutableLiveData.setValue(list);
    }

    public static void  removeSavedListReceiptUnit(Context context, Receipt receipt) {
        SqlService sqlService = new SqlService(context);
        sqlService.removeReceiptUnit(receipt.getId());
        List<Receipt> list = sqlService.getListReceipts();
        EkonomkaState.setSavedListReceipt(list);
    }





}

package com.example.flywood.ekonomka.ui.receipt;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.flywood.ekonomka.data.Product;
import com.example.flywood.ekonomka.data.Receipt;

public class ReceiptViewModel extends ViewModel {
    private final MutableLiveData<String> receiptSumm;
    private  final MutableLiveData<Receipt> receiptMutableLiveData;

    private Receipt receipt = new Receipt();

    public ReceiptViewModel() {
        receiptMutableLiveData = new MutableLiveData<>();
        receiptMutableLiveData.setValue(receipt);
        receiptSumm = new MutableLiveData<>();
        receiptSumm.setValue("= 33 000");

    }

    public LiveData<String> getText() {
        return receiptSumm;
    }



    ///////////////



    public void addReceiptUnit(Product product) {
        receipt.addProduct(product);
        receiptMutableLiveData.postValue(receipt);
    }

    public LiveData<Receipt> getReceipt() {
        return receiptMutableLiveData;
    }


}

package com.example.flywood.ekonomka.ui.receipt;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ReceiptViewModel extends ViewModel {
    private final MutableLiveData<String> receiptSumm;

    public ReceiptViewModel() {
        receiptSumm = new MutableLiveData<>();
        receiptSumm.setValue("= 33 000");
    }

    public LiveData<String> getText() {
        return receiptSumm;
    }
}

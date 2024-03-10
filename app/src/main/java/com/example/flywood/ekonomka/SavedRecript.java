package com.example.flywood.ekonomka;


import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;


import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.flywood.ekonomka.data.EkonomkaState;
import com.example.flywood.ekonomka.data.Receipt;
import com.example.flywood.ekonomka.data.services.SqlService;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

public class SavedRecript extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_recript);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        ColorDrawable colorDrawable = new ColorDrawable(ContextCompat.getColor(this, R.color.purple_200));
        actionBar.setBackgroundDrawable(colorDrawable);
        LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.seved_receipt_toolbar, null);
        ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(
                ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.MATCH_PARENT);
        actionBar.setCustomView(R.layout.seved_receipt_toolbar);

        Intent intent = getIntent();
        int receiptId = intent.getIntExtra("receiptId", -1);

        SqlService sqlService = new SqlService(this);
        Receipt receipt =  sqlService.getReceipt(receiptId);

        TextView textViewTitle = findViewById(R.id.receipt_title);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String strDate = df.format( receipt.getDate().getTime());

        Spanned spanned = Html.fromHtml("<u>" + receipt.getName() + "<br>от " + strDate + "</u>");

        textViewTitle.setText( spanned);

        final ListView listView = findViewById(R.id.list_saved_receipt);
        final TextView textView = findViewById(R.id.summ_saved_receipt);
        ReceiptUnitAdapter receiptUnitAdapter =new ReceiptUnitAdapter(receipt, this, R.layout.receipt_unit);
        listView.setAdapter(receiptUnitAdapter);

        DecimalFormat df2 = new DecimalFormat("#0.00");
        String roundedNumber = df2.format(receipt.getSumm());
        textView.setText("= " + roundedNumber + " р.");
    }

}
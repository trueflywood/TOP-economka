package com.example.flywood.ekonomka;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.flywood.ekonomka.R;
import com.example.flywood.ekonomka.data.QuantityProduct;
import com.example.flywood.ekonomka.data.Receipt;

import java.text.DecimalFormat;
import java.util.List;

public class ReceiptUnitAdapter extends BaseAdapter {

    List<QuantityProduct> quantityProductList;
    Context context;

    int templateLayout;

    LayoutInflater layoutInflater;

    public ReceiptUnitAdapter(Receipt receipt, Context context, int templateLayout) {
        quantityProductList = receipt.getQuantityProduct();

        this.context = context;
        this.templateLayout = templateLayout;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return quantityProductList.size();
    }

    @Override
    public Object getItem(int position) {
        return quantityProductList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = layoutInflater.inflate(templateLayout, parent, false);
        TextView name = view.findViewById(R.id.unitName);
        TextView code = view.findViewById(R.id.unitCode);
        TextView countPrice = view.findViewById(R.id.countPrice);
        QuantityProduct product = quantityProductList.get(position);
        name.setText(product.getName());
        code.setText(product.getBarcod());
        DecimalFormat df = new DecimalFormat("#0.00");
        countPrice.setText(product.getCount() + " x\n" + df.format(product.getPrice()) + " р.");
        return view;
    }

    public void updateReceipt(Receipt receipt) {
        Log.i("FLWOOD", "updateReceipt");
        this.quantityProductList = receipt.getQuantityProduct();
        this.notifyDataSetChanged();
    }
}

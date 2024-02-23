package com.example.flywood.ekonomka.ui.receipt;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.flywood.ekonomka.R;
import com.example.flywood.ekonomka.data.Product;
import com.example.flywood.ekonomka.data.QuantityProduct;
import com.example.flywood.ekonomka.data.Receipt;
import com.example.flywood.ekonomka.databinding.FragmentReceiptBinding;

import java.util.List;

public class ReceiptFragment extends Fragment {
    private FragmentReceiptBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ReceiptViewModel receiptViewModel =
                new ViewModelProvider(this).get(ReceiptViewModel.class);

        binding = FragmentReceiptBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textReceipt;
        final ListView listView = binding.listReceipt;

        Receipt receipt = new Receipt();

        receipt.addProduct( new Product("1234567890", "товар 1", 300));
        receipt.addProduct( new Product("1234567892", "товар 2", 400));
        receipt.addProduct( new Product("1234567892", "товар 2", 400));
        receipt.addProduct( new Product("1234567892", "товар 2", 400));
        receipt.addProduct( new Product("1234567893", "товар 3", 500));
        receipt.addProduct( new Product("1234567894", "товар 3", 500));
        receipt.addProduct( new Product("1234567895", "товар 3", 500));
        receipt.addProduct( new Product("1234567893", "товар 3", 500));
        receipt.addProduct( new Product("1234567896", "товар 3", 500));
        receipt.addProduct( new Product("1234567897", "товар 3", 500));
        receipt.addProduct( new Product("1234567898", "товар 3", 500));
        receipt.addProduct( new Product("1234567899", "товар 3", 500));
        receipt.addProduct( new Product("1234567830", "товар 3", 500));


        ReceiptUnitAdapter receiptUnitAdapter =new ReceiptUnitAdapter(receipt.getQuantityProduct(), this.getContext(), R.layout.receipt_unit);
        listView.setAdapter(receiptUnitAdapter);

        receiptViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

class ReceiptUnitAdapter extends BaseAdapter {

    List<QuantityProduct> quantityProductList;
    Context context;

    int templateLayout;

    LayoutInflater layoutInflater;

    public ReceiptUnitAdapter(List<QuantityProduct> list, Context context, int templateLayout) {
        quantityProductList = list;
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
        countPrice.setText(product.getCount() + " x\n" + product.getPrice());
        return view;
    }
}

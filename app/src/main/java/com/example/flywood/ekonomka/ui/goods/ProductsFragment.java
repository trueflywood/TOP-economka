package com.example.flywood.ekonomka.ui.goods;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.flywood.ekonomka.R;
import com.example.flywood.ekonomka.data.EkonomkaState;
import com.example.flywood.ekonomka.data.Receipt;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ProductsFragment extends Fragment {
    private com.example.flywood.ekonomka.databinding.FragmentGoodsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ProductsViewModel galleryViewModel =
                new ViewModelProvider(this).get(ProductsViewModel.class);

        binding = com.example.flywood.ekonomka.databinding.FragmentGoodsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final ListView receiptListUnitList = binding.receiptListUnitList;

        ProductsListUnitAdapter productsListUnitAdapter =new ProductsListUnitAdapter(EkonomkaState.getCurrentListReceipt(), this.getContext(), R.layout.receipt_list_unit);

        receiptListUnitList.setAdapter(productsListUnitAdapter);

        EkonomkaState.getLiveListReceipt().observe(getViewLifecycleOwner(), productsListUnitAdapter::updateListReceipt);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

class ProductsListUnitAdapter extends BaseAdapter {

    List<Receipt> productsList;

    Context context;
    int templateLayout;
    LayoutInflater layoutInflater;

    public ProductsListUnitAdapter(List<Receipt> productsList, Context context, int templateLayout) {
        this.productsList = productsList;
        this.context = context;
        this.templateLayout = templateLayout;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return productsList.size();
    }

    @Override
    public Object getItem(int position) {
        return productsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       View view = layoutInflater.inflate(templateLayout, parent, false);
       TextView name = view.findViewById(R.id.receipt_list_unit_name);
       TextView date = view.findViewById(R.id.receipt_list_unit_date);
       Receipt receipt = productsList.get(position);
       name.setText(receipt.getName());
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String strDate = df.format( receipt.getDate().getTime());
       date.setText("от " + strDate);
       return view;
    }


    public void updateListReceipt(List<Receipt> list) {
        this.productsList = list;
        this.notifyDataSetChanged();
    }
}

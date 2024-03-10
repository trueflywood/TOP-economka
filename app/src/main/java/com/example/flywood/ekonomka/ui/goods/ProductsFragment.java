package com.example.flywood.ekonomka.ui.goods;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.flywood.ekonomka.R;
import com.example.flywood.ekonomka.SavedRecript;
import com.example.flywood.ekonomka.data.EkonomkaState;
import com.example.flywood.ekonomka.data.Receipt;
import com.example.flywood.ekonomka.data.services.SqlService;

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

        Context context = this.getContext();

        ProductsListUnitAdapter productsListUnitAdapter =new ProductsListUnitAdapter(EkonomkaState.getCurrentListReceipt(), context, R.layout.receipt_list_unit);

        receiptListUnitList.setAdapter(productsListUnitAdapter);

        EkonomkaState.getLiveListReceipt().observe(getViewLifecycleOwner(), productsListUnitAdapter::updateListReceipt);

        receiptListUnitList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("FLYWOOD", "Удаление пункта в позиции " + position);
                Receipt receipt = productsListUnitAdapter.getItem(position);
                showPopupContextMenu(view, receipt);
                return true;
            }
        });

        receiptListUnitList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Receipt receipt = productsListUnitAdapter.getItem(position);
                // Создаем объект Intent для новой активити
                Intent intent = new Intent(context, SavedRecript.class);
                intent.putExtra("receiptId", receipt.getId());

                startActivity(intent);
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void showPopupContextMenu(View view, final Receipt receipt) {
        Context context = this.getContext();
        PopupMenu popup = new PopupMenu(context, view);
        popup.inflate(R.menu.list_reciepts_context);
//        MenuItem itemDelete = popup.getMenu().getItem(0);

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Log.i("FLYWOOD", "Удаление чекая с id -  " + receipt.getId());

                //List<Receipt> list = EkonomkaState.getCurrentListReceipt();
                EkonomkaState.removeSavedListReceiptUnit(context, receipt);
                //list.remove(position - 1);
                //EkonomkaState.setSavedListReceipt(list);
                return true;
            }
        });
        popup.show();
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
    public Receipt getItem(int position) {
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

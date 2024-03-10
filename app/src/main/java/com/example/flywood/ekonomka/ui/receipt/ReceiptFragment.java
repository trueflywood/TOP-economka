package com.example.flywood.ekonomka.ui.receipt;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.flywood.ekonomka.R;
import com.example.flywood.ekonomka.ReceiptUnitAdapter;
import com.example.flywood.ekonomka.data.EkonomkaState;
import com.example.flywood.ekonomka.databinding.FragmentReceiptBinding;

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




//        if (EkonomkaState.currentReceipt == null) {
//            EkonomkaState.currentReceipt = new Receipt();
//        }

//        receipt.addProduct( new Product("1234567890", "товар 1", 300));
//        receipt.addProduct( new Product("1234567892", "товар 2", 400));
//        receipt.addProduct( new Product("1234567892", "товар 2", 400));
//        receipt.addProduct( new Product("1234567892", "товар 2", 400));
//        receipt.addProduct( new Product("1234567893", "товар 3", 500));
//        receipt.addProduct( new Product("1234567894", "товар 3", 500));
//        receipt.addProduct( new Product("1234567895", "товар 3", 500));
//        receipt.addProduct( new Product("1234567893", "товар 3", 500));
//        receipt.addProduct( new Product("1234567896", "товар 3", 500));
//        receipt.addProduct( new Product("1234567897", "товар 3", 500));
//        receipt.addProduct( new Product("1234567898", "товар 3", 500));
//        receipt.addProduct( new Product("1234567899", "товар 3", 500));
//        receipt.addProduct( new Product("1234567830", "товар 3", 500));


        ReceiptUnitAdapter receiptUnitAdapter =new ReceiptUnitAdapter(EkonomkaState.getCurentReceipt(), this.getContext(), R.layout.receipt_unit);
        listView.setAdapter(receiptUnitAdapter);

        EkonomkaState.getLiveCurentReceipt().observe(getViewLifecycleOwner(), receiptUnitAdapter::updateReceipt);
        EkonomkaState.getLiveCurentReceiptSumm().observe(getViewLifecycleOwner(), textView::setText);


//        receiptViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}


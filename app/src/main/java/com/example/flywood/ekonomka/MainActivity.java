package com.example.flywood.ekonomka;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.flywood.ekonomka.data.EkonomkaState;
import com.example.flywood.ekonomka.data.Product;
import com.example.flywood.ekonomka.data.Receipt;
import com.example.flywood.ekonomka.data.services.SqlService;
import com.example.flywood.ekonomka.ui.receipt.ReceiptViewModel;
import com.google.android.material.navigation.NavigationView;

import androidx.appcompat.app.AlertDialog;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.flywood.ekonomka.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    SqlService sqlService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;


        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_receipt,
                R.id.nav_goods,
                R.id.nav_about
        )
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);




        navController.addOnDestinationChangedListener((navController1, navDestination, bundle) -> {


            Menu menu =   binding.appBarMain.toolbar.getMenu();
            if(navDestination.getId() == R.id.nav_receipt) {
                getMenuInflater().inflate(R.menu.main, menu);
            }
            else {
                menu.clear();
            }

        });

        // NOTE работа с базой данных
        sqlService = new SqlService(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void getBarcode(View view) {

        View formView = LayoutInflater.from(this).inflate(R.layout.input_product, null);

        new AlertDialog.Builder(this)
                .setTitle("Добавление товара")
                .setMessage("Заполниие поля для добавления товара")
                .setView(formView)
                .setPositiveButton("ОК", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        EditText editTextCode = formView.findViewById(R.id.product_code);
                        EditText editTextName = formView.findViewById(R.id.product_name);
                        EditText editTextPrice = formView.findViewById(R.id.product_price);

                        String code = editTextCode.getText().toString();
                        String name = editTextName.getText().toString();
                        String price = editTextPrice.getText().toString();

//                        if (EkonomkaState.currentReceipt == null) {
//                            EkonomkaState.currentReceipt = new Receipt();
//                        }



                        EkonomkaState.addCurrentReceiptUnit(new Product(code, name, Float.parseFloat(price)));




                        Toast.makeText(MainActivity.this, code, Toast.LENGTH_SHORT).show();



                        // NOTE Кусок кода для проверки работы сохранения рецепта

//                        Receipt receipt = new Receipt();
//
//                        receipt.addProduct( new Product("1234567890", "товар 1", 300));
//                        receipt.addProduct( new Product("1234567892", "товар 2", 400));
//                        receipt.addProduct( new Product("1234567892", "товар 2", 400));
//                        receipt.addProduct( new Product("1234567892", "товар 2", 400));
//                        receipt.addProduct( new Product("1234567893", "товар 3", 500));
//                        receipt.addProduct( new Product("1234567894", "товар 3", 500));
//                        receipt.addProduct( new Product("1234567895", "товар 3", 500));
//                        receipt.addProduct( new Product("1234567893", "товар 3", 500));
//                        receipt.addProduct( new Product("1234567896", "товар 3", 500));
//                        receipt.addProduct( new Product("1234567897", "товар 3", 500));
//                        receipt.addProduct( new Product("1234567898", "товар 3", 500));
//                        receipt.addProduct( new Product("1234567899", "товар 3", 500));
//                        receipt.addProduct( new Product("1234567830", "товар 3", 500));
//
//                        receipt.setName("test ewufieunfieunfiu qefqeoi");
//                        receipt.setDate(new Date(2024, 2,20));
//
//
//                        sqlService.addReceipt(receipt);
                    }
                })
                .setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // Обработка нажатия на кнопку Отмена
                    }
                })
                .show();
    }
}
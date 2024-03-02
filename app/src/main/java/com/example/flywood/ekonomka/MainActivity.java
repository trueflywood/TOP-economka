package com.example.flywood.ekonomka;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.flywood.ekonomka.data.EkonomkaState;
import com.example.flywood.ekonomka.data.Product;
import com.example.flywood.ekonomka.data.Receipt;
import com.example.flywood.ekonomka.data.services.SqlService;
import com.example.flywood.ekonomka.ui.receipt.ReceiptViewModel;
import com.google.android.material.navigation.NavigationView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.flywood.ekonomka.databinding.ActivityMainBinding;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;


import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    SqlService sqlService;

    private ActivityResultLauncher<String> resultPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    showCamera(0);
                } else {
                    Toast.makeText(this, "Not Permission", Toast.LENGTH_SHORT).show();
                }
            });

    private ActivityResultLauncher<ScanOptions> qrCodeLauncher = registerForActivityResult(new ScanContract(), result -> {
        if (result.getContents() == null) {
            Toast.makeText(this, "Отмена", Toast.LENGTH_SHORT).show();
        } else {
            setResult(result.getContents());
        }
    });

    private void setResult(String result) {
        Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
    }

    private void showCamera(int cameraId) {
        ScanOptions options = new ScanOptions();
        options.setDesiredBarcodeFormats(ScanOptions.EAN_13);
        options.setPrompt("Scann bar code");
        options.setCameraId(cameraId);
        options.setBeepEnabled(false);

        options.setBarcodeImageEnabled(true);
        options.setOrientationLocked(true);

        qrCodeLauncher.launch(options);

    }



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
                createReceiptMenu(menu);
            }
            else {
                menu.clear();
            }

        });

        // NOTE работа с базой данных
        sqlService = new SqlService(this);
    }


    public boolean createReceiptMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);

        MenuItem menuSaveItem = menu.findItem(R.id.action_favorite);
        menuSaveItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem item) {
                Toast.makeText(MainActivity.this, "Menu Save", Toast.LENGTH_SHORT).show();
                // TODO
                //     - сохранять список в базу
                //
                return true;
            }
        });

        MenuItem menuClearItem = menu.findItem(R.id.action_clear);
        menuClearItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem item) {
                Toast.makeText(MainActivity.this, "Menu Clear", Toast.LENGTH_SHORT).show();
                EkonomkaState.currentReceiptMutableLiveData.setValue(new Receipt());
                return true;
            }
        });


        return true;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return createReceiptMenu(menu);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void scanBarcode(View view) {
        checkPermissionAndShowActivity(this);

    }

    private void checkPermissionAndShowActivity(Context context) {
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED) {
            showCamera(0);
        } else if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
            Toast.makeText(context, "Разрешения на камеру обязвтельны", Toast.LENGTH_SHORT).show();
        } else {
            resultPermissionLauncher.launch(Manifest.permission.CAMERA);
        }
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

    public void saveReceipt(View view) {
        Toast.makeText(this, "Menu save", Toast.LENGTH_SHORT).show();
    }
}
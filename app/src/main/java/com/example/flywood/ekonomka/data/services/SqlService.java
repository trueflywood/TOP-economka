package com.example.flywood.ekonomka.data.services;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.flywood.ekonomka.data.Product;
import com.example.flywood.ekonomka.data.Receipt;

import java.util.List;

public class SqlService extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "EkonomkaDb";

    private static final String CREATE_RECEIPT_LIST_TABLE_SCRIPT =
            "CREATE TABLE IF NOT EXISTS Receipts (" +
                    " Id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    " Name TEXT NOT NULL, " +
                    " Date TEXT NOT NULL) ";
    private static final String CREATE_RECEIPT_UNIT_TABLE_SCRIPT =
            "CREATE TABLE IF NOT EXISTS ReceiptUnit (" +
                    " Id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    " IdReceipt INTEGER NOT NULL, " +
                    " name TEXT NOT NULL," +
                    " code TEXT NOT NULL," +
                    " price REAL NOT NULL) ";

    public SqlService(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_RECEIPT_LIST_TABLE_SCRIPT);
        db.execSQL(CREATE_RECEIPT_UNIT_TABLE_SCRIPT);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addReceipt(Receipt receipt) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put("Name", receipt.getName());
            values.put("Date", receipt.getDate().toString());

            long id1 = db.insert("Receipts", null ,values);
            Log.i("FLYWOOD", "add id = " + id1);


            this.addReceiptUnit((int) id1, receipt.getReceiptList());
        }catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
    }

    public void addReceiptUnit(int id, List<Product> receiptList) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        try {

            for (Product product:
                 receiptList) {
                ContentValues values = new ContentValues();

                values.put("IdReceipt", id);
                values.put("Name", product.getName());
                values.put("code", product.getBarcod());
                values.put("price", Float.parseFloat(product.getPrice().toString()));
                db.insert("ReceiptUnit", null ,values);
            }

            db.setTransactionSuccessful();
            Log.i("FLYWOOD", "Successful");
        }catch (Exception e){
            e.printStackTrace();
        } finally {
            db.endTransaction();
            db.close();
        }
    }
}

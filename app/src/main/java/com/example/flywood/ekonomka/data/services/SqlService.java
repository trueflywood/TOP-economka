package com.example.flywood.ekonomka.data.services;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.flywood.ekonomka.data.Product;
import com.example.flywood.ekonomka.data.Receipt;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            String strDate = df.format( receipt.getDate().getTime());
            values.put("Date", strDate);

            long id1 = db.insert("Receipts", null ,values);
            Log.i("FLYWOOD", "add id = " + id1);


            this.addReceiptUnit((int) id1, receipt.getReceiptList());
        }catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
    }

    public List<Receipt> getListReceipts() {
        List<Receipt> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query("Receipts", null,null,null, null, null,null);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        if (cursor.moveToFirst()) {

           int idIndex =  cursor.getColumnIndex("Id");
           int nameIndex =  cursor.getColumnIndex("Name");
           int dateIndex =  cursor.getColumnIndex("Date");
           if (nameIndex >= 0 && dateIndex >= 0 && idIndex >= 0) {

               do {
                   try {
                       int id = cursor.getInt(idIndex);
                       String name = cursor.getString(nameIndex);
                       String strDate = cursor.getString(dateIndex);

                       Date date = df.parse(strDate);
                       Calendar calendar = Calendar.getInstance();
                       calendar.setTime(date);

                       Receipt receipt = new Receipt(id, calendar, name);
                       Log.i("FLYWOOD", "receipt = " + id + " | "+ strDate + " | " + name);
                       list.add(receipt);
                   } catch (ParseException e) {
                       throw new RuntimeException(e);
                   }

               } while (cursor.moveToNext());
           }
        }
        db.close();
       return list;
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

    public void removeReceiptUnit(int id) {
        Log.i("FLYWOOD", "removeReceiptUnit - " + id);
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            db.execSQL("delete from Receipts where id = ?", new Object[]{id});
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
    }
}

package com.example.flywood.ekonomka.data.services.api;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class ProductResponse implements Serializable {
    @SerializedName("names")
    public ArrayList<String> names;
    @SerializedName("status")
    public Integer status;

}

package com.example.hammad.workshopinventory;

import android.graphics.Bitmap;

/**
 * Created by shekh chilli on 10/24/2016.
 */

public class Description {

    private String mname;
    private String mpartNo;
    private int mquantity;
    private String mprice;
    private Bitmap mimage;
    private String mpubNo;
    private String mrefNo;
    private String mdate;
    private Boolean NO_IMAGE_PROVIDED = false;


    public Description(Bitmap image, String name, String partNo, String price, int quantity) {
        this.mimage = image;
        this.mname = name;
        this.mpartNo = partNo;
        this.mprice = price;
        this.mquantity = quantity;
        this.NO_IMAGE_PROVIDED = false;
    }



    public Description(String name, String partNo, int quantity, String date) {
        this.mname = name;
        this.mpartNo = partNo;
        this.mquantity = quantity;
        this.mprice = date;
        this.NO_IMAGE_PROVIDED = true;
    }

    public Bitmap getMimage() {
        return mimage;
    }

    public String getMname() {
        return mname;
    }

    public String getMpartNo() {
        return mpartNo;
    }

    public String getMprice() {
        return mprice;
    }

    public String getMpubNo() {
        return mpubNo;
    }

    public int getMquantity() {
        return mquantity;
    }

    public String getMrefNo() {
        return mrefNo;
    }

    public Boolean hasImage(){
        return NO_IMAGE_PROVIDED;
    }
}

package com.PR.starbuzz_ver3;


import com.example.starbuzz2.R;

public class Store {
    private String name;
    private String address;
    private int imageResourceId;

    //drinks is an array of Drinks
    public static final Store[] stores = {
            new Store("안성점", "경기도 안성시 중앙로", R.drawable.shop_anseong),
            new Store("일산점", "경기도 일산동구 정발산로", R.drawable.shop_ilsan),
            new Store("평택점", "경기도 평택시 평택로", R.drawable.shop_p)
    };

    private Store (String name, String address, int imageResourceId) {
        this.name = name;
        this.address = address;
        this.imageResourceId = imageResourceId;
    }

    public String getAddress() {
        return address;
    }

    public String getName() {
        return name;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    public String toString() {
        return this.name;
    }
}
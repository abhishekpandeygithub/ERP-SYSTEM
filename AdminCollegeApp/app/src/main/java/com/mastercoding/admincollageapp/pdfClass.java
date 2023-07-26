package com.mastercoding.admincollageapp;

import android.net.Uri;

public class pdfClass {
    public   String name,url;

    public pdfClass(Uri url) {
    }



    public String getName(String name) {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl(String url) {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public pdfClass(String name,String url) {
        this.name = name;
        this.url = url;
    }
}

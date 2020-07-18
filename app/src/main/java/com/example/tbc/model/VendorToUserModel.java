package com.example.tbc.model;

import java.io.Serializable;

public class VendorToUserModel implements Serializable
{
    public  String id;
    public  String vendor_name;
    public  String type_commodity;
    public  String nature_of_vending;
    public  String lat;
    public  String lng;
    public  String timing_of_vendor;
    public  String license_number;
    public  String payment_option;
    public  String vendor_img;
    public  String rating;

    public String getVendor_name() {
        return vendor_name;
    }

    public void setVendor_name(String vendor_name) {
        this.vendor_name = vendor_name;
    }

    public String getType_commodity() {
        return type_commodity;
    }

    public void setType_commodity(String type_commodity) {
        this.type_commodity = type_commodity;
    }

    public String getNature_of_vending() {
        return nature_of_vending;
    }

    public void setNature_of_vending(String nature_of_vending) {
        this.nature_of_vending = nature_of_vending;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getTiming_of_vendor() {
        return timing_of_vendor;
    }

    public void setTiming_of_vendor(String timing_of_vendor) {
        this.timing_of_vendor = timing_of_vendor;
    }

    public String getLicense_number() {
        return license_number;
    }

    public void setLicense_number(String license_number) {
        this.license_number = license_number;
    }

    public String getPayment_option() {
        return payment_option;
    }

    public void setPayment_option(String payment_option) {
        this.payment_option = payment_option;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVendor_img() {
        return vendor_img;
    }

    public void setVendor_img(String vendor_img) {
        this.vendor_img = vendor_img;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }



}

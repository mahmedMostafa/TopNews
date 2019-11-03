package com.thealien.topnews.Models;



public class Country {

    private int countryImageUrl;
    private String countryName;

    public Country(int countryImageUrl, String countryName) {
        this.countryImageUrl = countryImageUrl;
        this.countryName = countryName;
    }

    public int getCountryImageUrl() {
        return countryImageUrl;
    }

    public String getCountryName() {
        return countryName;
    }


}

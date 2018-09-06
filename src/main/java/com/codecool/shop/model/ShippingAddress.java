package com.codecool.shop.model;

public class ShippingAddress {
    private int userId;
    private String country;
    private String city;
    private String address;
    private String zipCode;

    public ShippingAddress(int userId, String country, String city, String address, String zipCode){
        this.userId = userId;
        this.country = country;
        this.city = city;
        this.address = address;
        this.zipCode = zipCode;
    }

    public int getUserId() {
        return userId;
    }

    @Override
    public String toString() {
        return String.format("userId: %1$d, " +
                        "country: %2$s, " +
                        "city: %3$s" +
                        "address: %4$s" +
                        "zipCode: %5$s",

                this.userId,
                this.country,
                this.city,
                this.address,
                this.zipCode);

    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public String getAddress() {
        return address;
    }

    public String getZipCode() {
        return zipCode;
    }
}

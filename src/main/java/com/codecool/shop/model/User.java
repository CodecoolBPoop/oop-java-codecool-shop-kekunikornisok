package com.codecool.shop.model;

public class User {
    private int id;
    private String emailAddress;
    private String password;
    private String firstName;
    private String lastName;
    private String country;
    private String city;
    private String address;
    private String zipCode;
    private boolean isShippingSame;
    private static User instance = null;

    private User(){}

    public static User getInstance(){
        if (instance == null) {
            instance = new User();
        }
        return instance;
    }

    public User(int id, String emailAddress, String password, String firstName, String lastName, String country, String city, String address, String zipCode, boolean isShippingSame) {
        this.id = id;
        this.emailAddress = emailAddress;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.country = country;
        this.city = city;
        this.address = address;
        this.zipCode = zipCode;
        this.isShippingSame = isShippingSame;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getPassword() {
        return password;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
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

    public boolean isShippingSame() {
        return isShippingSame;
    }

    @Override
    public String toString() {
        return String.format("id: %1$d, " +
                        "emailAddress: %2$s, " +
                        "firstName: %3$s, " +
                        "lastName: %4$s, " +
                        "country: %5$s, " +
                        "city: %6$s" +
                        "address: %7$s" +
                        "zipCode: %8$s" +
                        "isShippingSame: %9$b",

                this.id,
                this.emailAddress,
                this.firstName,
                this.lastName,
                this.country,
                this.city,
                this.address,
                this.zipCode,
                this.isShippingSame);

    }
}

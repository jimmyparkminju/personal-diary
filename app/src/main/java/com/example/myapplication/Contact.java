package com.example.myapplication;

public class Contact {
    private long id;
    private String name;
    private String phone;
    private String email;
    private String company;
    private String position;

    public Contact(long id, String name, String phone, String email, String company, String position) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.company = company;
        this.position = position;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getCompany() {
        return company;
    }

    public String getPosition() {
        return position;
    }

    @Override
    public String toString() {
        return "이름: " + name;
    }
}

package com.example.agenda;

public class Contacte {

    private long id;
    private String name;
    private String surname;
    private String address;
    private String number;
    private String email;
    private String birthDate;

    public Contacte(long id, String name, String surname, String address, String number, String email, String birthDate) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.number = number;
        this.email = email;
        this.birthDate = birthDate;
    }

    public Contacte(String name, String surname, String address, String number, String email, String birthDate) {
        this(-1, name, surname, address, number, email, birthDate);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    @Override
    public String toString() {
        return name + " " + surname;
    }
}

package com.example.nyabankomaten;

public class Person {

    private String name;
    private String personNumber;

    public Person(String name, String personNumber) {
        this.name = name;
        this.personNumber = personNumber;
    }
    public Person(){
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPersonNumber() {
        return personNumber;
    }

    public void setPersonNumber(String personNumber) {
        this.personNumber = personNumber;
    }
}

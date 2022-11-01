package com.example.nyabankomaten;

public class Personal extends Person{
    private int salary;
    private String titel;

    public Personal(String name, String personNumber, int salary, String titel) {
        super(name, personNumber);
        this.salary = salary;
        this.titel = titel;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }
}

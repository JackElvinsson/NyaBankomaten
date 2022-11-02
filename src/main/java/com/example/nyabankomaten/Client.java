package com.example.nyabankomaten;

import java.util.ArrayList;
import java.util.List;

public class Client extends Person {

    private List<Account> account;

    public Client(String name, String personNumber) {
        super(name, personNumber);
        account = new ArrayList<>();
    }

    public Client() {

    };

    public List<Account> getAccount() {
        return account;
    }
    public Account[] getAccountArray(){
        return account.toArray(new Account[0]);
    }

    public void addAccount(Account accountNum) {
        this.account.add(accountNum);
    }
}
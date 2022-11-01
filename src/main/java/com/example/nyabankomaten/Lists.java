package com.example.nyabankomaten;

import java.util.ArrayList;
import java.util.List;

public class Lists {

    public String getNewAccountNumber(List<String> inputList) {
        String accountNumber = "";
        boolean running = true;
        while(running) {
            for (int i = 0; i < 9; i++) {
                accountNumber += (int) (Math.random() * 9);
            }
            for (String a: inputList) {
                if(accountNumber.equals(a)){
                    break;
                }
            }
            running = false;
        }
        inputList.add(accountNumber);
        return accountNumber;
    }
}
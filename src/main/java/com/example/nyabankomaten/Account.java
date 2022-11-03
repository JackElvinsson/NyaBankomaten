package com.example.nyabankomaten;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Account implements Printable {

    private String accountNumber;
    private long balance;
    private long loan;
    List<String> accountHistory = new ArrayList<>();
    private Interest loanDetails;
    Path path = Paths.get("src/clients/accounts/"+getAccountNumber()+".txt");
    public Account(String accountNumber) {
        this.accountNumber = accountNumber;
        this.balance = 0;
        this.loan = 0;
        this.loanDetails = Interest.NONE;
    }
    public Account(String accountNumber, long balance, long loan, Interest loanDetails){
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.loan = loan;
        this.loanDetails = loanDetails;
    }

    public String getAccountNumber(){
        return accountNumber;
    }

    public Interest getLoanDetails() {
        return loanDetails;
    }

    public void setLoanDetails(Interest loanDetails) {
        this.loanDetails = loanDetails;
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance += balance;
    }
    public void payLoan(){
        balance -= loan;
        loan = 0;
        loanDetails = Interest.NONE;
    }
    public void deposit(long deposit){
        balance += deposit;
    }
    public void withdraw(long withdraw){
        if(withdraw <= balance){
            balance -= withdraw;
        }
    }

    public double getLoan() {
        return loan * loanDetails.interest;
    }


    public void newLoan(long loan){
        this.loan = loan;
    }

    @Override
    public void PrintAccountDetails() {
        System.out.println("Account number: "+ accountNumber+ "\nBalance: "+ balance +"\nLoan amount: "+ loan + "\nLoan interest: "+ Math.round((loanDetails.interest -1) * 100) + "%" +"\nLoan type: "+ loanDetails.type);
    }
}

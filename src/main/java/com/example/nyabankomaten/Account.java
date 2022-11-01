package com.example.nyabankomaten;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Account implements Printable {

    private String accountNumber;
    private int balance;
    private int loan;
    private Interest loanDetails;
    Path path = Paths.get("src/clients/accounts/"+getAccountNumber()+".txt");
    public Account(String accountNumber) {
        this.accountNumber = accountNumber;
        this.balance = 0;
        this.loan = 0;
        this.loanDetails = Interest.NONE;
    }
    public Account(String accountNumber, int balance, int loan, Interest loanDetails){
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

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }
    public void deposit(int deposit){
        balance += deposit;
    }
    public void withdraw(int withdraw){
        if(withdraw <= balance){
            balance -= withdraw;
        }
    }

    public double getLoan() {
        return loan * loanDetails.interest;
    }

    public void setLoan(int loan) {
        this.loan = loan;
    }

    public void newLoan(int loan){

    }

    @Override
    public void PrintAccountDetails() {
        System.out.println("Account number: "+ accountNumber+ "\nBalance: "+ balance +"\nLoan amount: "+ loan + "\nLoan interest: "+ Math.round((loanDetails.interest -1) * 100) + "%" +"\nLoan type: "+ loanDetails.type);
    }
}

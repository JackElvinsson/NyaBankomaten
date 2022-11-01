package com.example.nyabankomaten;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.List;

public class MainController {
    Client client = new Client();
    Lists l = new Lists();
    List<Client> listOfClients = new ArrayList<>();
    List<String> listOfAllAccounts = new ArrayList<>();


    @FXML
    private Button addNewClient;
    @FXML
    private Label clientList;
    @FXML
    private Label accounts;
    @FXML
    private Label accountName;
    @FXML
    private Button addClientButton;
    @FXML
    private Button createNewAccountButton;
    @FXML
    private Button removeClientButton;
    @FXML
    private Button newLoanButton;
    @FXML
    private Button payoffLoanButton;
    @FXML
    private TextField addClientName;
    @FXML
    private TextField addClientSSC;
    @FXML
    private TextField depositAmount;
    @FXML
    private TextField withdrawAmount;

    @FXML
    private ScrollPane clientListPane;
    @FXML
    private ScrollPane accountListPane;


    @FXML
    protected void onSelectClientClick(){
        client = listOfClients.get();

    }

    @FXML
    protected void onAddClientClick(){
        Client client = new AddClient(addClientName.getText(), addClientSSC.getText());
        listOfClients.add(client);
    }

    @FXML
    protected void onDepositClick(){
        client.getAccount().get(accountListPane).deposit(Integer.parseInt(depositAmount.getText()));
    }
    @FXML
    protected void onWithdrawClick(){
        client.getAccount().get(accountListPane).withdraw(Integer.parseInt(withdrawAmount.getText()));
    }

    @FXML
    protected void onCreateNewAccountClick(){
        Account a = new Account(l.getNewAccountNumber(listOfAllAccounts));
        client.addAccount(a);
    }

    @FXML
    protected void onRemoveClientClick(){
        listOfClients.remove(clientListPane);
    }
}

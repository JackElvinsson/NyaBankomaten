package com.example.nyabankomaten;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    Client client;
    Account account;
    Lists l = new Lists();

    Client c1 = new Client("Kalle", "123455677");
    Client c2 = new Client("Björne", "987456423");

    List<Client> listOfClients = List.of(c1, c2);
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
    private Button deposit;
    @FXML
    private Button withdraw;
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
    private ListView<String> listViewClient;

    @FXML
    private ListView<String> listViewAccounts;


    @FXML
    protected void onSelectClientClick() {
//        client = listOfClients.get();

    }

    @FXML
    protected void onAddClientClick() {
        Client client = new AddClient(addClientName.getText(), addClientSSC.getText());
        listOfClients.add(client);
    }

    @FXML
    protected void onDepositClick() {
        client.getAccount().get(listViewAccounts.getSelectionModel().getSelectedIndex()).deposit(Integer.parseInt(depositAmount.getText()));
    }

    @FXML
    protected void onWithdrawClick() {
//        client.getAccount().get(accountListPane).withdraw(Integer.parseInt(withdrawAmount.getText()));
    }

    @FXML
    protected void onCreateNewAccountClick() {
        Account a = new Account(l.getNewAccountNumber(listOfAllAccounts));
        client.addAccount(a);
    }

    //Funkar ej
    @FXML
    protected void onRemoveClientClick() {
        listOfClients.remove(listViewClient.getSelectionModel().getSelectedIndex());
        listViewClient.getItems().remove(listViewClient.getSelectionModel().getSelectedIndex());
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        for(Client c: listOfClients){
            listViewClient.getItems().add(c.getName() + "\t\t\t|\t" + c.getPersonNumber());
        }
        listViewClient.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                client = listOfClients.get(listViewClient.getSelectionModel().getSelectedIndex());
                listViewAccounts.getItems().clear();
                for(Account a: client.getAccount()){
                    listViewAccounts.getItems().add(a.getAccountNumber() + "\t\t\t|\t" + a.getBalance());
                }
                System.out.println(client.getName());
            }
        });
    }
}

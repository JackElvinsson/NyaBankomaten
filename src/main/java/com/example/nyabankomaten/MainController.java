package com.example.nyabankomaten;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    Client client;
    Account account;
    Lists l = new Lists();

    Client c1 = new Client("Kalle", "123455677");
    Client c2 = new Client("Björne", "987456423");

//    List<Client> listOfClients = List.of(c1, c2);
    List<Client> listOfClients = new ArrayList<>();
    List<String> listOfAllAccounts = new ArrayList<>();
    List<Interest> listOfLoanTypes = List.of(Interest.BUSINESS, Interest.HOUSE, Interest.EDUCATION, Interest.PERSONAL, Interest.VEHICLE);

    @FXML
    private GridPane gridpane1;
    @FXML
    private Button addNewClient;
    @FXML
    private Label clientList;
    @FXML
    private Label accounts;
    @FXML
    private Label accountName;
    @FXML
    private TextField loanAmountField;
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
    private Button cancelButton;
    @FXML
    private Button applyForLoanButton;
    @FXML
    private ScrollPane accountHistory;
    @FXML
    private TextField addClientName;
    @FXML
    private TextField addClientSSC;
    @FXML
    private TextField depositAmount;
    @FXML
    private TextField withdrawAmount;

    @FXML
    private ListView<String> listViewClient;

    @FXML
    private ListView<String> listViewAccounts;
    @FXML
    private ComboBox<String> loanTypeBox;
    @FXML
    protected void onCancelButtonClick(){
        gridpane1.setVisible(false);
    }

    @FXML
    protected void onNewLoanButtonClick() {
        gridpane1.setVisible(true);
    }


    @FXML
    protected void onAddClientClick() {
        Client client = new AddClient(addClientName.getText(), addClientSSC.getText());
        listOfClients.add(client);
        System.out.println(listOfClients.size());
        System.out.println(listOfClients.get(listOfClients.size()-1).getName());
    }

    @FXML
    protected void onDepositClick() {
        try {
            client.getAccount().get(listViewAccounts.getSelectionModel().getSelectedIndex()).deposit(Integer.parseInt(depositAmount.getText()));
            client.getAccount().get(listViewAccounts.getSelectionModel().getSelectedIndex()).accountHistory.add("Deposit - " + depositAmount.getText() + "kr - " + LocalDateTime.now() + "\n");

        } catch (IndexOutOfBoundsException e) {
            Alert alert = new Alert(Alert.AlertType.NONE);
            alert.setContentText("No account selected");
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.show();
        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.NONE);
            alert.setContentText("No accounts available");
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.show();
        }
    }

    @FXML
    protected void onWithdrawClick() {
        try {
            client.getAccount().get(listViewAccounts.getSelectionModel().getSelectedIndex()).withdraw(Integer.parseInt(withdrawAmount.getText()));
            client.getAccount().get(listViewAccounts.getSelectionModel().getSelectedIndex()).accountHistory.add("Withdrawal - " + withdrawAmount.getText() + "kr - " + LocalDateTime.now() + "\n");
        } catch (IndexOutOfBoundsException e) {
            Alert alert = new Alert(Alert.AlertType.NONE);
            alert.setContentText("No account selected");
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.show();
        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.NONE);
            alert.setContentText("No accounts available");
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.show();
        }
    }

    @FXML
    protected void onCreateNewAccountClick() {
        Account a = new Account(l.getNewAccountNumber(listOfAllAccounts));
        client.addAccount(a);
        listViewAccounts.refresh();
    }

    //Funkar ej
    @FXML
    protected void onRemoveClientClick() {
        listOfClients.remove(listViewClient.getSelectionModel().getSelectedIndex());
        listViewClient.getItems().remove(listViewClient.getSelectionModel().getSelectedIndex());
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        for(Interest i: listOfLoanTypes){
            loanTypeBox.getItems().add(i.type);
        }
        for (Client c : listOfClients) {
            listViewClient.getItems().add(c.getName() + "\t\t\t|\t" + c.getPersonNumber());
        }
        listViewClient.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                client = listOfClients.get(listViewClient.getSelectionModel().getSelectedIndex());
                listViewAccounts.getItems().clear();
                for (Account a : client.getAccount()) {
                    listViewAccounts.getItems().add(a.getAccountNumber() + "\t\t\t|\t" + a.getBalance());
                }
                System.out.println(client.getName());
            }
        });
    }
}
